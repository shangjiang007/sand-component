package cn.com.sandpay.processmanager.core.configuration.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.com.sandpay.processmanager.core.flow.FlowExecutionStatus;

public abstract class AbstractFlowParser extends AbstractSingleBeanDefinitionParser {

	private static final String ID_ATTR = "id";

	private static final String STEP_ELE = "step";

	private static final String DECISION_ELE = "decision";

	private static final String NEXT_ATTR = "next";

	private static final String NEXT_ELE = "next";

	private static final String END_ELE = "end";

	private static final String FAIL_ELE = "fail";

	private static final String STOP_ELE = "stop";

	private static final String ON_ATTR = "on";

	private static final String TO_ATTR = "to";

	private static final String EXIT_CODE_ATTR = "exit-code";

	private static final InlineStepParser stepParser = new InlineStepParser();

	private static final DecisionParser decisionParser = new DecisionParser();

	// For generating unique state names for end transitions
	private static int endCounter = 0;

	private String jobFactoryRef;

	protected void setJobFactoryRef(String jobFactoryRef) {
		this.jobFactoryRef = jobFactoryRef;
	}

	@Override
	protected Class<?> getBeanClass(Element element) {
		return SimpleFlowFactoryBean.class;
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		/** stateTransitions */
		List<BeanDefinition> stateTransitions = new ArrayList<BeanDefinition>();

		// 这一步是独立的
		CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName(), parserContext.extractSource(element));
		parserContext.pushContainingComponent(compositeDef);

		boolean stepExists = false;
		
