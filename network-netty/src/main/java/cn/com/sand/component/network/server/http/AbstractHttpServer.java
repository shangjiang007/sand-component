/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-server $Id$ $Revision$ Last Changed by SJ at
 * 2015年9月17日 上午11:12:24 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年9月17日
 * Initailized
 */
package cn.com.sand.component.network.server.http;

import cn.com.sand.component.network.server.AbstractServer;
import cn.com.sand.component.network.server.IServer;
import cn.com.sand.component.network.server.ServerType;

/**
 *
 * @ClassName ：AbstractHttpServer
 * @author : SJ
 * @Date : 2015年9月17日 上午11:12:24
 * @version 2.0.0
 *
 */
public abstract class AbstractHttpServer extends AbstractServer
        implements IServer
{
    protected final int    port;
    protected final String serviceName;

    /**
     * @param type
     */
    public AbstractHttpServer(ServerType type, int port, String serviceName)
    {
        super(type);
        this.port = port;
        this.serviceName = serviceName;
    }

    public final int getPort()
    {
        return this.port;
    }

}
