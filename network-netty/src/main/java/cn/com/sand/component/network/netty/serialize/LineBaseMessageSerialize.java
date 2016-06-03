/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : multichannel-core-common-netty
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2015年10月10日 下午1:51:19
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2015年10月10日        Initailized
 */
package cn.com.sand.component.network.netty.serialize;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 *
 * @ClassName ：LineBaseMessageSerialize
 * @author : SJ
 * @Date : 2015年10月10日 下午1:51:19
 * @version 2.0.0
 *
 */
public class LineBaseMessageSerialize implements NettyMessageSerialize
{

    /* (non-Javadoc)
     * @see cn.com.sandpay.multichannel.core.common.netty.serialize.NettyMessageSerialize#getPipeline(io.netty.channel.socket.SocketChannel)
     */
    @Override
    public ChannelPipeline getPipeline(SocketChannel ch)
    {
        ChannelPipeline pipeline = ch.pipeline();
//        pipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0,4, 0, 4));
//        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
        pipeline.addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast("encoder",new StringEncoder(CharsetUtil.UTF_8));
        return pipeline;
    }

}
