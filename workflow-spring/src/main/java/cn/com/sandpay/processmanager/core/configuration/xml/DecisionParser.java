package cn.com.sandpay.processmanager.core.configuration.xml;

import java.util.Collection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class DecisionParser {

	public Collection<BeanDefinition> parse(Element element, ParserContext parserContext) {

		String refAttribute = element.getAttribute("decider");
		String idAttribute = element.getAttribute("id");

		BeanDefinitionBuilder stateBuilder = 
			BeanDefinitionBuilder.genericBeanDefinition(
					"cn.com.sandpay.processmanager.core.flow.support.state.DecisionState");
		stateBuilder.addConstructorArgValue(new RuntimeBeanReference(refAttribute));
		stateBuilder.addConstructorArgValue(idAttribute);
		return TopLevelFlowParser.getNextElements(parserContext, stateBuilder.getBeanDefinition(), element);

	}
}
