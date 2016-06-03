/**
 * Copyright : http://www.sandpay.com.cn , 2016年4月21日
 * Project : monitor-zookeeper
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年4月21日 上午9:42:32
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年4月21日        Initailized
 */
package cn.com.sand.component.monitor.zookeeper.core;

import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.monitor.zookeeper.common.ClientHostCacheSet;
import cn.com.sand.component.monitor.zookeeper.common.Constants;
import cn.com.sand.component.monitor.zookeeper.common.NodeBean;
import cn.com.sand.component.monitor.zookeeper.common.ServHostCacheSet;
import cn.com.sand.component.monitor.zookeeper.exception.ZkException;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @ClassName ：MonitorRegisty
 * @author : SJ
 * @Date : 2016年4月21日 上午9:42:32
 * @version 1.0.0
 *
 */
public class MonitorRegisty
{
    private final Logger             logger          = LoggerFactory.getLogger(getClass());

    /** 服务端路径缓存 */
    private List<PathChildrenCache>  servCachedPaths = new ArrayList<>();
    /** 客户端路径缓存 */
    private PathChildrenCache        clientCachedPath;

    /** {@link CuratorFramework} */
    private final CuratorFramework   zookeeper;

    private final ClientHostCacheSet clientHostSet   = new ClientHostCacheSet();

    private final ServHostCacheSet   servHostSet     = new ServHostCacheSet();

    /** {@link NodeBean} */
    private final NodeBean           nodeBean;

    /** 锁对象 */
    private final Object             lock            = new Object();

    public MonitorRegisty(CuratorFramework zookeeper, NodeBean nodeBean)
    {
        this.zookeeper = zookeeper;
        this.nodeBean = nodeBean;
    }

