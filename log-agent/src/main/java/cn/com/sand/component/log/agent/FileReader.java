/**
 * Copyright : http://www.sandpay.com.cn , 2016年2月17日
 * Project : log-agent
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年2月17日 下午5:48:51
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年2月17日        Initailized
 */
package cn.com.sand.component.log.agent;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @ClassName ：FileReader
 * @author : SJ
 * @Date : 2016年2月17日 下午5:48:51
 * @version 1.0.0
 *
 */
public class FileReader
{
    private long lastTimeFileSize = 0; // 上次文件大小

    public FileReader()
    {
    }

    public String read(RandomAccessFile randomFile, long lastTimeFileSize)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            if (randomFile != null)
            {
                String tmp = "";
                randomFile.seek(lastTimeFileSize);
                while ((tmp = randomFile.readLine()) != null)
                {
                    sb.append(new String(tmp.getBytes("UTF-8")));
                }
                this.lastTimeFileSize = randomFile.length();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public long getSize()
    {
        return this.lastTimeFileSize;
    }

}
