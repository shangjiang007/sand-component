/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-netty $Id$ $Revision$ Last Changed by SJ at
 * 2015年10月8日 下午2:31:44 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年10月8日
 * Initailized
 */
package cn.com.sand.component.network.netty.codec;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.network.netty.NettyConstants;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * 编码器
 * 
 * @ClassName ：MultiStringEncoder
 * @author : SJ
 * @Date : 2015年10月8日 下午2:31:44
 * @version 2.0.0
 * 
 */
@Sharable
public class MultiStringEncoder extends MessageToMessageEncoder<CharSequence>
{
    private Logger        logger = LoggerFactory.getLogger(getClass());
    // TODO Use CharsetEncoder instead.
    private final Charset charset;

    /**
     * Creates a new instance with the current system character set.
     */
    public MultiStringEncoder()
    {
        this(Charset.defaultCharset());
    }

    /**
     * Creates a new instance with the specified character set.
     */
    public MultiStringEncoder(Charset charset)
    {
        if (charset == null)
        {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, CharSequence msg,
            List<Object> out) throws Exception
    {
        if (msg.length() == 0)
        {
            return;
        }
        String str = msg.toString();
        int len = str.getBytes().length;
        String frameLength = StringUtils.leftPad(String.valueOf(len),
                NettyConstants.HEAD_LENGTH, "0");
        msg = frameLength + msg;
//        String name = Thread.currentThread().getName();
//        JSONObject traceInfo = GsonHelper.toObject(name, JSONObject.class);
//        traceInfo.put(LogConstants.LOG_M_ENDTIME, DateTimeUtils
//                .getCurrentDateToString(DateTimeUtils.LONG_DATA_TIME_FORMAT));
//        Thread.currentThread().setName(traceInfo.toJSONString());
        logger.info("send info ---> " + msg);
        out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg),
                charset));
    }
}
