package cn.com.sand.component.comm.future;

/**
 * 值
 *
 * @author Nano
 * @version 1.0.0
 * @since 2015/7/2 15:03
 * abacus-parent
 */
public class Val<V> {

    private V val;

    private volatile boolean success = false;

    private Throwable cause;

    public Val<V> setVal(V val){
        this.success = true;
        this.val = val;
        return this;
    }

    public Val<V> setCause(Throwable cause){
        this.success = false;
        this.cause   = cause;
        return this;
    }

    public Throwable getCause(){
        return this.cause;
    }

    public V getVal(){
        return this.val;
    }

    public boolean isSuccess(){
        return this.success;
    }

}
