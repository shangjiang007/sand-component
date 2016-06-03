/**
 * Copyright : http://www.sandpay.com.cn , 2016年4月28日
 * Project : discover-zookeeper
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年4月28日 上午10:40:14
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年4月28日        Initailized
 */
package cn.com.sand.test;

import com.alibaba.fastjson.JSONObject;

import cn.com.sand.component.discover.zookeeper.core.CuratorManager;
import cn.com.sand.component.discover.zookeeper.core.URL;

/**
 *
 * @ClassName ：ClienTest
 * @author : SJ
 * @Date : 2016年4月28日 上午10:40:14
 * @version 1.0.0
 *
 */
public class ClienTest
{
    public static void main(String[] args) throws Exception
    {
        CuratorManager.init().connectString("172.28.250.86:2181").start();
        URL url = new URL();
        url.setIp("127.0.0.1");
        url.setPort("8080");
        url.setProtocol("tcp");
        url.setNode("cn.com.sand.multichannel.test");
        url.setTimeout(3000);
        CuratorManager.init().regClient(url);
        for (int i = 0; i < 3; i++)
        {
            URL config = CuratorManager.init().getUrl("cn.com.sand.multichannel.test");
            System.out.println("===================="+JSONObject.toJSONString(config));
        }
//        CuratorManager.addClientData("cn.com.sand.multichannel.test");
//        CuratorManager.addClientData("cn.com.sand.multichannel.test1");
//        CuratorManager.regClient("127.0.0.1", "cn.com.sand.multichannel.client1");
//        CuratorManager.addClientData("cn.com.sand.multichannel.test1");
//        CuratorManager.regClient("127.0.0.1", "cn.com.sand.multichannel.client2");
//        CuratorManager.addClientData("cn.com.sand.multichannel.test2");
        Thread.sleep(500000000);
    }
}
