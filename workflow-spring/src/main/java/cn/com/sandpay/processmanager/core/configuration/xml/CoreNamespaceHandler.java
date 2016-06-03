package cn.com.sandpay.processmanager.core.configuration.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class CoreNamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		
		this.registerBeanDefinitionParser("flow", new TopLevelFlowParser());
		
	}

}
