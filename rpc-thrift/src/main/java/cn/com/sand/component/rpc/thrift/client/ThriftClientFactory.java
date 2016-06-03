/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-deploy-adapter $Id$ $Revision$ Last Changed by SJ at
 * 2015年10月21日 上午11:07:52 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2015年10月21日
 * Initailized
 */
package cn.com.sand.component.rpc.thrift.client;

/**
 *
 * @ClassName ：InterceptorProcesser
 * @author : SJ
 * @Date : 2015年10月21日 上午11:07:52
 * @version 2.0.0
 *
 */
public class ThriftClientFactory
{
    private static ThriftClientFactory instance;
    private String                     host;
    private int                        port;
    private int                        timeout;

    private ThriftClientFactory()
    {
    }

    public static ThriftClientFactory builder()
    {
        if (instance == null)
        {
            instance = new ThriftClientFactory();
        }
        return instance;
    }

    public ThriftClient<?, ?> create()
    {
        if (timeout == 0)
        {
            timeout = 20 * 1000;
        }
        return new SandThriftClient(host, port, timeout);
    }

    public ThriftClientFactory host(String host)
    {
        this.host = host;
        return this;
    }

    public ThriftClientFactory port(int port)
    {
        this.port = port;
        return this;
    }

    public ThriftClientFactory timeout(int timeout)
    {
        this.timeout = timeout;
        return this;
    }

}
