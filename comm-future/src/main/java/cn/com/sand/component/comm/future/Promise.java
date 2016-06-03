package cn.com.sand.component.comm.future;

/**
 * 承诺
 *
 * @author Nano
 * @version 1.0.0
 * @since 2015/7/2 14:19
 * abacus-parent
 */
public interface Promise<V> extends Future<V> {


    /**
     * 判断处理成功
     *
     * @return
     */
    boolean isSuccess();

    /**
     * 设置成功处理的结果
     *
     * @param result
     * @return
     */
    Promise<V> setSuccess(V result);

    /**
     * 设置失败处理异常状态
     *
     * @param cause
     * @return
     */
    Promise<V> setFailure(Throwable cause);

    /**
     * 获得失败原因
     *
     * @return
     */
    Throwable getFailureCause();

}
