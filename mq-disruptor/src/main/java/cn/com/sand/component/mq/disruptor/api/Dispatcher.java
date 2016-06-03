package cn.com.sand.component.mq.disruptor.api;

import cn.com.sand.component.mq.disruptor.disruptor.event.Event;
import cn.com.sand.component.mq.disruptor.exception.DispatcherException;

/**
 *
 * Dispatcher will be design Thread Safe
 *
 * The target of Dispatcher is dispathed and scheduled for the inflows data and out of data
 *
 * @param <R>
 * @param <T>
 */
public interface Dispatcher<R,T> {

    public  void asynDispatch(Event event,R r)throws DispatcherException;

}
