/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : multichannel-core-gateway-api
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2015年8月13日 下午1:27:14
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2015年8月13日        Initailized
 */
package cn.com.sand.component.network.server;

/**
 *
 * @ClassName ：IServer
 * @author : SJ
 * @Date : 2015年8月13日 下午1:27:14
 * @version 2.0.0
 *
 */
public interface IServer
{
    public void start();
    
    public void stop();
    
    public boolean isStarted();
    
    public ServerType getType();
}
