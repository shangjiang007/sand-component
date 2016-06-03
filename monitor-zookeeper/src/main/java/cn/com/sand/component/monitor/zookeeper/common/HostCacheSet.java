/**
 * Copyright : http://www.sandpay.com.cn , 2016年4月21日
 * Project : monitor-zookeeper
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年4月21日 上午10:00:30
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年4月21日        Initailized
 */
package cn.com.sand.component.monitor.zookeeper.common;

import java.util.Collection;

/**
 *
 * @ClassName ：HostCacheSet
 * @author : SJ
 * @Date : 2016年4月21日 上午10:00:30
 * @version 1.0.0
 *
 */
public interface HostCacheSet<K>
{
    public void addServerInstance(NodeBean node);

    public void addLiveInstance(NodeBean node);

    public void addDeadInstance(NodeBean node);

    void adjustAll();

    public void replaceWithList(Collection<NodeBean> hosts);
}
