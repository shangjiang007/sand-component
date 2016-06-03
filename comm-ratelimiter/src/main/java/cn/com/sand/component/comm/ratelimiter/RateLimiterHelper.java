package cn.com.sand.component.comm.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * 流控辅助库
 *
 * @author Nano
 * @version 1.0.0
 * @since 2015/5/26 17:14
 * abacus-parent
 */
public class RateLimiterHelper {


    /**
     * 根据指定的稳定吞吐率创建RateLimiter，这里的吞吐率是指每秒多少许可数（通常是指QPS，每秒多少次）
     *
     * @param permitsPerSecond
     *
     * @return 限流器
     */
    public static RateLimiter create(double permitsPerSecond){
        return RateLimiter.create(permitsPerSecond);
    }

    /**
     * 根据指定的稳定吞吐率和预热期来创建RateLimiter，这里的吞吐率是指每秒多少许可数（通常是指QPS，每秒多少个请求量），
     * 在这段预热时间内，RateLimiter每秒分配的许可数会平稳地增长直到预热期结束时达到其最大速率。
     * 只要存在足够请求数来使其饱和
     *
     * @param  permitsPerSecond
     * @param  warmupPeriod
     * @return 限流器
     */
    public static RateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit unit){
        return RateLimiter.create(permitsPerSecond,warmupPeriod,unit);
    }

    /**
     *
     * Acquires the given number of permits from this {@code RateLimiter}, blocking until the
     * request can be granted. Tells the amount of time slept, if any.
     *
     * <p>This method is equivalent to {@code acquire(1)}.
     * @return time spent sleeping to enforce rate, in seconds; 0.0 if not rate-limited
     */
    public static double acquire(RateLimiter rateLimiter){
        return rateLimiter.acquire();
    }

    /**
     * Acquires the given number of permits from this {@code RateLimiter}, blocking until the
     * request can be granted. Tells the amount of time slept, if any.
     *
     * @param permits the number of permits to acquire
     * @return time spent sleeping to enforce rate, in seconds; 0.0 if not rate-limited
     */
    public static double acquire(RateLimiter rateLimiter,int permits){
        return rateLimiter.acquire(permits);
    }


    /**
     * Returns the stable rate (as {@code permits per seconds}) with which this
     * {@code RateLimiter} is configured with. The initial value of this is the same as
     * the {@code permitsPerSecond} argument passed in the factory method that produced
     * this {@code RateLimiter}, and it is only updated after invocations
     * to {@linkplain #setRate}.
     *
     */
    public static double getRate(RateLimiter rateLimiter){
        return rateLimiter.getRate();
    }

    /**
     * Updates the stable rate of this {@code RateLimiter}, that is, the
     * {@code permitsPerSecond} argument provided in the factory method that
     * constructed the {@code RateLimiter}. Currently throttled threads will <b>not</b>
     * be awakened as a result of this invocation, thus they do not observe the new rate;
     * only subsequent requests will.
     *
     * <p>Note though that, since each request repays (by waiting, if necessary) the cost
     * of the <i>previous</i> request, this means that the very next request
     * after an invocation to {@code setRate} will not be affected by the new rate;
     * it will pay the cost of the previous request, which is in terms of the previous rate.
     *
     * <p>The behavior of the {@code RateLimiter} is not modified in any other way,
     * e.g. if the {@code RateLimiter} was configured with a warmup period of 20 seconds,
     * it still has a warmup period of 20 seconds after this method invocation.
     *
     * @param permitsPerSecond the new stable rate of this {@code RateLimiter}. Must be positive
     */
    public static void setRate(RateLimiter rateLimiter,double permitsPerSecond){
        rateLimiter.setRate(permitsPerSecond);
    }

    /**
     * Acquires a permit from this {@link RateLimiter} if it can be acquired immediately without
     * delay.
     *
     * <p>
     * This method is equivalent to {@code tryAcquire(1)}.
     *
     * @return {@code true} if the permit was acquired, {@code false} otherwise
     */
    public static  boolean	tryAcquire(RateLimiter rateLimiter){
        return rateLimiter.tryAcquire();
    }

    /**
     * Acquires permits from this {@link RateLimiter} if it can be acquired immediately without delay.
     *
     * <p>
     * This method is equivalent to {@code tryAcquire(permits, 0, anyUnit)}.
     *
     * @param permits the number of permits to acquire
     * @return {@code true} if the permits were acquired, {@code false} otherwise
     */
    public static  boolean	tryAcquire(RateLimiter rateLimiter,int permits){
        return rateLimiter.tryAcquire(permits);
    }

    /**
     * Acquires the given number of permits from this {@code RateLimiter} if it can be obtained
     * without exceeding the specified {@code timeout}, or returns {@code false}
     * immediately (without waiting) if the permits would not have been granted
     * before the timeout expired.
     *
     * @param permits the number of permits to acquire
     * @param timeout the maximum time to wait for the permits
     * @param unit the time unit of the timeout argument
     * @return {@code true} if the permits were acquired, {@code false} otherwise
     */
    public static  boolean	tryAcquire(RateLimiter rateLimiter,int permits,long timeout, TimeUnit unit){
        return rateLimiter.tryAcquire(permits,timeout,unit);
    }

    /**
     * Acquires a permit from this {@code RateLimiter} if it can be obtained
     * without exceeding the specified {@code timeout}, or returns {@code false}
     * immediately (without waiting) if the permit would not have been granted
     * before the timeout expired.
     *
     * <p>This method is equivalent to {@code tryAcquire(1, timeout, unit)}.
     *
     * @param timeout the maximum time to wait for the permit
     * @param unit the time unit of the timeout argument
     * @return {@code true} if the permit was acquired, {@code false} otherwise
     */
    public static  boolean	tryAcquire(RateLimiter rateLimiter,long timeout, TimeUnit unit){
        return rateLimiter.tryAcquire(timeout,unit);
    }

}
