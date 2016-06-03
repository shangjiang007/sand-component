package cn.com.sand.component.comm.future.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cn.com.sand.component.comm.future.Promise;
import cn.com.sand.component.comm.future.Val;

/**
 * 承诺
 *
 * @ClassName ：SandPromise
 * @author : SJ
 * @Date : 2016年2月18日 下午4:39:09
 * @version 2.0.0
 *
 */
public class SandPromise<V> implements Promise<V>
{

    private volatile boolean done = false;

    private Object lock = new Object();

    private Val<V> val = new Val<V>();

    @Override
    public boolean isSuccess()
    {
        return this.val.isSuccess();
    }

    @Override
    public Throwable getFailureCause()
    {
        return this.val.getCause();
    }

    @Override
    public Promise<V> setSuccess(V result)
    {
        this.set(result);
        return this;
    }

    @Override
    public Promise<V> setFailure(Throwable cause)
    {
        synchronized (lock)
        {
            this.done = true;
            this.val.setCause(cause);
            lock.notifyAll();
        }
        return this;
    }

    @Override
    public void set(V val)
    {
        synchronized (lock)
        {
            this.done = true;
            this.val.setVal(val);
            lock.notifyAll();
        }
    }

    @Override
    public V get()
    {
        synchronized (lock)
        {
            if (!done)
            {
                try
                {
                    lock.wait();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                    this.val.setCause(e);
                }
            }
        }
        return this.val.getVal();
    }

    @Override
    public V get(long timeout, TimeUnit unit)
    {
        synchronized (lock)
        {
            if (!done)
            {
                try
                {
                    lock.wait(unit.toMillis(timeout));
                    if (!done)
                    {
                        this.val.setCause(new TimeoutException("timeout for " + unit.toMillis(timeout) + " ms"));
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                    this.val.setCause(e);
                }
            }
        }
        return this.val.getVal();
    }

    public static <T> Promise<T> newPromise(Class<T> clazz)
    {
        return new SandPromise<T>();
    }

    public static void main(String[] args)
    {

        final Promise<String> promise = SandPromise.newPromise(String.class);

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                // promise.set("test");
                try
                {
                    Thread.sleep(6000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                promise.setFailure(new RuntimeException("test"));
            }
        }).start();

        System.out.println("result=>" + promise.get(5, TimeUnit.SECONDS));
        System.out.println("success=>" + promise.isSuccess());
        System.out.println("success=>" + promise.getFailureCause());

    }
}