/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : multichannel-core-common-netty
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2015年9月25日 下午5:31:57
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
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 *
 * @ClassName ：HttpMessageSerialize
 * @author : SJ
 * @Date : 2015年9月25日 下午5:31:57
 * @version 2.0.0
 *
 */
public class HttpMessageSerialize implements NettyMessageSerialize
{

    /* (non-Javadoc)
     * @see cn.com.sandpay.multichannel.core.common.netty.serialize.NettyMessageSerialize#getPipeline(io.netty.channel.socket.SocketChannel)
     */
    @Override
    public ChannelPipeline getPipeline(SocketChannel ch)
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("logHandler",
                new LoggingHandler(LogLevel.DEBUG))
                .addLast(new HttpResponseEncoder())
                .addLast(new HttpRequestDecoder());
        return pipeline;
    }
    
}
