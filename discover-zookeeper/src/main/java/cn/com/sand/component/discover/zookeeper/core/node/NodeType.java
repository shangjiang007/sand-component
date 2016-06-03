/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * discover-zookeeper $Id$ $Revision$ Last Changed by SJ at 2016年5月16日 下午2:12:55
 * $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2016年5月16日
 * Initailized
 */
package cn.com.sand.component.discover.zookeeper.core.node;

/**
 *
 * @ClassName ：NodeType
 * @author : SJ
 * @Date : 2016年5月16日 下午2:12:55
 * @version 2.0.0
 *
 */
public enum NodeType
{
    PROVIDERS("providers"), CONSUMERS("consumers"), ROUTERS("routers"), CONFIGURATORS("configurators"), MONITORS(
            "monitors");

    private final String type;

    NodeType(String type)
    {
        this.type = type;
    }

    public String value()
    {
        return type;
    }
}
