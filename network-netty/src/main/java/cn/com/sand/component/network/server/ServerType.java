/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-gateway-api $Id$ $Revision$ Last Changed by SJ at
 * 2015年8月13日 下午1:40:43 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年8月13日
 * Initailized
 */
package cn.com.sand.component.network.server;

/**
 *
 * @ClassName ：ServerType
 * @author : SJ
 * @Date : 2015年8月13日 下午1:40:43
 * @version 2.0.0
 *
 */
public enum ServerType
{
    HTTP("http-server"), SOCKET("socket-server");

    private final String type;

    private ServerType(String type)
    {
        this.type = type;
    }

    public String getServerTypeName()
    {
        return this.type;
    }
}
