/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-thrift $Id$ $Revision$ Last Changed by SJ at
 * 2015年9月21日 上午11:04:25 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年9月21日
 * Initailized
 */
package cn.com.sand.component.rpc.thrift.service;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @ClassName ：ThriftServer
 * @author : SJ
 * @Date : 2015年9月21日 上午11:04:25
 * @version 2.0.0
 *
 */
public abstract class ThriftServer
{
    private Logger  logger = LoggerFactory.getLogger(getClass());
    private TServer server = null;

    private TNonblockingServerTransport serverTransport = null;

    private volatile boolean active = false;

    public void start(int port, int selectorThreads, int workThreads,
            int acceptQueueSizePerThread, String policy,
            int fixedThreadPoolSize)
    {
        try
        {
            serverTransport = new TNonblockingServerSocket(port);
            TTransportFactory transportFactory = new TFramedTransport.Factory();
            TProtocolFactory proFactory = new TCompactProtocol.Factory();
            TProcessor processor = this.getProcesser();
            TThreadedSelectorServer.Args.AcceptPolicy acceptPolicy = null;
            if ("FAIR".equals(policy))
            {
                acceptPolicy = TThreadedSelectorServer.Args.AcceptPolicy.FAIR_ACCEPT;
            }
            else if ("FAST".equals(policy))
            {
                acceptPolicy = TThreadedSelectorServer.Args.AcceptPolicy.FAST_ACCEPT;
            }
            TThreadedSelectorServer.Args serverArgs = new TThreadedSelectorServer.Args(
                    serverTransport).protocolFactory(proFactory)
                            .transportFactory(transportFactory)
                            .processor(processor)
                            .selectorThreads(selectorThreads)
                            .acceptQueueSizePerThread(acceptQueueSizePerThread)
                            .workerThreads(workThreads)
                            .executorService(Executors
                                    .newFixedThreadPool(fixedThreadPoolSize))
                            .acceptPolicy(acceptPolicy).stopTimeoutVal(60)
                            .stopTimeoutUnit(TimeUnit.SECONDS);
            logger.info(
                    "start server args:(AcceptPolicy=>{},AcceptQueueSizePerThread=>{},SelectorThreads=>{},WorkerThreads=>{})",
                    new Object[] { serverArgs.getAcceptPolicy(),
                            serverArgs.getAcceptQueueSizePerThread(),
                            serverArgs.getSelectorThreads(),
                            serverArgs.getWorkerThreads() });
            server = new TThreadedSelectorServer(serverArgs);
            logger.info("start server:{},port:{}, info:{} ",
                    new Object[] { serverTransport, port, this });
            active = true;
            server.serve();
        }
        catch (TTransportException e)
        {
            logger.error("start server:{} fail , cause by:{}",
                    new Object[] { this, e.getMessage() });
        }
    }

    public boolean isActive()
    {
        return active;
    }

    public abstract TProcessor getProcesser();

    public void stop()
    {
        logger.info("will stop server:{}", new Object[] { this });
        if (serverTransport != null)
            serverTransport.close();
        if (server != null && server.isServing())
            server.stop();
    }
}
