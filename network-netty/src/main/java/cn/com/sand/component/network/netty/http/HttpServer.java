/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-netty $Id$ $Revision$ Last Changed by SJ at
 * 2015年9月17日 上午11:22:19 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年9月17日
 * Initailized
 */
package cn.com.sand.component.network.netty.http;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.network.netty.serialize.MsgSerialType;
import cn.com.sand.component.network.netty.serialize.NettySerializeFactory;
import cn.com.sand.component.network.server.ServerType;
import cn.com.sand.component.network.server.http.AbstractHttpServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * @ClassName ：HttpServer
 * @author : SJ
 * @Date : 2015年9月17日 上午11:22:19
 * @version 2.0.0
 *
 */
public class HttpServer extends AbstractHttpServer
{
    private Logger               logger = LoggerFactory.getLogger(getClass());
    private final int            port;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private ChannelFuture        channelFuture;
    private final int            backlog;

    public HttpServer(int port) throws UnknownHostException
    {
        this(port, "http-server");
    }

    public HttpServer(int port, int boss, int worker, int backlog)
    {
        this(port, boss, worker, backlog, "http-server");
    }

    public HttpServer(int port, String serverName) throws UnknownHostException
    {
        this(port, Runtime.getRuntime().availableProcessors() + 1,
                Runtime.getRuntime().availableProcessors() * 2 + 1, 1024,
                serverName);
    }

    public HttpServer(int port, int boss, int worker, int backlog,
            String serverName)
    {
        super(ServerType.HTTP, port, serverName);
        this.port = port;
        this.backlog = backlog;
        this.bossGroup = new NioEventLoopGroup(boss);
        this.workerGroup = new NioEventLoopGroup(worker);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.com.sandpay.multichannel.core.common.server.AbstractServer#startInner(
     * )
     */
    public void startInner(final ChannelInboundHandlerAdapter adapter)
    {

        try
        {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, backlog)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        /*
                         * (non-Javadoc)
                         * 
                         * @see
                         * io.netty.channel.ChannelInitializer#initChannel(io.
                         * netty.channel.Channel)
                         */
                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception
                        {
                            ChannelPipeline pipeline = NettySerializeFactory
                                    .getMessageSeri(MsgSerialType.HTTP)
                                    .getPipeline(ch);
                            pipeline.addLast(adapter);

                        }
                    });
            channelFuture = b.bind(port).sync();

            logger.info("server start successful...");
        }
        catch (InterruptedException e)
        {
            logger.error("server start fail: " + e.getMessage());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.com.sandpay.multichannel.core.common.server.AbstractServer#stopInner()
     */
    @Override
    public void stopInner()
    {
        try
        {
            channelFuture.channel().closeFuture().sync();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            logger.info("server stop successful...");
        }
        catch (InterruptedException e)
        {
            logger.info("server stop fail: " + e.getMessage());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.com.sandpay.multichannel.core.common.server.AbstractServer#startInner(
     * )
     */
    @Override
    public void startInner()
    {
        // TODO Auto-generated method stub

    }

}
