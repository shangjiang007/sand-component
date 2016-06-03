/**
 * Copyright : http://www.sandpay.com.cn , 2016年4月21日
 * Project : monitor-zookeeper
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年4月21日 上午10:02:15
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年4月21日        Initailized
 */
package cn.com.sand.component.monitor.zookeeper.common;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

/**
 *
 * @ClassName ：ClientHostCacheSet
 * @author : SJ
 * @Date : 2016年4月21日 上午10:02:15
 * @version 1.0.0
 *
 */
public class ClientHostCacheSet implements HostCacheSet<NodeBean>
{
    /** logger */
    private final Logger        logger      = LoggerFactory.getLogger(getClass());
    /** 所有的{@link NodeBean} */
    private final Set<NodeBean> all   = Sets.newHashSet();

    /** 可访问的{@link NodeBean} */
    private final Set<NodeBean> lives = Sets.newHashSet();

    /** 不可访问的{@link NodeBean} */
    private final Set<NodeBean> deads = Sets.newHashSet();

    /**
     * add a server instance.
     *
     * @param node
     *            {@link ClientNode}
     */
    public synchronized void addServerInstance(NodeBean node)
    {
        if (!lives.contains(node))
        {
            logger.warn("add {} to lives set", node);
            lives.add(node);
            logger.warn("add {} to all set", node);
            all.add(node);
        }
    }

    /**
     * add a live instance for heartbeat.
     * <p>
     *
     * @param node
     *            {@link ClientNode}
     */
    public synchronized void addLiveInstance(NodeBean node)
    {
        if (all.contains(node) && deads.contains(node))
        {
            lives.add(node);
            logger.warn("add {} to lives set", node);
            deads.remove(node);
            logger.warn("remove {} from deads set", node);
        }
    }

    /**
     * add dead instance
     *
     * @param node
     *            {@link NodeBean}
     */
    public synchronized void addDeadInstance(NodeBean node)
    {
        if (all.contains(node) && lives.contains(node))
        {
            deads.add(node);
            logger.warn("add {} to deads set", node);
            lives.remove(node);
            logger.warn("remove {} from lives set", node);
            if (lives.size() == 0)
            {
                adjustAll();
            }
        }
    }

    /**
     * 重新调整
     * <p>
     */
    public void adjustAll()
    {
        for (NodeBean node : all)
        {
            lives.add(node);
        }
        deads.clear();
    }

    /**
     * replace all hosts with new
     *
     * @param hosts
     */
    public synchronized void replaceWithList(Collection<NodeBean> hosts)
    {
        String hostSetString = toString();
        // all替换为最新的集合
        all.clear();
        all.addAll(hosts);

        // deads取与最新集合的交集
        deads.retainAll(hosts);

        // lives取all和deads的差集
        lives.clear();
        lives.addAll(hosts);
        lives.removeAll(deads);

        logger.warn("replace " + hostSetString + " with " + toString());
    }


    @Override
    public String toString()
    {
        return "[all=" + all + ", lives=" + lives + ", deads=" + deads + "]";
    }

    /**
     * getter method
     *
     * @see DynamicHostSet#all
     * @return the all
     */
    public Set<NodeBean> getAll()
    {
        return all;
    }

    /**
     * getter method
     *
     * @see DynamicHostSet#lives
     * @return the lives
     */
    public Set<NodeBean> getLives()
    {
        return lives;
    }

    /**
     * getter method
     *
     * @see DynamicHostSet#deads
     * @return the deads
     */
    public Set<NodeBean> getDeads()
    {
        return deads;
    }
}
