/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-netty $Id$ $Revision$ Last Changed by SJ at
 * 2015年10月8日 下午2:17:01 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年10月8日
 * Initailized
 */
package cn.com.sand.component.network.netty.serialize;

import cn.com.sand.component.network.netty.codec.MultiFixedLengthDecoder;
import cn.com.sand.component.network.netty.codec.MultiStringDecoder;
import cn.com.sand.component.network.netty.codec.MultiStringEncoder;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

/**
 *
 * @ClassName ：FixedLengthMessageSerialize
 * @author : SJ
 * @Date : 2015年10月8日 下午2:17:01
 * @version 2.0.0
 *
 */
public class LengthFieldMessageSerialize implements NettyMessageSerialize
{

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.sandpay.multichannel.core.common.netty.serialize.
     * NettyMessageSerialize#getPipeline(io.netty.channel.socket.SocketChannel)
     */
    @Override
    public ChannelPipeline getPipeline(SocketChannel ch)
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("logHandler", new LoggingHandler(LogLevel.DEBUG))
                .addLast("frameDecoder", new MultiFixedLengthDecoder())
                .addLast("decoder",new MultiStringDecoder(CharsetUtil.UTF_8))
                .addLast("encoder",new MultiStringEncoder(CharsetUtil.UTF_8));
        return pipeline;
    }

}
