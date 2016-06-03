/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-gateway-netty $Id$ $Revision$ Last Changed by SJ at
 * 2015年8月13日 下午2:30:29 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年8月13日
 * Initailized
 */
package cn.com.sand.component.network.netty.socket;

import cn.com.sand.component.network.server.ServerChannel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 *
 * @ClassName ：NettyChannel
 * @author : SJ
 * @Date : 2015年8月13日 下午2:30:29
 * @version 2.0.0
 *
 */
public class NettyChannel implements ServerChannel<AttributeKey<Object>>
{
    private ChannelHandlerContext channel;

    public static final String  ATTR_KEY_CLEANSESSION_NAME = "ATTR_KEY_CLEANSESSION";

    public static final String  ATTR_KEY_CLIENTID_NAME     = "ATTR_KEY_CLIENTID";

    public static final AttributeKey<Object> ATTR_KEY_CLEANSESSION = AttributeKey.valueOf(ATTR_KEY_CLEANSESSION_NAME);

    public static final AttributeKey<Object> ATTR_KEY_CLIENTID = AttributeKey.valueOf(ATTR_KEY_CLIENTID_NAME);

    public NettyChannel(Long clientID,ChannelHandlerContext ctx) {
        channel = ctx;
        this.setAttribute(ATTR_KEY_CLIENTID,clientID);
    }

    public Object getAttribute(AttributeKey<Object> key) {
        Attribute<Object> attr = channel.attr(key);
        return attr.get();
    }

    public void setAttribute(AttributeKey<Object> key, Object value) {
        Attribute<Object> attr = channel.attr(key);
        attr.set(value);
    }

    public void write(Object value) {
        channel.writeAndFlush(value);
    }

    public String toString() {
        Long clientID = (Long) getAttribute(ATTR_KEY_CLIENTID);
        return "Channel [clientID: "+ clientID +",channel:"+channel+"]" + super.toString();
    }

    /* (non-Javadoc)
     * @see cn.com.sandpay.multichannel.core.common.server.ServerChannel#close()
     */
    @Override
    public void close()
    {
        channel.close();
        
    }

}
