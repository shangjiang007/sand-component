package cn.com.sand.component.mq.disruptor.disruptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;

import cn.com.sand.component.mq.disruptor.disruptor.event.DataEvent;
import cn.com.sand.component.mq.disruptor.disruptor.event.Event;

/**
 *
 *  DataEvent Processer
 *
 */
public abstract class DataEventHandler<T extends Event> implements EventHandler<DataEvent>{
    private Logger logger = LoggerFactory.getLogger(DataEventHandler.class);
    public void onEvent(DataEvent event, long sequence, boolean endOfBatch) throws Exception {
	    try{
           logger.debug("Handler received event: " + event+",sequence:"+sequence+",endOfBatch:"+endOfBatch);
           T innerEvent =  (T)event.getEvent();
           doHandle(innerEvent);
        }catch(Exception e){
            logger.error("onEvent(event:" + event+",sequence:"+sequence+",endOfBatch:"+endOfBatch+") process fail,cause by:", e);
            throw e;
        }
    }

    public abstract void doHandle(T t);
}
