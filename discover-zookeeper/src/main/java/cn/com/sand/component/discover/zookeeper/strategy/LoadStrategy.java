/**
 * Copyright : http://www.sandpay.com.cn , 2016年4月28日
 * Project : discover-zookeeper
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年4月28日 上午10:03:51
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年4月28日        Initailized
 */
package cn.com.sand.component.discover.zookeeper.strategy;

import java.util.List;
import java.util.Random;

import cn.com.sand.component.discover.zookeeper.core.URL;

/**
 *
 * @ClassName ：LoadStrategy
 * @author : SJ
 * @Date : 2016年4月28日 上午10:03:51
 * @version 1.0.0
 *
 */
public class LoadStrategy
{
    /**
     * 随机取得KEY
    *
    * @param urls
    * @return
     */
    public static URL randomKey(List<URL> urls)
    {
        int size = urls.size();
        Random random = new Random();
        int i = random.nextInt(size);
        URL url = urls.get(i);
        return url;
    }

}
