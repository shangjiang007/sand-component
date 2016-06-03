package cn.com.sand.component.discover.zookeeper.core;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sand.component.discover.zookeeper.common.Constants;

/**
 * CuratorFrameworkManager 管理器
 *
 * @author songx
 *
 */
public class CuratorFrameworkManager
{
    private Logger log = LoggerFactory.getLogger(getClass());

    private CuratorFramework curatorFramework;

    /** 命名空间 */
    private String              namespace;
    /** 连接地址 */
    private String              connectString;
    /** 连接超时时间 */
    private int                 connectionTimeoutMs;
    /** session超时时间 */
    private int                 sessionTimeoutMs;
    private RetryPolicy         retryPolicy;

    /**
     * @param namespace
     * @param connectString
     * @param baseSleepTimeMs
     * @param maxRetries
     * @param connectionTimeoutMs
     * @param sessionTimeoutMs
     */
    public CuratorFrameworkManager(String namespace, String connectString, int baseSleepTimeMs, int maxRetries,
            int connectionTimeoutMs, int sessionTimeoutMs)
    {
        this.namespace = namespace;
        this.connectString = connectString;
        this.connectionTimeoutMs = connectionTimeoutMs;
        this.sessionTimeoutMs = sessionTimeoutMs;

        this.retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);

    }

    /**
     * @param namespace
     * @param connectString
     * @param connectionTimeoutMs
     * @param sessionTimeoutMs
     */
    public CuratorFrameworkManager(String namespace, String connectString, int connectionTimeoutMs,
            int sessionTimeoutMs)
    {
        this.namespace = namespace;
        this.connectString = connectString;
        this.connectionTimeoutMs = connectionTimeoutMs;
        this.sessionTimeoutMs = sessionTimeoutMs;

        this.retryPolicy = new ExponentialBackoffRetry(1000, 3);
    }

    /**
     * @param namespace
     * @param connectString
     */
    public CuratorFrameworkManager(String namespace, String connectString)
    {
        this.namespace = namespace;
        this.connectString = connectString;
        this.connectionTimeoutMs = 30000;
        this.sessionTimeoutMs = 60000;

        this.retryPolicy = new ExponentialBackoffRetry(1000, 3);
    }

    /**
     * @param connectString
     */
    public CuratorFrameworkManager(String connectString)
    {
        this.connectString = connectString;
        this.connectionTimeoutMs = 30000;
        this.sessionTimeoutMs = 60000;
        this.namespace = Constants.DEFAULT_NAME_SPACE;

        this.retryPolicy = new ExponentialBackoffRetry(1000, 3);
    }

    public void create()
    {

    }

    /**
     * 创建zookeeper连接
     *
     * @param namespace
     * @param connectString
     * @param retryPolicy
     * @param connectionTimeoutMs
     * @param sessionTimeoutMs
     */
    private synchronized void initCuratorFramework(String namespace, String connectString, RetryPolicy retryPolicy,
            int connectionTimeoutMs, int sessionTimeoutMs)
    {
        if (curatorFramework == null)
        {
            log.info("创建zookeeper连接 ---> " + connectString);
            curatorFramework = CuratorFrameworkFactory.builder().namespace(namespace).connectString(connectString)
                    .retryPolicy(retryPolicy).connectionTimeoutMs(connectionTimeoutMs)
                    .sessionTimeoutMs(sessionTimeoutMs).build();
        }
        /*
         * jvm关闭时，关闭curatorFramework
         */
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run()
            {
                log.info("zookeeper close...");
                CloseableUtils.closeQuietly(curatorFramework);
            }
        });

    }

    /**
     * 服务启动
     */
    public void start()
    {
        this.initCuratorFramework(namespace, connectString, retryPolicy, connectionTimeoutMs, sessionTimeoutMs);
        if (curatorFramework != null)
            this.curatorFramework.start();
    }

    public CuratorFramework getCuratorFramework()
    {
        return this.curatorFramework;
    }

    public static void main(String[] args) throws InterruptedException
    {
        // CuratorFrameworkManager cfm = CuratorFrameworkManager.getInstance();
        // cfm.addConnectionStateListener(new ZkConnectionStateListener());
        // cfm.start();
        // cfm.createNode("/1tt");
        // ZookeeperWatcherRegister reg = new ZookeeperWatcherRegister("/1tt");
        // cfm.setDataForNode("/1tt", "helloword213".getBytes());
        // byte[] bb = cfm.getDataForNode("/1tt", reg);
        // System.out.println(new String(bb));
        // cfm.setDataForNode("/1tt", "helloword2134".getBytes());
        // Thread.sleep(1000);
        // System.out.println(new String(reg.getData()) + "=====");
    }

}
