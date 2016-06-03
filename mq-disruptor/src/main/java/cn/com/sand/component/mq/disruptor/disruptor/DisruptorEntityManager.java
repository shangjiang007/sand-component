package cn.com.sand.component.mq.disruptor.disruptor;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.netflix.config.DynamicStringProperty;

import cn.com.sand.component.mq.disruptor.util.DynamicPropertyHelper;
import cn.com.sand.component.mq.disruptor.util.ReflectUtil;

public class DisruptorEntityManager
{
    private Logger                             logger                 = LoggerFactory.getLogger(DisruptorEntityManager.class);
    public static final DisruptorEntityManager disruptorEntityManager = new DisruptorEntityManager();

    private DisruptorEntityManager()
    {
        this.init();
    }

    private Map<ChannelTopic, DisruptorEntity> cacheDisruptorEntitys = new ConcurrentHashMap<ChannelTopic, DisruptorEntity>();

    public static DisruptorEntityManager getInstance()
    {
        return disruptorEntityManager;
    }

    public DisruptorEntity getDisruptorEntity(ChannelTopic channel)
    {
        return this.cacheDisruptorEntitys.get(channel);
    }

    private void init()
    {
        DynamicStringProperty channnelTypes = DynamicPropertyHelper.getDynamicPropertyFactory()
                .getStringProperty("multichannel.busi.dispatcher.disruptor.channel.type", "pay");
        Iterator<String> itTypes = Splitter.on(',').trimResults().omitEmptyStrings().split(channnelTypes.get()).iterator();
        while (itTypes.hasNext())
        {
            String type = itTypes.next();
            DynamicStringProperty topics = DynamicPropertyHelper.getDynamicPropertyFactory()
                    .getStringProperty("multichannel.busi.dispatcher.disruptor.channel.topic." + type, "");
            Iterator<String> itTopics = Splitter.on(',').trimResults().omitEmptyStrings().split(topics.get()).iterator();
            while (itTopics.hasNext())
            {
                String topic = itTopics.next();
                ChannelTopic channelTopic = new ChannelTopic(ChannelTopic.ChannelType.getChannelType(type), topic);
                int bufferSize = DynamicPropertyHelper.getDynamicPropertyFactory()
                        .getIntProperty("multichannel.busi.dispatcher.disruptor.channel.topic." + topic + ".buffersize", 1024).get();
                String producerType = DynamicPropertyHelper.getDynamicPropertyFactory()
                        .getStringProperty("multichannel.busi.dispatcher.disruptor.channel.topic." + topic + ".producertype", "SINGLE")
                        .get();
                int consumerNum = DynamicPropertyHelper.getDynamicPropertyFactory()
                        .getIntProperty("multichannel.busi.dispatcher.disruptor.channel.topic." + topic + ".consumernum", 1).get();
                String waitStrategy = DynamicPropertyHelper.getDynamicPropertyFactory()
                        .getStringProperty("multichannel.busi.dispatcher.disruptor.channel.topic." + topic + ".waitstrategy", "BLOCKING")
                        .get();
                String handler = DynamicPropertyHelper.getDynamicPropertyFactory()
                        .getStringProperty("multichannel.busi.dispatcher.disruptor.channel.topic." + topic + ".handler", "").get();
//                String processer = DynamicPropertyHelper.getDynamicPropertyFactory()
//                        .getStringProperty("multichannel.busi.dispatcher.disruptor.channel.topic." + topic + ".handler.processer", "")
//                        .get();
//                String processerType = DynamicPropertyHelper.getDynamicPropertyFactory()
//                        .getStringProperty("multichannel.busi.dispatcher.disruptor.channel.topic." + topic + ".handler.processer.type", "")
//                        .get();
//                int concurrency = DynamicPropertyHelper.getDynamicPropertyFactory().getIntProperty(
//                        "multichannel.busi.dispatcher.disruptor.channel.topic." + topic + ".handler.processer.concurrency", 30).get();

                Preconditions.checkArgument(handler.length() > 0,
                        "please set \"multichannel.busi.dispatcher.disruptor.channel.topic."+topic+".handler\" property");
//                Preconditions.checkArgument(processer.length() > 0,
//                        "please set \"multichannel.busi.dispatcher.disruptor.channel.topic."+topic+".handler.processer\" property");

                channelTopic.setBufferSize(bufferSize);
                channelTopic.setProducer(DisruptorWrap.Producer.getProducer(producerType));
                channelTopic.setConsumerNum(consumerNum);
                channelTopic.setWait(DisruptorWrap.Wait.getWait(waitStrategy));
                ChannelTopic.Constructor<DataEventHandler<?>> handlerConstructor = new ChannelTopic.Constructor<DataEventHandler<?>>();
                handlerConstructor.setClazz((Class<DataEventHandler<?>>) ReflectUtil.loadClass(handler));
//                Class<?>[] argsType = new Class<?>[2];
//                argsType[0] = ReflectUtil.loadClass(processerType);
//                argsType[1] = Integer.class;
//                handlerConstructor.setArgTypes(argsType);
//                Object[] args = new Object[2];
//                args[0] = ReflectUtil.callConstructor(ReflectUtil.loadClass(processer));
//                args[1] = concurrency;
//                handlerConstructor.setArgs(args);
                channelTopic.addHandler(handlerConstructor);
                DisruptorWrap disruptorWrap = new DisruptorWrap(channelTopic.getBufferSize(), channelTopic.getProducer(),
                        channelTopic.getConsumerNum(), channelTopic.getWait(), channelTopic.toHandler());
                DisruptorEntity disruptorEntity = new DisruptorEntity(channelTopic, disruptorWrap);
                this.cacheDisruptorEntitys.put(channelTopic, disruptorEntity);
            }
        }
    }

