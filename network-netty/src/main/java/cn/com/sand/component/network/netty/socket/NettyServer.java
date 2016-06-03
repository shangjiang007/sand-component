/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-gateway-netty $Id$ $Revision$ Last Changed by SJ at
 * 2015年8月13日 下午4:57:23 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年8月13日
 * Initailized
 */
package cn.com.sand.component.network.netty.socket;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.network.netty.serialize.MsgSerialType;
import cn.com.sand.component.network.netty.serialize.NettySerializeFactory;
import cn.com.sand.component.network.server.ServerType;
import cn.com.sand.component.network.server.socket.AbstractSocketServer;
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
 * @ClassName ：NettyServer
 * @author : SJ
 * @Date : 2015年8月13日 下午4:57:23
 * @version 2.0.0
 *
 */
public class NettyServer extends AbstractSocketServer
{
    private Logger               logger = LoggerFactory.getLogger(getClass());
    private final SocketAddress  endpoint;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private ChannelFuture        channelFuture;
    private final int            backlog;

    public NettyServer(int port) throws UnknownHostException
    {
        this(new InetSocketAddress(port), "netty4-server");
    }

    public NettyServer(String bind, int port) throws UnknownHostException
    {
        this(new InetSocketAddress(bind, port), "netty4-server");
    }

    public NettyServer(int port, String serviceName) throws UnknownHostException
    {
        this(new InetSocketAddress(port), serviceName);
    }

    public NettyServer(String bind, int port, String serviceName)
            throws UnknownHostException
    {
        this(new InetSocketAddress(bind, port), serviceName);
    }

    public NettyServer(int port, int boss, int worker, int backlog)
    {
        this(new InetSocketAddress(port), boss, worker, backlog,
                "netty4-server");
    }

    NettyServer(int port, int boss, int work, int backlog, String serverName)
    {
        this(new InetSocketAddress(port), boss, work, backlog, serverName);
    }

    public NettyServer(InetSocketAddress socketAddress, String name)
            throws UnknownHostException
    {
        this(socketAddress, Runtime.getRuntime().availableProcessors() + 1,
                Runtime.getRuntime().availableProcessors() * 2 + 1, 1024, name);
    }

    public NettyServer(InetSocketAddress address, int boss, int worker,
            int backlog, String name)
    {
        super(ServerType.SOCKET, address.getPort(), name);
        this.endpoint = address;
        this.backlog = backlog;
        this.bossGroup = new NioEventLoopGroup(boss);
        this.workerGroup = new NioEventLoopGroup(worker);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.com.sand.multichannel.core.gateway.api.AbstractServer#startInner()
     */
    public void startInner(final ChannelInboundHandlerAdapter adapter,final MsgSerialType serType)
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
                                    .getMessageSeri(serType)
                                    .getPipeline(ch);
                            pipeline.addLast(adapter);

                        }
                    });
            channelFuture = b.bind(endpoint).sync();

            logger.info("server start successful...");
            channelFuture.channel().closeFuture().sync();
            
        }
        catch (InterruptedException e)
        {
            logger.error("server start fail: " + e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.sand.multichannel.core.gateway.api.AbstractServer#stopInner()
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
