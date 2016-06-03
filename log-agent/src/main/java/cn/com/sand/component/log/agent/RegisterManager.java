/**
 * Copyright : http://www.sandpay.com.cn , 2016年2月17日
 * Project : log-agent
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年2月17日 下午5:40:06
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年2月17日        Initailized
 */
package cn.com.sand.component.log.agent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @ClassName ：RegisterManager
 * @author : SJ
 * @Date : 2016年2月17日 下午5:40:06
 * @version 1.0.0
 *
 */
public class RegisterManager
{
    private static ConcurrentMap<String, RandomAccessFile> randomFileMap = new ConcurrentHashMap<String, RandomAccessFile>();

    public void add(String filePath)
    {
        try
        {
            File file = new File(filePath);
            RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
            randomFileMap.put(file.getAbsolutePath(), randomFile);
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean delete(String key)
    {
        if (randomFileMap.containsKey(key))
        {
            randomFileMap.remove(key);
        }
        return true;
    }

    public RandomAccessFile get(String key)
    {
        if (randomFileMap.containsKey(key))
        {
            return randomFileMap.get(key);
        }
        return null;
    }

    public ConcurrentMap<String, RandomAccessFile> getAll()
    {
        return randomFileMap;
    }

    public static void main(String[] args) throws Exception
    {
        RegisterManager reg = new RegisterManager();
//        reg.add("main.log");
//        reg.add("main2.log");
        reg.add("/Users/songx/Documents/workspace/sand-component/sand-component/log-agent/main3.log");
        final ConcurrentMap<String, RandomAccessFile> map = reg.getAll();
        for (final String key : map.keySet())
        {
            ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
            exec.scheduleWithFixedDelay(new Runnable() {
                FileReader reader = new FileReader();
                RandomAccessFile randomFile = map.get(key);
//                final File tmpLogFile = new File("main.log");
//                RandomAccessFile randomFile = new RandomAccessFile(tmpLogFile, "rw");
                @Override
                public void run()
                {
                    long size = reader.getSize();
                    System.out.println(size+"===");
                    String ss = reader.read(randomFile, size);
                    System.out.println(ss);

                }
            }, 0, 1, TimeUnit.SECONDS);
        }


    }
}
