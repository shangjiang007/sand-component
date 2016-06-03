/**
 * Copyright : http://www.sandpay.com.cn , 2016年5月9日 Project :
 * discover-zookeeper $Id$ $Revision$ Last Changed by SJ at 2016年5月9日 下午1:34:54
 * $URL$
 *
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2016年5月9日
 * Initailized
 */
package cn.com.sand.component.discover.zookeeper.core.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.discover.zookeeper.core.node.NodeManager;

/**
 * 服务端监听器
 *
 * @ClassName ：ServConnectionStateListener
 * @author : SJ
 * @Date : 2016年5月9日 下午1:34:54
 * @version 1.0.0
 *
 */
public class ServConnectionStateListener implements ConnectionStateListener
{
    private static final Logger logger = LoggerFactory.getLogger(ServConnectionStateListener.class);
    private String              config;
    private final NodeManager   nodeManager;

    public ServConnectionStateListener(NodeManager nodeManager, String config)
    {
        this.config = config;
        this.nodeManager = nodeManager;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.curator.framework.state.ConnectionStateListener#stateChanged
     * (org.apache.curator.framework.CuratorFramework,
     * org.apache.curator.framework.state.ConnectionState)
     */
    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState)
    {
        if (connectionState == ConnectionState.LOST)
        {// session 过期
            while (true)
            {
                try
                {
                    if (curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut())
                    {
                        if (nodeManager.createEpheNode(config))
                        {
                            break;
                        }
                    }
                }
                catch (Exception e)
                {
                    logger.error(e.getMessage(), e);
                    break;
                }
            }
        }
    }

}
