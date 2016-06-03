package cn.com.sandpay.processmanager.core.configuration.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class TopLevelFlowParser extends AbstractFlowParser {

	private static final String ID_ATTR = "id";

	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		String flowName = element.getAttribute(ID_ATTR);
		builder.getRawBeanDefinition().setAttribute("flowName", flowName);
		builder.addPropertyValue("name", flowName);
		super.doParse(element, parserContext, builder);
	}

}
