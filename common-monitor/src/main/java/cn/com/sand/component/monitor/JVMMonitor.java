/**
 * Copyright : http://www.sandpay.com.cn , 2016年4月19日
 * Project : Test
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年4月19日 下午2:37:42
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年4月19日        Initailized
 */
package cn.com.sand.component.monitor;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.sun.management.OperatingSystemMXBean;

/**
 *
 * @ClassName ：JVMMonitor
 * @author : SJ
 * @Date : 2016年4月19日 下午2:37:42
 * @version 1.0.0
 *
 */
public class JVMMonitor
{
    private static Logger logger = LoggerFactory.getLogger(JVMMonitor.class);

    /**
     * 获得VM概要信息
     *
     * @return
     */
    @SuppressWarnings("restriction")
    public static JSONObject getVmInfo()
    {
        JSONObject json = new JSONObject();
        RuntimeMXBean mxbean = ManagementFactory.getRuntimeMXBean();
        OperatingSystemMXBean op = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = mem.getHeapMemoryUsage();
        ClassLoadingMXBean cl = ManagementFactory.getClassLoadingMXBean();
        ThreadMXBean thread = ManagementFactory.getThreadMXBean();
        json.put("bootClassPath", mxbean.getBootClassPath());
        json.put("commitMemory", heap.getCommitted());
        json.put("conntName", "");
        json.put("daemonThreadCount", thread.getDaemonThreadCount());
        json.put("jvmName", mxbean.getVmName());
        json.put("jvmVersion", mxbean.getVmVersion());
        json.put("loadedClassCount", cl.getLoadedClassCount());
        json.put("maxMemory", heap.getMax());
        json.put("operArch", op.getArch());
        json.put("operName", op.getName());
        json.put("operProc", op.getAvailableProcessors());
        json.put("peakThreadCount", thread.getPeakThreadCount());
        json.put("threadCount", thread.getThreadCount());
        json.put("totalLoadedClassCount", cl.getTotalLoadedClassCount());
        json.put("totalStartedThreadCount", thread.getTotalStartedThreadCount());
        json.put("unloadedClassCount", cl.getUnloadedClassCount());
        json.put("upTime", mxbean.getUptime());
        json.put("usedMemory", heap.getUsed());
        json.put("vmVendor", mxbean.getVmVendor());
        json.put("currentTime", System.currentTimeMillis());
        json.put("processCpuTime", op.getProcessCpuTime());
        logger.info(json.toJSONString());
        return json;
    }

    /** 获得CPU使用率 */
    public static double cpuUsed()
    {
        OperatingSystemMXBean op = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long start = System.currentTimeMillis();
        long startC = op.getProcessCpuTime();
        try
        {
            TimeUnit.SECONDS.sleep(5);
        }
        catch (Exception e)
        {
            System.out.println("中断异常");
        }

        long end = System.currentTimeMillis();
        long endC = op.getProcessCpuTime();
        int availableProcessors = op.getAvailableProcessors();
        double ratio = (endC - startC) / 1000000.0 / (end - start) / availableProcessors;
        return ratio;
    }
}
