/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-netty $Id$ $Revision$ Last Changed by SJ at
 * 2015年9月25日 下午5:43:43 $URL$
 *
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年9月25日
 * Initailized
 */
package cn.com.sand.component.network.netty.serialize;

/**
 *
 * @ClassName ：NettySerializeFactory
 * @author : SJ
 * @Date : 2015年9月25日 下午5:43:43
 * @version 2.0.0
 *
 */
public class NettySerializeFactory
{
    public static NettyMessageSerialize getMessageSeri(MsgSerialType serType)
            throws Exception
    {
        if (MsgSerialType.HTTP.equals(serType))
        {
            return new HttpMessageSerialize();
        }
        else if (MsgSerialType.LINEBASE.equals(serType))
        {
            return new LineBaseMessageSerialize();
        }
        else if (MsgSerialType.LENFIELD.equals(serType))
        {
            return new LengthFieldMessageSerialize();
        }
        else
        {
            throw new Exception();
        }
    }
}
