/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : discover-zookeeper
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年5月26日 下午2:39:17
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年5月26日        Initailized
 */
package cn.com.sand.component.discover.zookeeper.core;

/**
 *
 * @ClassName ：ConfigManager
 * @author : SJ
 * @Date : 2016年5月26日 下午2:39:17
 * @version 2.0.0
 *
 */
public class ConfigManager
{
    private static ConfigManager instance;
    public synchronized static ConfigManager init()
    {
        if(instance == null)
        {
            instance = new ConfigManager();
        }
        return instance;
    }
    
}
