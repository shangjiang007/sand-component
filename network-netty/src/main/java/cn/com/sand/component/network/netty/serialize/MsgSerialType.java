/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-netty $Id$ $Revision$ Last Changed by SJ at
 * 2015年10月8日 下午1:29:54 $URL$
 *
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年10月8日
 * Initailized
 */
package cn.com.sand.component.network.netty.serialize;

/**
 *
 * @ClassName ：MsgSerialType
 * @author : SJ
 * @Date : 2015年10月8日 下午1:29:54
 * @version 2.0.0
 *
 */
public enum MsgSerialType
{
    HTTP("HTTP"), PROTOBUF("PROTOBUF"),LENFIELD("LENFIELD"),LINEBASE("LINEBASE");

    private final String type;

    private MsgSerialType(String type)
    {
        this.type = type;
    }

    public String value()
    {
        return type;
    }
}
