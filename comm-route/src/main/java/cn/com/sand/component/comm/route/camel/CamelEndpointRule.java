package cn.com.sand.component.comm.route.camel;

import com.google.common.base.Joiner;

import cn.com.sand.component.comm.route.api.Rule;

public class CamelEndpointRule implements Rule{

    //组件名
    private String componentName = "direct";

    //连接符
    private String connectorSymbol = ":";

    //协议方式
    private String protocol ;

    //业务规则数据
    private String ruleContext;

    public CamelEndpointRule(String componentName,String connectorSymbol,String protocol,String ruleContext){
        this.componentName   = componentName;
        this.connectorSymbol = connectorSymbol;
        this.protocol        = protocol;
        this.ruleContext     = ruleContext;
    }

    public CamelEndpointRule(String componentName,String protocol,String ruleContext){
        this.componentName   = componentName;
        this.protocol        = protocol;
        this.ruleContext     = ruleContext;
    }

    public CamelEndpointRule(String componentName,String ruleContext){
        this.componentName   = componentName;
        this.ruleContext     = ruleContext;
    }

    public CamelEndpointRule(String ruleContext){
        this.ruleContext     = ruleContext;
    }

    public String getRule() {
        return Joiner.on(connectorSymbol).skipNulls().join(componentName,protocol,ruleContext);
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getConnectorSymbol() {
        return connectorSymbol;
    }

    public void setConnectorSymbol(String connectorSymbol) {
        this.connectorSymbol = connectorSymbol;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRuleContext() {
        return ruleContext;
    }

    public void setRuleContext(String ruleContext) {
        this.ruleContext = ruleContext;
    }
}
