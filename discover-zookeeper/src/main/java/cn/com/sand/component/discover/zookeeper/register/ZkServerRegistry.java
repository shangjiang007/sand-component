/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * discover-zookeeper $Id$ $Revision$ Last Changed by SJ at 2016年2月22日 下午2:57:24
 * $URL$
 *
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2016年2月22日
 * Initailized
 */
package cn.com.sand.component.discover.zookeeper.register;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.discover.zookeeper.core.listener.ServConnectionStateListener;
import cn.com.sand.component.discover.zookeeper.core.node.NodeHelper;
import cn.com.sand.component.discover.zookeeper.core.node.NodeManager;
import cn.com.sand.component.discover.zookeeper.core.node.NodeType;
import cn.com.sand.component.discover.zookeeper.exception.ZkException;

/**
 * 服务端注册（zookeeper方式）
 *
 * @ClassName ：ZkServerRegistry
 * @author : SJ
 * @Date : 2016年2月22日 下午2:57:24
 * @version 2.0.0
 *
 */
public class ZkServerRegistry
{

    private static final Logger logger = LoggerFactory.getLogger(ZkServerRegistry.class);

    /** {@link CuratorFramework} */
    private final CuratorFramework zookeeper;

    private String servId;

    private NodeManager nodeManager;

    /**
     * @param zookeeper
     * @param servId
     */
    public ZkServerRegistry(CuratorFramework zookeeper, String servId)
    {
        this.zookeeper = zookeeper;
        this.servId = servId;
        nodeManager = new NodeManager(zookeeper);
    }

    public void register(String config) throws ZkException
    {
        if (zookeeper.getState() == CuratorFrameworkState.LATENT)
        {
            zookeeper.start();
            logger.info("zookeeper start success...");
            zookeeper.newNamespaceAwareEnsurePath(NodeHelper.getNameSpace(servId,NodeType.PROVIDERS));
        }
        String nameSpace = NodeHelper.getNameSpace(servId,NodeType.PROVIDERS);
        String cfg = NodeHelper.getConfigPath(nameSpace, config);
        nodeManager.createConfig(nameSpace, cfg);
        ServConnectionStateListener listener = new ServConnectionStateListener(nodeManager, cfg);
        zookeeper.getConnectionStateListenable().addListener(listener);
    }

    public void unregister()
    {
        zookeeper.close();
    }

}