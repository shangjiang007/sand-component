/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014 Project :
 * discover-zookeeper $Id$ $Revision$ Last Changed by SJ at 2016年2月22日 下午2:52:05
 * $URL$
 *
 * Change Log Author Change Date Comments
 * ------------------------------------------------------------- SJ 2016年2月22日
 * Initailized
 */
package cn.com.sand.component.discover.zookeeper.common;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.discover.zookeeper.core.URL;

import com.google.common.collect.Sets;

/**
 * 动态服务主机集合
 *
 * @ClassName ：DynamicHostSet
 * @author : SJ
 * @Date : 2016年2月22日 下午2:52:05
 * @version 2.0.0
 *
 */
public class DynamicHostSet
{

    private static final Logger logger = LoggerFactory.getLogger(DynamicHostSet.class);

    /** 所有的{@link URL} */
    private final Set<URL> all = Sets.newHashSet();

    /** 可访问的{@link URL} */
    private final Set<URL> lives = Sets.newHashSet();

    /** 不可访问的{@link URL} */
    private final Set<URL> deads = Sets.newHashSet();

    /**
     * add a server instance.
     *
     * @param url
     *            {@link ClientNode}
     */
    public synchronized void addServerInstance(URL url)
    {
        if (!lives.contains(url))
        {
            logger.warn("add {} to lives set", url);
            lives.add(url);
            logger.warn("add {} to all set", url);
            all.add(url);
        }
    }

    /**
     * add a live instance for heartbeat.
     * <p>
     *
     * @param url
     *            {@link ClientNode}
     */
    public synchronized void addLiveInstance(URL url)
    {
        if (all.contains(url) && deads.contains(url))
        {
            lives.add(url);
            logger.warn("add {} to lives set", url);
            deads.remove(url);
            logger.warn("remove {} from deads set", url);
        }
    }

    /**
     * add dead instance
     *
     * @param url
     *            {@link URL}
     */
    public synchronized void addDeadInstance(URL url)
    {
        if (all.contains(url) && lives.contains(url))
        {
            deads.add(url);
            logger.warn("add {} to deads set", url);
            lives.remove(url);
            logger.warn("remove {} from lives set", url);
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
    private void adjustAll()
    {
        for (URL url : all)
        {
            lives.add(url);
        }
        deads.clear();
    }

    /**
     * replace all hosts with new
     *
     * @param hosts
     */
    public synchronized void replaceWithList(Collection<URL> hosts)
    {
        // all替换为最新的集合
        all.clear();
        all.addAll(hosts);

        // deads取与最新集合的交集
        deads.retainAll(hosts);

        // lives取all和deads的差集
        lives.clear();
        lives.addAll(hosts);
        lives.removeAll(deads);

        logger.info(toString());
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
    public Set<URL> getAll()
    {
        return all;
    }

    /**
     * getter method
     *
     * @see DynamicHostSet#lives
     * @return the lives
     */
    public Set<URL> getLives()
    {
        return lives;
    }

    /**
     * getter method
     *
     * @see DynamicHostSet#deads
     * @return the deads
     */
    public Set<URL> getDeads()
    {
        return deads;
    }
}