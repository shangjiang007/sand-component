/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * discover-zookeeper $Id$ $Revision$ Last Changed by SJ at 2016年5月17日
 * 上午10:15:31 $URL$
 * 
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2016年5月17日
 * Initailized
 */
package cn.com.sand.component.discover.zookeeper.monitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.discover.zookeeper.common.Constants;
import cn.com.sand.component.discover.zookeeper.common.UrlUtils;
import cn.com.sand.component.discover.zookeeper.core.CuratorFrameworkManager;
import cn.com.sand.component.discover.zookeeper.core.URL;
import cn.com.sand.component.discover.zookeeper.core.node.NodeHelper;
import cn.com.sand.component.discover.zookeeper.core.node.NodeManager;
import cn.com.sand.component.discover.zookeeper.core.node.NodeType;

/**
 *
 * @ClassName ：ZkMonitorManager
 * @author : SJ
 * @Date : 2016年5月17日 上午10:15:31
 * @version 2.0.0
 *
 */
public class ZkMonitorManager
{
    private static final Logger logger = LoggerFactory.getLogger(ZkMonitorManager.class);

    private NodeManager nodeManager;

    private final CuratorFramework zookeeper;

    public ZkMonitorManager(CuratorFramework zookeeper)
    {
        this.zookeeper = zookeeper;
        nodeManager = new NodeManager(zookeeper);
    }

    public List<String> getServices()
    {
        zkStart();
        List<String> list = nodeManager.getChildrenList(Constants.ZK_SEPARATOR_DEFAULT);
        return list;
    }

    public List<String> getServKind(String servId)
    {
        zkStart();
        List<String> list = nodeManager.getChildrenList(Constants.ZK_SEPARATOR_DEFAULT + servId);
        return list;
    }

    public List<String> getServConfig(String servId)
    {
        String nameSpace = NodeHelper.getNameSpace(servId, NodeType.PROVIDERS);
        List<String> list = nodeManager.getChildrenList(nameSpace);
        return list;
    }

    public List<String> getClientConfig(String servId)
    {
        String nameSpace = NodeHelper.getNameSpace(servId, NodeType.CONSUMERS);
        List<String> list = nodeManager.getChildrenList(nameSpace);
        return list;
    }

    public void deleteServ(String servId)
    {
        nodeManager.delete(Constants.ZK_SEPARATOR_DEFAULT + servId);
    }

    public List<URL> transferConfig(List<String> configs)
    {
        List<URL> urls = new ArrayList<>();
        for (int i = 0; i < configs.size(); i++)
        {
            String config = configs.get(i);
            URL url = UrlUtils.transfer(config);
            urls.add(url);
        }
        return urls;
    }

    private void zkStart()
    {
        if (zookeeper.getState() == CuratorFrameworkState.LATENT)
        {
            zookeeper.start();
            logger.info("zookeeper start success...");
        }
    }

    public static void main(String[] args)
    {
        CuratorFrameworkManager f = new CuratorFrameworkManager("127.0.0.1:2181");
        f.start();
        CuratorFramework zookeeper = f.getCuratorFramework();
        ZkMonitorManager zk = new ZkMonitorManager(zookeeper);
        List<String> list = zk.getServices();
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i));
        }
        zk.deleteServ("clients");
    }
}
