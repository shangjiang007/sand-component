package cn.com.sandpay.processmanager.core.configuration.xml;

import java.util.Collection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import cn.com.sandpay.processmanager.core.flow.support.state.StepState;

public class InlineStepParser extends AbstractStepParser {

	public Collection<BeanDefinition> parse(Element element, ParserContext parserContext, String jobFactoryRef) {
		// 使用StepState
		BeanDefinitionBuilder stateBuilder = BeanDefinitionBuilder.genericBeanDefinition(StepState.class);
		String stepId = element.getAttribute(ID_ATTR);

		AbstractBeanDefinition bd = parseStep(element, parserContext, jobFactoryRef);
		parserContext.registerBeanComponent(new BeanComponentDefinition(bd, stepId));
		stateBuilder.addConstructorArgReference(stepId);

		return TopLevelFlowParser.getNextElements(parserContext, stepId, stateBuilder.getBeanDefinition(), element);

	}

}
