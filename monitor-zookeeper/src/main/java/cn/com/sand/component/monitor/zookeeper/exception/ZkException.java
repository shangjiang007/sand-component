/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : discover-zookeeper
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2016年2月22日 下午2:36:26
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2016年2月22日        Initailized
 */
package cn.com.sand.component.monitor.zookeeper.exception;

/**
 *
 * @ClassName ：ZkException
 * @author : SJ
 * @Date : 2016年2月22日 下午2:36:26
 * @version 2.0.0
 *
 */
public class ZkException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ZkException() {
        super();
    }

    public ZkException(String s) {
        super(s);
    }

    public ZkException(Throwable cause) {
        super(cause);
    }

    public ZkException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ZkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
