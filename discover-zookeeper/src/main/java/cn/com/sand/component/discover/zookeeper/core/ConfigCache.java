/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : discover-zookeeper
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年5月26日 下午2:24:42
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年5月26日        Initailized
 */
package cn.com.sand.component.discover.zookeeper.core;

import java.util.Map;

import com.google.common.collect.Maps;

import cn.com.sand.component.discover.zookeeper.common.DynamicHostSet;

/**
 *
 * @ClassName ：ConfigCache
 * @author : SJ
 * @Date : 2016年5月26日 下午2:24:42
 * @version 2.0.0
 *
 */
public class ConfigCache
{
    private static Map<String, DynamicHostSet> cache = Maps.newHashMap();
    private static ConfigCache          instance;

    public static ConfigCache init()
    {
        if (instance == null)
        {
            instance = new ConfigCache();
        }
        return instance;
    }

    public void put(String key, DynamicHostSet value)
    {
        cache.put(key, value);
    }

    public DynamicHostSet get(String key)
    {
        return cache.get(key);
    }
}
