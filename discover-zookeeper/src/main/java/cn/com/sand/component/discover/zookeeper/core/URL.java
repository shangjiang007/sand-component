/**
 * Copyright : http://www.sandpay.com.cn , 2016年4月27日
 * Project : athena
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年4月27日 下午3:33:46
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年4月27日        Initailized
 */
package cn.com.sand.component.discover.zookeeper.core;

import cn.com.sand.component.discover.zookeeper.common.Constants;

/**
 *
 * @ClassName ：URL
 * @author : SJ
 * @Date : 2016年4月27日 下午3:33:46
 * @version 1.0.0
 *
 */
public class URL
{
    private String id;

    private String ip;

    private String port;

    private String node;

    private String category;

    private String version;

    private String protocol;

    private int    timeout;

    private int    retries;

    private int    callTimes;

    private long   timestamp;

    public URL()
    {

    }

    public URL(String ip, String node)
    {
        this.ip = ip;
        this.node = node;
        this.timestamp = getTimestamp();
    }

    public URL(String ip, String port, String node)
    {
        this.ip = ip;
        this.port = port;
        this.node = node;
    }

    public URL(String node)
    {
        this.node = node;
    }

    public String genAddress()
    {
        return getNode() + Constants.ZK_COLON_DEFAULT + getIp() + Constants.ZK_COLON_DEFAULT + getTimestamp();
    }

    /**
     * id
     *
     * @return the id
     * @since 1.0.0
     */

    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * ip
     *
     * @return the ip
     * @since 1.0.0
     */

    public String getIp()
    {
        return ip;
    }

    /**
     * @param ip
     *            the ip to set
     */
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    /**
     * port
     *
     * @return the port
     * @since 1.0.0
     */

    public String getPort()
    {
        return port;
    }

    /**
     * @param port
     *            the port to set
     */
    public void setPort(String port)
    {
        this.port = port;
    }

    /**
     * node
     *
     * @return the node
     * @since 1.0.0
     */

    public String getNode()
    {
        return node;
    }

    /**
     * @param node
     *            the node to set
     */
    public void setNode(String node)
    {
        this.node = node;
    }

    /**
     * category
     *
     * @return the category
     * @since 1.0.0
     */

    public String getCategory()
    {
        return category;
    }

    /**
     * @param category
     *            the category to set
     */
    public void setCategory(String category)
    {
        this.category = category;
    }

    /**
     * version
     *
     * @return the version
     * @since 1.0.0
     */

    public String getVersion()
    {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version)
    {
        this.version = version;
    }

    /**
     * protocol
     *
     * @return the protocol
     * @since 1.0.0
     */

    public String getProtocol()
    {
        return protocol;
    }

    /**
     * @param protocol
     *            the protocol to set
     */
    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }

    /**
     * timeout
     *
     * @return the timeout
     * @since 1.0.0
     */

    public int getTimeout()
    {
        return timeout;
    }

    /**
     * @param timeout
     *            the timeout to set
     */
    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }

    /**
     * retries
     *
     * @return the retries
     * @since 1.0.0
     */

    public int getRetries()
    {
        return retries;
    }

    /**
     * @param retries
     *            the retries to set
     */
    public void setRetries(int retries)
    {
        this.retries = retries;
    }

    /**
     * callTimes
     *
     * @return the callTimes
     * @since 1.0.0
     */

    public int getCallTimes()
    {
        return callTimes;
    }

    /**
     * @param callTimes
     *            the callTimes to set
     */
    public void setCallTimes(int callTimes)
    {
        this.callTimes = callTimes;
    }

    public long getTimestamp()
    {
        return timestamp == 0 ? System.currentTimeMillis() : timestamp;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "{id:" + id + ", ip:" + ip + ", port:" + port + ", node:" + node + ", category:" + category
                + ", version:" + version + ", protocol:" + protocol + ", timeout:" + timeout + ", retries:" + retries
                + ", callTimes:" + callTimes + ", timestamp:" + timestamp + "}";
    }

}
