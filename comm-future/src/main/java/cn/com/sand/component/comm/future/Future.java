package cn.com.sand.component.comm.future;

import java.util.concurrent.TimeUnit;

/**
 * 异步接口
 *
 * @author Nano
 * @version 1.0.0
 * @since 2015/7/2 14:08
 * abacus-parent
 */
public interface Future<V> {

   /**
    * 异步处理完成，设置异步结果
    *
    * @param val
    */
   void set(V val);

   /**
    * 获取异步处理结果
    *
    * @return
    */
    V get();

    /**
     * 等待指定超时时间获得异步处理结果
     *
     * @param timeout
     * @param unit
     * @return
     */
    V get(long timeout, TimeUnit unit);


}
