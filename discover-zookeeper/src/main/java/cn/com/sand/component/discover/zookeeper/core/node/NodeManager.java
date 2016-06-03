/**
 * Copyright : http://www.sandpay.com.cn , 2016年5月9日 Project :
 * discover-zookeeper $Id$ $Revision$ Last Changed by SJ at 2016年5月9日 下午3:28:53
 * $URL$
 *
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2016年5月9日
 * Initailized
 */
package cn.com.sand.component.discover.zookeeper.core.node;

import java.text.MessageFormat;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.discover.zookeeper.exception.ZkException;

/**
 *
 * @ClassName ：NodeManager
 * @author : SJ
 * @Date : 2016年5月9日 下午3:28:53
 * @version 1.0.0
 *
 */
public class NodeManager
{
    private static Logger    logger = LoggerFactory.getLogger(NodeManager.class);
    private CuratorFramework curatorFramework;

    public NodeManager(CuratorFramework curatorFramework)
    {
        this.curatorFramework = curatorFramework;
    }

    public boolean createEpheNode(String config)
    {
        try
        {
            if (checkExists(config))
            {
                curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(config);
                logger.info("create node {} success", config);
                return true;
            }
        }
        catch (Exception e)
        {
            logger.error("create node {} error by {}", new Object[] { config, e.getMessage() });
            throw new ZkException("create node " + config, e);
        }
        return false;
    }

    public void createNameSpace(String nameSpace)
    {
        try
        {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(nameSpace);// .withACL(acls)
        }
        catch (Exception e)
        {
            String message = MessageFormat.format("Zookeeper error in the path : {}", nameSpace);
            logger.error(message, e);
            throw new ZkException(message, e);
        }
    }

    public void createConfig(String nameSpce, String config)
    {
        if (checkExists(nameSpce))
        {
            createNameSpace(nameSpce);
        }
        createEpheNode(config);
    }

    public void delete(String path)
    {
        try
        {
            curatorFramework.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
            logger.error("delete path: {} success...", path);
        }
        catch (Exception e)
        {
            logger.error("delete path: {} fail by {} ", new Object[] { path, e.getMessage() });
        }
    }

    public boolean checkExists(String path)
    {
        try
        {
            if (curatorFramework.checkExists().forPath(path) == null)
            {
                return true;
            }
        }
        catch (Exception e)
        {
            logger.error("checkExists path: {} fail by {} ", new Object[] { path, e.getMessage() });
        }
        return false;
    }

    public List<String> getChildrenList(String path)
    {
        try
        {
            return curatorFramework.getChildren().forPath(path);
        }
        catch (Exception e)
        {
            logger.error("get path: {} children fial by {}", new Object[] { path, e.getMessage() });
        }
        return null;
    }
}
