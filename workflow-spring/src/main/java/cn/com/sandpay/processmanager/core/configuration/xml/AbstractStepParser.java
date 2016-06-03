package cn.com.sandpay.processmanager.core.configuration.xml;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class AbstractStepParser {
	
	// tasklet在这里处理

	protected static final String ID_ATTR = "id";

	private static final String TASKLET_ELE = "tasklet";

	protected AbstractBeanDefinition parseStep(Element stepElement, ParserContext parserContext, String jobFactoryRef) {

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
		AbstractBeanDefinition bd = builder.getRawBeanDefinition();

		// 遍历所有的子节点
		NodeList children = stepElement.getChildNodes();
		
		for (int i = 0; i < children.getLength(); i++) {
			Node nd = children.item(i);
			if (nd instanceof Element) {
				Element nestedElement = (Element) nd;
				String name = nestedElement.getLocalName();
				if (TASKLET_ELE.equals(name)) {
					// 直接new一个TaskletParser来处理
					new TaskletParser().parseTasklet(stepElement, nestedElement, bd, parserContext);
				}
				else {
					throw new RuntimeException("目前只支持tasklet");
				}
			}
		}

		return bd;
	}

}
