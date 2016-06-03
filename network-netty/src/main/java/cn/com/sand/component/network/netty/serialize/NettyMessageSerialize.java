/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : multichannel-core-common-netty
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2015年9月25日 下午5:32:56
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2015年9月25日        Initailized
 */
package cn.com.sand.component.network.netty.serialize;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 *
 * @ClassName ：NettyMessageSerialize
 * @author : SJ
 * @Date : 2015年9月25日 下午5:32:56
 * @version 2.0.0
 *
 */
public interface NettyMessageSerialize
{
    
    public ChannelPipeline getPipeline(SocketChannel ch);
}
