/**
 * Copyright : http://www.sandpay.com.cn , 2016年5月5日
 * Project : discover-zookeeper
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年5月5日 上午10:06:32
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年5月5日        Initailized
 */
package cn.com.sand.component.discover.zookeeper.common;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.com.sand.component.discover.zookeeper.core.URL;
import cn.com.sand.component.discover.zookeeper.strategy.LoadStrategy;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @ClassName ：UrlUtils
 * @author : SJ
 * @Date : 2016年5月5日 上午10:06:32
 * @version 1.0.0
 *
 */
public class UrlUtils
{
    public static URL transfer(String data)
    {
        try
        {
            String config = URLDecoder.decode(data, Constants.UTF8);
            URL url = JSONObject.parseObject(config, URL.class);
            return url;
        }
        catch (Exception e)
        {

        }
        return null;
    }

    public static URL getUrl(Set<URL> lives, String servId)
    {
        if (lives != null && lives.size() > 0)
        {
            List<URL> urList = new ArrayList<>();
            for (URL url : lives)
            {
                if (servId.equals(url.getNode()))
                {
                    urList.add(url);
                }
            }
            return LoadStrategy.randomKey(urList);
        }
        return null;
    }
}
