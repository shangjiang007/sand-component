/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-netty $Id$ $Revision$ Last Changed by SJ at
 * 2015年9月17日 下午6:35:33 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年9月17日
 * Initailized
 */
package cn.com.sand.component.network.netty.adapter;

import com.alibaba.fastjson.JSONObject;

/**
 * 代理适配器
 * 
 * @ClassName ：ProxyApapter
 * @author : SJ
 * @Date : 2015年9月17日 下午6:35:33
 * @version 2.0.0
 *
 */
public interface ProxyApapter<Req, Resp>
{
    /**
     * 请求报文格式转换
     * 
     * @param request
     * @return
     */
    public JSONObject reqProcess(String cformat, JSONObject body);

    /**
     * 应答报文格式转换
     * 
     * @param response
     * @return
     */
    public String respProcess(JSONObject header, String tcode, Resp response);
}
