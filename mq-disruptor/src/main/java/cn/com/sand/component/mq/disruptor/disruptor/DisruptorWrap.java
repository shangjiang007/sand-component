package cn.com.sand.component.mq.disruptor.disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import cn.com.sand.component.mq.disruptor.disruptor.event.DataEvent;
import cn.com.sand.component.mq.disruptor.disruptor.event.Event;

/**
 * 
 * The Wrap of disruptor and channel, for diff business or diff channel
 * 
 */
public class DisruptorWrap
{
    private Logger                logger  = LoggerFactory
                                                  .getLogger(DisruptorWrap.class);
    private ExecutorService       executor;

    private Disruptor<DataEvent>  disruptor;

    private AtomicBoolean         flag    = new AtomicBoolean(false);

    private volatile boolean      started = false;

    private final int             consumerNum;

    private int                   bufferSize;

    private RingBuffer<DataEvent> ringBuffer;

    private final Wait            waitStrategy;

    private final Producer        producerType;

    private DataEventHandler<?>[] handlers;

    public DisruptorWrap(DataEventHandler<?>... handlers)
    {
        this(1024, handlers);
    }

    public DisruptorWrap(int bufferSize, DataEventHandler<?>... handlers)
    {
        this(bufferSize, Producer.SINGLE, handlers);
    }

    public DisruptorWrap(int bufferSize, Producer producerType,
            DataEventHandler<?>... handlers)
    {
        this(bufferSize, producerType, 1, handlers);
    }

    public DisruptorWrap(int bufferSize, Producer producerType,
            int consumerNum, DataEventHandler<?>... handlers)
    {
        this(bufferSize, producerType, consumerNum, Wait.BLOCKING, handlers);
    }

    public DisruptorWrap(int bufferSize, Producer producerType,
            int consumerNum, Wait waitStrategy, DataEventHandler<?>... handlers)
    {
        Preconditions.checkArgument(bufferSize > 0,
                "bufferSize must greater than 0");
        Preconditions.checkArgument(consumerNum > 0,
                "consumerNum must greater than 0");
        Preconditions.checkArgument(handlers.length > 0,
                "handlers length must greater than 0");
        this.bufferSize = bufferSize;
        this.producerType = producerType;
        this.consumerNum = consumerNum;
        this.waitStrategy = waitStrategy;
        this.handlers = handlers;
    }

    public void init()
    {
        executor = Executors.newFixedThreadPool(this.consumerNum);

        disruptor = new Disruptor<DataEvent>(DataEvent.EVENT_FACTORY,
                bufferSize, executor, producerType.getProducerType(),
                waitStrategy.getWaitStrategy());
        EventHandlerGroup<DataEvent> group = null;
        for (int i = 0; i < handlers.length; i++)
        {
            if (i == 0)
                group = disruptor.handleEventsWith(handlers[i]);
            else
                group.then(handlers[i]);
        }
        disruptor.start();
        ringBuffer = disruptor.getRingBuffer();

    }

    public boolean publish(Event typeEvent)
    {
        long sequence = ringBuffer.next();
        try
        {
            DataEvent event = ringBuffer.get(sequence);
            event.setEvent(typeEvent);
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
        return true;
    }

    public boolean start()
    {
        this.init();
        started = flag.compareAndSet(false, true);
        return started;
    }

    public boolean isActive()
    {
        return this.started;
    }

    public void stop()
    {
        try
        {
            if (started)
            {
                if (flag.compareAndSet(true, false))
                {
                    started = false;
                }
                executor.shutdown();
                disruptor.shutdown();
            }
        }
        catch (Exception ex)
        {
            logger.error("stop fail,cause:", ex);
            throw new RuntimeException(ex);
        }
    }

    public static enum Wait
    {
        /***
         * highest performing : BUSYSPIN > YIELDING > SLEEPING > BLOCKING
         * 
         * low latency : BUSYSPIN > YIELDING > SLEEPING > BLOCKING
         * 
         * event hand smaller and physical cores : BUSYSPIN,YIELDING
         * 
         * use case is for asynchronous logging: SLEEPING
         */
        BLOCKING, SLEEPING, YIELDING, BUSYSPIN;

        public WaitStrategy getWaitStrategy()
        {
            switch (this)
            {
                case BLOCKING:
                    return new BlockingWaitStrategy();
                case SLEEPING:
                    return new SleepingWaitStrategy();
                case YIELDING:
                    return new YieldingWaitStrategy();
                case BUSYSPIN:
                    return new BusySpinWaitStrategy();
                default:
                    return new BlockingWaitStrategy();
            }
        }

        public static Wait getWait(String type)
        {
            if ("BLOCKING".equalsIgnoreCase(type))
            {
                return BLOCKING;
            }
            else if ("SLEEPING".equalsIgnoreCase(type))
            {
                return SLEEPING;
            }
            else if ("YIELDING".equalsIgnoreCase(type))
            {
                return YIELDING;
            }
            else if ("BUSYSPIN".equalsIgnoreCase(type))
            {
                return BUSYSPIN;
            }
            else
            {
                return BLOCKING;
            }
        }
    }

    public static enum Producer
    {
        /** single producer */
        SINGLE,

        /** multiple producer */
        MULTI;

        public ProducerType getProducerType()
        {
            switch (this)
            {
                case SINGLE:
                    return ProducerType.SINGLE;
                case MULTI:
                    return ProducerType.MULTI;
                default:
                    return ProducerType.SINGLE;
            }
        }

        public static Producer getProducer(String type)
        {
            if (type.equalsIgnoreCase("SINGLE"))
            {
                return SINGLE;
            }
            else if (type.equalsIgnoreCase("MULTI"))
            {
                return MULTI;
            }
            else
            {
                return SINGLE;
            }
        }

    }

}