    public boolean startAll()
    {
        boolean startAll = false;
        Iterator<DisruptorEntity> iterator = cacheDisruptorEntitys.values().iterator();
        int i = 0;
        while (iterator.hasNext())
        {
            DisruptorEntity disruptorEntity = iterator.next();
            if (!disruptorEntity.isActive())
            {
                boolean start = disruptorEntity.start();
                if (start)
                {
                    i++;
                    logger.info("DisruptorEntity:{} start success", new Object[] { disruptorEntity.toString() });
                }
                else
                {
                    logger.info("DisruptorEntity:{} start failed", new Object[] { disruptorEntity.toString() });
                }
            }
            else
            {
                i++;
                logger.warn("DisruptorEntity:{} aleady started, not need start again", new Object[] { disruptorEntity.toString() });
            }
        }
        startAll = (i == cacheDisruptorEntitys.size());
        return startAll;
    }

    public boolean stopAll()
    {
        boolean stopAll = false;
        Iterator<DisruptorEntity> iterator = cacheDisruptorEntitys.values().iterator();
        int i = 0;
        while (iterator.hasNext())
        {
            DisruptorEntity disruptorEntity = iterator.next();
            if (disruptorEntity.isActive())
            {
                boolean stop = disruptorEntity.stop();
                if (stop)
                {
                    i++;
                    logger.warn("DisruptorEntity:{} stop success", new Object[] { disruptorEntity.toString() });
                }
                else
                {
                    logger.warn("DisruptorEntity:{} stop fail", new Object[] { disruptorEntity.toString() });
                }
            }
            else
            {
                i++;
                logger.warn("DisruptorEntity:{} aleady stoped, not need stop again", new Object[] { disruptorEntity.toString() });
            }
        }
        stopAll = (i == cacheDisruptorEntitys.size());
        return stopAll;
    }

    public boolean start(ChannelTopic topic)
    {
        DisruptorEntity entity = this.cacheDisruptorEntitys.get(topic);
        if (entity != null)
            return entity.start();
        return false;
    }

    public boolean stop(ChannelTopic topic)
    {
        DisruptorEntity entity = this.cacheDisruptorEntitys.get(topic);
        if (entity != null)
            return entity.stop();
        return false;
    }

}
