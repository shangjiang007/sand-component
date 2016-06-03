/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : multichannel-core-common-thrift
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2015年10月27日 下午3:33:19
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2015年10月27日        Initailized
 */
package cn.com.sand.component.rpc.thrift.client;

/**
 *
 * @ClassName ：ThriftClient
 * @author : SJ
 * @Date : 2015年10月27日 下午3:33:19
 * @version 2.0.0
 *
 */
public interface ThriftClient<Req, Resp>
{
    public Resp invoke(Req request);
}
