/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * discover-zookeeper $Id$ $Revision$ Last Changed by SJ at 2016年2月22日 下午3:00:56
 * $URL$
 *
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2016年2月22日
 * Initailized
 */
package cn.com.sand.component.discover.zookeeper.register;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.discover.zookeeper.common.Constants;
import cn.com.sand.component.discover.zookeeper.common.DynamicHostSet;
import cn.com.sand.component.discover.zookeeper.common.UrlUtils;
import cn.com.sand.component.discover.zookeeper.core.URL;
import cn.com.sand.component.discover.zookeeper.core.listener.ClientConnectionStateListener;
import cn.com.sand.component.discover.zookeeper.core.node.NodeHelper;
import cn.com.sand.component.discover.zookeeper.core.node.NodeManager;
import cn.com.sand.component.discover.zookeeper.core.node.NodeType;
import cn.com.sand.component.discover.zookeeper.exception.ZkException;

/**
 * 客户端注册（zookeeper方式） <br>
 * <br>
 * 使用Apache的Curator框架监控zookeeper节点的变化 <br>
 * 参考资料： <a href="http://www.cnblogs.com/hupengcool/p/3982301.html">使用Apache
 * Curator监控Zookeeper的Node和Path的状态</a>
 * <p>
 *
 * @ClassName ：ZkClientRegistry
 * @author : SJ
 * @Date : 2016年2月22日 下午3:00:56
 * @version 2.0.0
 *
 */
public class ZkClientRegistry
{

    private static final Logger logger = LoggerFactory.getLogger(ZkClientRegistry.class);

    /** {@link CuratorFramework} */
    private final CuratorFramework zookeeper;

    /** {@link DynamicHostSet} */
    private final DynamicHostSet configSet = new DynamicHostSet();

    private final String clientId;

    /** 锁对象 */
    private final Object lock = new Object();

    private NodeManager nodeManager;

    private String servId;

    /**
     * @param configPath
     * @param zookeeper
     * @param clientNode
     */
    public ZkClientRegistry(CuratorFramework zookeeper, String clientId)
    {
        this.zookeeper = zookeeper;
        this.clientId = clientId;
        nodeManager = new NodeManager(zookeeper);
    }

    public void register(String config) throws ZkException
    {
        // 如果zk尚未启动,则启动
        if (zookeeper.getState() == CuratorFrameworkState.LATENT)
        {
            zookeeper.start();
            logger.info("zookeeper start success...");
        }
        String nameSpace = NodeHelper.getNameSpace(clientId, NodeType.CONSUMERS);
        String cfg = NodeHelper.getConfigPath(nameSpace, config);
        nodeManager.createConfig(nameSpace, cfg);

        ClientConnectionStateListener listener = new ClientConnectionStateListener(nodeManager, cfg);
        zookeeper.getConnectionStateListenable().addListener(listener);

    }

    private List<String> getServConfig()
    {
        if (zookeeper.getState() == CuratorFrameworkState.LATENT)
        {
            zookeeper.start();
            logger.info("zookeeper start success...");
        }
        String nameSpace = getServSpace();
        List<String> cfgList = nodeManager.getChildrenList(nameSpace);
        return cfgList;
    }

    private PathChildrenCache buildPathChildrenCache(Boolean cacheData)
    {
        String path = getServSpace();
        final PathChildrenCache cachedPath = new PathChildrenCache(zookeeper, path, cacheData);
        cachedPath.getListenable().addListener(new PathChildrenCacheListener() {
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
                    cachedPath.rebuild();
                    rebuild(cachedPath.getCurrentData());
                }
                catch (Exception e)
                {
                    logger.error("CachedPath rebuild error!", e);
                }
            }
        });
        return cachedPath;
    }

    protected void rebuild(List<ChildData> children)
    {
        if (children != null && !children.isEmpty())
        {
            List<URL> list = new ArrayList<>();
            for (ChildData data : children)
            {
                String path = data.getPath();
                String config = path.substring(path.lastIndexOf(Constants.ZK_SEPARATOR_DEFAULT) + 1);
                URL cfg = UrlUtils.transfer(config);
                list.add(cfg);
            }
            freshContainer(list);
        }
    }

    public void addProvidListener(String servId)
    {
        this.servId = servId;
        List<String> list = getServConfig();
        List<URL> urList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)
        {
            URL cfg = UrlUtils.transfer(list.get(i));
            urList.add(cfg);
        }

        startCache();
        freshContainer(urList);
    }

    private void startCache()
    {
        try
        {
            PathChildrenCache cache = buildPathChildrenCache(true);
            cache.start(StartMode.POST_INITIALIZED_EVENT);
            // cachedPaths.add(cache);
        }
        catch (Exception e)
        {
            logger.warn("start cache error, by {}", e.getMessage());
        }

    }

    private void freshContainer(List<URL> current)
    {
        synchronized (lock)
        {
            configSet.replaceWithList(current);
        }
    }

    public DynamicHostSet getProviders()
    {
        return configSet;
    }

    private String getServSpace()
    {
        return NodeHelper.getNameSpace(servId, NodeType.PROVIDERS);
    }

}
