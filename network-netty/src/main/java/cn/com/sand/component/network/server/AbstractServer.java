/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-gateway-api $Id$ $Revision$ Last Changed by SJ at
 * 2015年8月13日 下午1:39:52 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年8月13日
 * Initailized
 */
package cn.com.sand.component.network.server;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @ClassName ：AbstractServer
 * @author : SJ
 * @Date : 2015年8月13日 下午1:39:52
 * @version 2.0.0
 *
 */
public abstract class AbstractServer implements IServer
{
    private Logger                 logger = LoggerFactory.getLogger(getClass());
    private final AtomicBoolean isStarted;

    private final ServerType type;

    /** 
    *  
    */
    public AbstractServer(ServerType type)
    {
        this.type = type;
        isStarted = new AtomicBoolean(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.sand.multichannel.core.gateway.api.IServer#start()
     */
    @Override
    public void start()
    {
        logger.info("server starting...");
        boolean isnStarted = isStarted.compareAndSet(false, true);
        if (!isnStarted)
        {
            throw new IllegalStateException("server is already started!");
        }
        startInner();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.sand.multichannel.core.gateway.api.IServer#stop()
     */
    @Override
    public void stop()
    {
        logger.info("server stoping...");
        synchronized (this)
        {
            if (!isStarted())
            {
                logger.info("the server is already stoped");
                return;
            }
            stopInner();
            isStarted.set(false);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.sand.multichannel.core.gateway.api.IServer#isStarted()
     */
    @Override
    public boolean isStarted()
    {

        return isStarted.get();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.sand.multichannel.core.gateway.api.IServer#getType()
     */
    @Override
    public ServerType getType()
    {
        return this.type;
    }

    public abstract void startInner();

    public abstract void stopInner();

}
