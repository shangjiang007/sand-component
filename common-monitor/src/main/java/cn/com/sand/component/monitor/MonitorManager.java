/**
 * Copyright : http://www.sandpay.com.cn , 2016年4月19日
 * Project : Test
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年4月19日 下午3:42:22
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年4月19日        Initailized
 */
package cn.com.sand.component.monitor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @ClassName ：MonitorManager
 * @author : SJ
 * @Date : 2016年4月19日 下午3:42:22
 * @version 1.0.0
 *
 */
public class MonitorManager
{

    static
    {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {
            public void run()
            {
                JVMMonitor.getVmInfo();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

}
