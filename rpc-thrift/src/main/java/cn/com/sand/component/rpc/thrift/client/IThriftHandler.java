/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-thrift $Id$ $Revision$ Last Changed by SJ at
 * 2015年10月21日 下午6:37:43 $URL$
 *
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年10月21日
 * Initailized
 */
package cn.com.sand.component.rpc.thrift.client;

/**
 *
 * @ClassName ：AbstractThriftHandler
 * @author : SJ
 * @Date : 2015年10月21日 下午6:37:43
 * @version 2.0.0
 *
 */
public interface IThriftHandler<Req, Resp>
{
    public String doHandle(final String request);

    public Req packet(String request);

    public String unPacket(Resp response);
}
