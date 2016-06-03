/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-gateway-api $Id$ $Revision$ Last Changed by SJ at
 * 2015年8月13日 下午2:05:25 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年8月13日
 * Initailized
 */
package cn.com.sand.component.network.server.socket;

import cn.com.sand.component.network.server.AbstractServer;
import cn.com.sand.component.network.server.IServer;
import cn.com.sand.component.network.server.ServerType;

/**
 *
 * @ClassName ：AbstractSocketServer
 * @author : SJ
 * @Date : 2015年8月13日 下午2:05:25
 * @version 2.0.0
 *
 */
public abstract class AbstractSocketServer extends AbstractServer
        implements IServer
{
    protected final int port;

    protected final String serverName;

    /**
     * @param type
     */
    public AbstractSocketServer(ServerType type, int port, String serverName)
    {
        super(type);
        this.port = port;
        this.serverName = serverName;
    }

    public final int getPort()
    {
        return port;
    }
}