    public void monitor()
    {
        // 如果zk尚未启动,则启动
        if (zookeeper.getState() == CuratorFrameworkState.LATENT)
        {
            zookeeper.start();
        }

        // 构建zk节点
        addListener();
        buildPathClients();
        buildPathChildrenCache(Constants.ZK_NAMESPACE_CLIENTS, true);
        // buildPathChildrenCache(Constants.ZK_NAMESPACE_SERVERS, true);
        build(Constants.ZK_NAMESPACE_CLIENTS);
        build(Constants.ZK_NAMESPACE_SERVERS);

        try
        {
            clientCachedPath.start(StartMode.POST_INITIALIZED_EVENT);
            for (int i = 0; i < servCachedPaths.size(); i++)
            {
                servCachedPaths.get(i).start(StartMode.POST_INITIALIZED_EVENT);
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 添加监听器，防止网络异常或者zookeeper挂掉的情况
     * <p>
     *
     * @param config
     *            配置信息
     */
    private void addListener()
    {
        zookeeper.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState)
            {
                if (connectionState == ConnectionState.LOST)
                {
                    while (true)
                    {
                        try
                        {
                            if (curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut())
                            {
                                if (buildPathClients())
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
        });
    }

    /**
     * 创建monitor节点
     * <p>
     *
     * @param config
     *            配置信息
     * @throws ZkException
     * @return 是否创建节点
     */
    private boolean buildPathClients() throws ZkException
    {
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(Constants.ZK_SEPARATOR_DEFAULT).append(Constants.ZK_NAMESPACE_MONITOR)
                .append(Constants.ZK_SEPARATOR_DEFAULT).append(nodeBean.getId());
        if (StringUtils.isEmpty(nodeBean.getId()))
        {
            try
            {
                zookeeper.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                        .forPath(pathBuilder.toString(), nodeBean.getDesc().getBytes(Constants.UTF8));
                return true;
            }
            catch (Exception e)
            {
                String message = MessageFormat.format("Create node error in the path : {0}", pathBuilder.toString());
                logger.error(message, e);
                throw new ZkException(message, e);
            }
        }
        else
        {
            try
            {
                // 注意：zk重启的过程中，节点可能会存在
                if (zookeeper.checkExists().forPath(pathBuilder.toString()) == null)
                {
                    zookeeper.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                            .forPath(pathBuilder.toString(), nodeBean.getDesc().getBytes(Constants.UTF8));
                    return true;
                }
            }
            catch (Exception e)
            {
                String message = MessageFormat.format("Create node error in the path : {0}", pathBuilder.toString());
                logger.error(message, e);
                throw new ZkException(message, e);
            }
        }
        return false;
    }

    private PathChildrenCache getPathCache(Boolean cacheData)
    {
        clientCachedPath = new PathChildrenCache(zookeeper, Constants.ZK_SEPARATOR_DEFAULT
                + Constants.ZK_NAMESPACE_CLIENTS, cacheData);
        return clientCachedPath;
    }

    /**
     * 创建缓存路径
     * <p>
     *
     * @param cacheData
     */
    private void buildPathChildrenCache(final String nameSpace, Boolean cacheData)
    {
        final PathChildrenCache cache = getPathCache(cacheData);

        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
            {
                PathChildrenCacheEvent.Type eventType = event.getType();
                switch (eventType)
                {
                    case CONNECTION_SUSPENDED:
                    case CONNECTION_LOST:
                        logger.error("Connection error,waiting...");
                        return;
                    default:
                }
                // 任何节点的时机数据变动,都会rebuild,此处为一个"简单的"做法.
                try
                {
                    cache.rebuild();
                    rebuild(cache, nameSpace);
                }
                catch (Exception e)
                {
                    logger.error("CachedPath rebuild error!", e);
                }
            }
        });
    }

    /**
     * 创建缓存路径
     * <p>
     *
     * @param cacheData
     */
    private PathChildrenCache buildServPathChildrenCache(final String nameSpace, final String node, Boolean cacheData)
    {
        final PathChildrenCache cache = new PathChildrenCache(zookeeper, Constants.ZK_SEPARATOR_DEFAULT
                + Constants.ZK_NAMESPACE_SERVERS + Constants.ZK_SEPARATOR_DEFAULT + node, cacheData);

        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
            {
                PathChildrenCacheEvent.Type eventType = event.getType();
                switch (eventType)
                {
                    case CONNECTION_SUSPENDED:
                    case CONNECTION_LOST:
                        logger.error("Connection error,waiting...");
                        return;
                    default:
                }
                // 任何节点的时机数据变动,都会rebuild,此处为一个"简单的"做法.
                try
                {
                    cache.rebuild();
                    rebuildServ(cache, node, nameSpace);
                }
                catch (Exception e)
                {
                    logger.error("CachedPath rebuild error!", e);
                }
            }
        });
        return cache;
    }

    /**
     * 构建服务信息 <br>
     * <br>
     * 注意：构建时直接操作zookeeper，不使用PathChildrenCache,原因参考：{@link PathChildrenCache}
     * <p>
     *
     * @throws ZkException
     */
    private void build(String nameSpace) throws ZkException
    {
        List<String> childrenList = null;
        String path = null;
        if (Constants.ZK_NAMESPACE_CLIENTS.equals(nameSpace))
        {
            path = Constants.ZK_SEPARATOR_DEFAULT + Constants.ZK_NAMESPACE_CLIENTS;
        }
        else if (Constants.ZK_NAMESPACE_SERVERS.equals(nameSpace))
        {
            path = Constants.ZK_SEPARATOR_DEFAULT + Constants.ZK_NAMESPACE_SERVERS;
        }
        try
        {
            childrenList = zookeeper.getChildren().forPath(path);
        }
        catch (Exception e)
        {
            String message = MessageFormat.format("Get children node error in the path : {0}", path);
            logger.error(message, e);
            throw new ZkException(message, e);
        }

        if (childrenList == null)
        {
            logger.error("Not find a service in zookeeper!");
            throw new ZkException("Not find a service in zookeeper!");
        }

        List<NodeBean> current = new ArrayList<NodeBean>();
        for (String children : childrenList)
        {
            String data = "";
            if (Constants.ZK_NAMESPACE_CLIENTS.equals(nameSpace))
            {
                data = getData(children);
            }
            else if (Constants.ZK_NAMESPACE_SERVERS.equals(nameSpace))
            {
                PathChildrenCache cache = buildServPathChildrenCache(nameSpace, children, true);
                servCachedPaths.add(cache);
                data = getChildren(children);
            }

            NodeBean node = new NodeBean(children, data);
            current.add(node);
            // current.add(ServeNodeUtils.transfer(data, children));
        }
        freshContainer(current, nameSpace);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * cn.com.sand.component.discover.zookeeper.register.IRegistry#getData(java.
     * lang.String)
     */
    public String getChildren(String node)
    {
        String path = Constants.ZK_SEPARATOR_DEFAULT + Constants.ZK_NAMESPACE_SERVERS + Constants.ZK_SEPARATOR_DEFAULT
                + node;

        try
        {
            List<String> list = zookeeper.getChildren().forPath(path);
            String data = "";
            for (int i = 0; i < list.size(); i++)
            {
                data = data + list.get(i) + "|";
            }
            String str = data.substring(0, data.length() - 1);
            return URLDecoder.decode(str, Constants.UTF8);
        }
        catch (Exception e)
        {
            logger.debug("node is not exsit: " + node);
        }
        return null;
    }

    public String getData(String node)
    {
        String path = Constants.ZK_SEPARATOR_DEFAULT + Constants.ZK_NAMESPACE_CLIENTS + Constants.ZK_SEPARATOR_DEFAULT
                + node;

        try
        {
            byte[] byt = zookeeper.getData().forPath(path);
            return new String(byt, Constants.UTF8);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重新构建服务信息
     * <p>
     */
    protected void rebuildServ(PathChildrenCache cache, String nodeId, String nameSpace)
    {
        List<ChildData> children = cache.getCurrentData();
        List<NodeBean> current = new ArrayList<NodeBean>();
        try
        {
            for (ChildData data : children)
            {
                String path = data.getPath();
                String desc = path.substring(path.lastIndexOf(Constants.ZK_SEPARATOR_DEFAULT));
                logger.debug("node ---> ", nodeId);
                NodeBean node = new NodeBean(nodeId, URLDecoder.decode(desc, Constants.UTF8));
                current.add(node);
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        freshContainer(current, nameSpace);
    }

    /**
     * 重新构建服务信息
     * <p>
     */
    protected void rebuild(PathChildrenCache cache, String nameSpace)
    {
        List<ChildData> children = cache.getCurrentData();
        List<NodeBean> current = new ArrayList<NodeBean>();
        for (ChildData data : children)
        {
            String path = data.getPath();
            String id = path.substring(path.lastIndexOf(Constants.ZK_SEPARATOR_DEFAULT));
            byte[] byt = data.getData();
            String desc = new String(byt);
            logger.debug("node ---> ", id);
            NodeBean node = new NodeBean(id, desc);
            current.add(node);
        }
        freshContainer(current, nameSpace);
    }

    /**
     * 刷新容器
     * <p>
     *
     * @param current
     */
    private void freshContainer(List<NodeBean> current, String nameSpace)
    {
        synchronized (lock)
        {
            if (Constants.ZK_NAMESPACE_SERVERS.equals(nameSpace))
            {
                servHostSet.replaceWithList(current);
            }
            else if (Constants.ZK_NAMESPACE_CLIENTS.equals(nameSpace))
            {
                clientHostSet.replaceWithList(current);
            }

        }
    }

    public ClientHostCacheSet getClientSet()
    {
        return clientHostSet;
    }

    public ServHostCacheSet getServSet()
    {
        return servHostSet;
    }

    public static void main(String[] args) throws Exception
    {
        CuratorFrameworkManager f = new CuratorFrameworkManager("172.28.250.86:2181");
        f.start();

        CuratorFramework curatorFramework = f.getCuratorFramework();
        NodeBean node = new NodeBean("monitorServ", "123");
        MonitorRegisty mr = new MonitorRegisty(curatorFramework, node);
        mr.monitor();
        while (true)
        {
            System.out.println("client =======" + mr.getClientSet());

            Set<NodeBean> call = mr.getClientSet().getAll();
            for (NodeBean nodeBean : call)
            {
                System.out.println("client ===  " + JSONObject.toJSONString(nodeBean));
            }

            System.out.println("service =======" + mr.getServSet());
            Set<NodeBean> sall = mr.getServSet().getAll();
            for (NodeBean nodeBean : sall)
            {
                System.out.println("service ===  " + JSONObject.toJSONString(nodeBean));
            }

            Thread.sleep(3000);
        }

    }
}
