/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-netty $Id$ $Revision$ Last Changed by SJ at
 * 2015年10月8日 下午2:28:00 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年10月8日
 * Initailized
 */
package cn.com.sand.component.network.netty.codec;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.network.netty.NettyConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

/**
 * 按报文长度解码器
 * 
 * @ClassName ：MultiFixedLengthDecoder
 * @author : SJ
 * @Date : 2015年10月8日 下午2:28:00
 * @version 2.0.0
 * 
 */
public class MultiFixedLengthDecoder extends ByteToMessageDecoder
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected final void decode(ChannelHandlerContext ctx, ByteBuf in,
            List<Object> out) throws Exception
    {
        Object decoded = decode(ctx, in);
        if (decoded != null)
        {
            out.add(decoded);
        }
    }

    /**
     * Create a frame out of the {@link ByteBuf} and return it.
     * 
     * @param ctx
     *            the {@link ChannelHandlerContext} which this
     *            {@link ByteToMessageDecoder} belongs to
     * @param in
     *            the {@link ByteBuf} from which to read data
     * @return frame the {@link ByteBuf} which represent the frame or
     *         {@code null} if no frame could be created.
     */
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
            throws Exception
    {
        if (in.readableBytes() < NettyConstants.HEAD_LENGTH)
        {
            return null;
        }
        else
        {
            ByteBuf len = in.readBytes(NettyConstants.HEAD_LENGTH);
            in.resetReaderIndex();
            int headSize = Integer.valueOf(len.toString(CharsetUtil.UTF_8));
            int frameLength = headSize + NettyConstants.HEAD_LENGTH;

            if (in.readableBytes() != (frameLength))
            {
                return null;
            }
//            String traceId = IdGenHelper.GetTraceId();
//            JSONObject traceInfo = new JSONObject();
//            traceInfo.put(LogConstants.LOG_M_TRACEID, traceId);
//            traceInfo.put(LogConstants.LOG_M_SYSID, "00");
//            traceInfo.put(LogConstants.LOG_M_SYSNAME, "adapter");
//            traceInfo
//                    .put(LogConstants.LOG_M_STARTTIME,
//                            DateTimeUtils
//                                    .getCurrentDateToString(DateTimeUtils.LONG_DATA_TIME_FORMAT));

//            Thread.currentThread().setName(traceInfo.toJSONString());
            logger.info(in.toString(CharsetUtil.UTF_8));
            return in.readBytes(frameLength);
        }
    }
}
