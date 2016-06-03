/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * multichannel-core-deploy-interceptor $Id$ $Revision$ Last Changed by SJ at
 * 2016年3月10日 下午3:20:13 $URL$
 *
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2016年3月10日
 * Initailized
 */
package cn.com.sand.component.discover.zookeeper.core;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;

import com.alibaba.fastjson.JSONObject;

import cn.com.sand.component.discover.zookeeper.common.DynamicHostSet;
import cn.com.sand.component.discover.zookeeper.common.UrlUtils;
import cn.com.sand.component.discover.zookeeper.register.ZkClientRegistry;
import cn.com.sand.component.discover.zookeeper.register.ZkServerRegistry;

/**
 *
 * @ClassName ：CuratorFactory
 * @author : SJ
 * @Date : 2016年3月10日 下午3:20:13
 * @version 2.0.0
 *
 */
public class CuratorManager
{
    private CuratorFramework            curatorFramework;
    private static CuratorManager intance;
    private String connectString;
    
    public synchronized static CuratorManager init()
    {
        if(intance == null)
        {
            intance = new CuratorManager();
        }
        return intance;
    }
    
    public CuratorManager start()
    {
        if(StringUtils.isNotBlank(connectString))
        {
            CuratorFrameworkManager f = new CuratorFrameworkManager(connectString);
            f.start();
            curatorFramework = f.getCuratorFramework();
        }
        return this;
    }
    
    public CuratorManager connectString(String connectString)
    {
        this.connectString = connectString;
        return this;
    }
    

    public  void regService(URL url)
    {
        String json = JSONObject.toJSONString(url);
        ZkServerRegistry zkServerRegistery = new ZkServerRegistry(curatorFramework, url.getNode());
        zkServerRegistery.register(json);
    }

    public void regClient(URL url)
    {
        String json = JSONObject.toJSONString(url);
        ZkClientRegistry zkClientRegistry = new ZkClientRegistry(curatorFramework, url.getNode());
        zkClientRegistry.register(json);
        zkClientRegistry.addProvidListener(url.getNode());
        DynamicHostSet hostSet = zkClientRegistry.getProviders();
        ConfigCache.init().put(url.getNode(), hostSet);
    }

    public  Set<URL> getLives(String servId)
    {
        return ConfigCache.init().get(servId).getAll();
    }

    public  String getIp(String id)
    {
        URL url = getUrl(id);
        return url.getIp();
    }

    public  String getPort(String id)
    {
        URL url = getUrl(id);
        return url.getPort();
    }

    public  int getTimeOut(String id)
    {
        URL url = getUrl(id);
        return url.getTimeout();
    }

    public  URL getUrl(String servId)
    {
        return UrlUtils.getUrl(getLives(servId), servId);
    }
}
