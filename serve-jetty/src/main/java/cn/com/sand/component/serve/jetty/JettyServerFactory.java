/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-common-jetty $Id$ $Revision$ Last Changed by SJ at
 * 2015年10月12日 下午1:56:17 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年10月12日
 * Initailized
 */
package cn.com.sand.component.serve.jetty;

import org.eclipse.jetty.server.Server;

/**
 *
 * @ClassName ：JettyServerFactory
 * @author : SJ
 * @Date : 2015年10月12日 下午1:56:17
 * @version 2.0.0
 *
 */
public class JettyServerFactory
{
    private static JettyServerFactory instance;
    private static int                servPort = 8080;

    private JettyServerFactory()
    {
    }

    public static synchronized JettyServerFactory getInstance()
    {
        if (instance == null)
        {
            instance = new JettyServerFactory();
        }
        return instance;
    }

    public Server create()
    {
        return new Server(servPort);
    }

    public JettyServerFactory port(int port)
    {
        servPort = port;
        return this;
    }
}
