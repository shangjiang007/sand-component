/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-gateway-api $Id$ $Revision$ Last Changed by SJ at
 * 2015年8月13日 下午2:09:36 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年8月13日
 * Initailized
 */
package cn.com.sand.component.network.server;

/**
 *
 * @ClassName ：ServerChannel
 * @author : SJ
 * @Date : 2015年8月13日 下午2:09:36
 * @version 2.0.0
 *
 */
public interface ServerChannel<Container>
{
    Object getAttribute(Container key);

    void setAttribute(Container key, Object value);

    void close();

    void write(Object value);
}
