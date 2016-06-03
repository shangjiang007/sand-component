/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-netty $Id$ $Revision$ Last Changed by SJ at
 * 2015年10月9日 下午2:02:21 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年10月9日
 * Initailized
 */
package cn.com.sand.component.network.netty.codec;

import java.nio.charset.Charset;
import java.util.List;

import cn.com.sand.component.network.netty.NettyConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 *
 * @ClassName ：MultiStringDecoder
 * @author : SJ
 * @Date : 2015年10月9日 下午2:02:21
 * @version 2.0.0
 *
 */
@Sharable
public class MultiStringDecoder extends MessageToMessageDecoder<ByteBuf>
{
    // TODO Use CharsetDecoder instead.
    private final Charset charset;

    /**
     * Creates a new instance with the current system character set.
     */
    public MultiStringDecoder()
    {
        this(Charset.defaultCharset());
    }

    /**
     * Creates a new instance with the specified character set.
     */
    public MultiStringDecoder(Charset charset)
    {
        if (charset == null)
        {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
            List<Object> out) throws Exception
    {
        String req = msg.toString(charset);
        out.add(req.substring(NettyConstants.HEAD_LENGTH));
    }
}