		/** reachableElementMap */
		Map<String, Set<String>> reachableElementMap = new HashMap<String, Set<String>>();
		String startElement = null;
		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node instanceof Element) {
				String nodeName = node.getLocalName();
				Element child = (Element) node;
				// 如果是step
				if (nodeName.equals(STEP_ELE)) {
					/** 使用stepParser解析 */
					stateTransitions.addAll(stepParser.parse(child, parserContext, jobFactoryRef));
					stepExists = true;
				}
				// 如果是decision
				else if (nodeName.equals(DECISION_ELE)) {
					/** 使用decisionParser解析 */
					stateTransitions.addAll(decisionParser.parse(child, parserContext));
				}

				// 得到reachableElementMap
				if (Arrays.asList(STEP_ELE, DECISION_ELE).contains(nodeName)) {
					reachableElementMap.put(child.getAttribute(ID_ATTR), findReachableElements(child));
					// 得到startElement
					if (startElement == null) {
						startElement = child.getAttribute(ID_ATTR);
					}
				}
			}
		}

		// 验证flow配置
		String flowName = (String) builder.getRawBeanDefinition().getAttribute("flowName");
		if (!stepExists) {
			parserContext.getReaderContext().error("The flow [" + flowName + "] must contain at least one step, flow or split", element);
		}

		// 确保所有的elements都是可到达的
		Set<String> allReachableElements = new HashSet<String>();
		// 如果理解startElement?
		findAllReachableElements(startElement, reachableElementMap, allReachableElements);
		// 验证，如果发现有element不可到达，则报异常
		for (String elementId : reachableElementMap.keySet()) {
			if (!allReachableElements.contains(elementId)) {
				parserContext.getReaderContext().error("The element [" + elementId + "] is unreachable", element);
			}
		}

		// stateTransitions中每一个都是StateTransition
		ManagedList<BeanDefinition> managedList = new ManagedList<BeanDefinition>();
		// stateTransitions最后都放入managedList中
		@SuppressWarnings("unused")
		boolean dummy = managedList.addAll(stateTransitions);
		builder.addPropertyValue("stateTransitions", managedList);
	}

	private Set<String> findReachableElements(Element element) {
		Set<String> reachableElements = new HashSet<String>();

		String nextAttribute = element.getAttribute(NEXT_ATTR);
		if (StringUtils.hasText(nextAttribute)) {
			reachableElements.add(nextAttribute);
		}

		List<Element> nextElements = DomUtils.getChildElementsByTagName(element, NEXT_ELE);
		for (Element nextElement : nextElements) {
			String toAttribute = nextElement.getAttribute(TO_ATTR);
			reachableElements.add(toAttribute);
		}

		return reachableElements;
	}

	// 使用了递归
	private void findAllReachableElements(
			String startElement, 
			Map<String, Set<String>> reachableElementMap,
			Set<String> accumulator) {
		Set<String> reachableIds = reachableElementMap.get(startElement);
		accumulator.add(startElement);
		if (reachableIds != null) {
			for (String reachable : reachableIds) {
				// don't explore a previously explored element; prevent loop
				if (!accumulator.contains(reachable)) {
					findAllReachableElements(reachable, reachableElementMap, accumulator);
				}
			}
		}
	}

	protected static Collection<BeanDefinition> getNextElements(
			ParserContext parserContext, 
			BeanDefinition stateDef,
			Element element) {
		return getNextElements(parserContext, null, stateDef, element);
	}

	protected static Collection<BeanDefinition> getNextElements(
			ParserContext parserContext, 
			String stepId,
			BeanDefinition stateDef, 
			Element element) {

		Collection<BeanDefinition> list = new ArrayList<BeanDefinition>();

		String shortNextAttribute = element.getAttribute(NEXT_ATTR);
		boolean hasNextAttribute = StringUtils.hasText(shortNextAttribute);
		if (hasNextAttribute) {
			list.add(getStateTransitionReference(parserContext, stateDef, null, shortNextAttribute));
		}

		boolean transitionElementExists = false;
		List<String> patterns = new ArrayList<String>();
		for (String transitionName : new String[] { NEXT_ELE, STOP_ELE, END_ELE, FAIL_ELE }) {
			List<Element> transitionElements = DomUtils.getChildElementsByTagName(element, transitionName);
			for (Element transitionElement : transitionElements) {
				verifyUniquePattern(transitionElement, patterns, element, parserContext);
				list.addAll(parseTransitionElement(transitionElement, stepId, stateDef, parserContext));
				transitionElementExists = true;
			}
		}

		if (!transitionElementExists) {
			list.addAll(createTransition(
					FlowExecutionStatus.FAILED, FlowExecutionStatus.FAILED.getName(), null, null, stateDef, parserContext, false));
			list.addAll(createTransition(
					FlowExecutionStatus.UNKNOWN, FlowExecutionStatus.UNKNOWN.getName(), null, null, stateDef, parserContext, false));
			if (!hasNextAttribute) {
				list.addAll(createTransition(
					FlowExecutionStatus.COMPLETED, null, null, null, stateDef, parserContext, false));
			}
		}
		else if (hasNextAttribute) {
			parserContext.getReaderContext().error("The <" + element.getNodeName() + "/> may not contain a '" + NEXT_ATTR + "' attribute and a transition element", element);
		}

		return list;
	}

	private static void verifyUniquePattern(
			Element transitionElement, 
			List<String> patterns, 
			Element element,
			ParserContext parserContext) {
		String onAttribute = transitionElement.getAttribute(ON_ATTR);
		if (patterns.contains(onAttribute)) {
			parserContext.getReaderContext().error("Duplicate transition pattern found for '" + onAttribute + "'", element);
		}
		patterns.add(onAttribute);
	}

	private static Collection<BeanDefinition> parseTransitionElement(
			Element transitionElement, 
			String stateId,
			BeanDefinition stateDef, 
			ParserContext parserContext) {
		String onAttribute = transitionElement.getAttribute(ON_ATTR);
		String nextAttribute = transitionElement.getAttribute(TO_ATTR);
		boolean abandon = stateId != null;
		String exitCodeAttribute = transitionElement.getAttribute(EXIT_CODE_ATTR);
		// 没有从配置文件读，直接取FlowExecutionStatus.UNKNOWN
		return createTransition(FlowExecutionStatus.UNKNOWN, onAttribute, nextAttribute, exitCodeAttribute, stateDef, parserContext, abandon);
	}

	private static Collection<BeanDefinition> createTransition(
			FlowExecutionStatus status, 
			String on, 
			String next,
			String exitCode, 
			BeanDefinition stateDef, 
			ParserContext parserContext, 
			boolean abandon) {

		BeanDefinition endState = null;

		if (status.isEnd()) {

			BeanDefinitionBuilder endBuilder = 
					BeanDefinitionBuilder.genericBeanDefinition("cn.com.sandpay.processmanager.core.flow.support.state.EndState");

			boolean exitCodeExists = StringUtils.hasText(exitCode);

			endBuilder.addConstructorArgValue(status);

			endBuilder.addConstructorArgValue(exitCodeExists ? exitCode : status.getName());

			String endName = (status == FlowExecutionStatus.STOPPED ? STOP_ELE
					: status == FlowExecutionStatus.FAILED ? FAIL_ELE : END_ELE)
					+ (endCounter++);
			endBuilder.addConstructorArgValue(endName);

			endBuilder.addConstructorArgValue(abandon);

			String nextOnEnd = exitCodeExists ? null : next;
			endState = getStateTransitionReference(parserContext, endBuilder.getBeanDefinition(), null, nextOnEnd);
			next = endName;

		}

		Collection<BeanDefinition> list = new ArrayList<BeanDefinition>();
		list.add(getStateTransitionReference(parserContext, stateDef, on, next));
		if (endState != null) {
			//
			// Must be added after the state to ensure that the state is the
			// first in the list
			//
			list.add(endState);
		}
		return list;
	}

	public static BeanDefinition getStateTransitionReference(
			ParserContext parserContext,
			BeanDefinition stateDefinition, 
			String on, 
			String next) {

		BeanDefinitionBuilder nextBuilder = 
				BeanDefinitionBuilder.genericBeanDefinition(
						"cn.com.sandpay.processmanager.core.flow.support.StateTransition");
		nextBuilder.addConstructorArgValue(stateDefinition);

		if (StringUtils.hasText(on)) {
			nextBuilder.addConstructorArgValue(on);
		}

		if (StringUtils.hasText(next)) {
			nextBuilder.setFactoryMethod("createStateTransition");
			nextBuilder.addConstructorArgValue(next);
		}
		else {
			nextBuilder.setFactoryMethod("createEndStateTransition");
		}

		return nextBuilder.getBeanDefinition();

	}

}
