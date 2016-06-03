package cn.com.sand.component.mq.disruptor.disruptor;


import java.util.ArrayList;
import java.util.List;

import cn.com.sand.component.mq.disruptor.util.ReflectUtil;

public class ChannelTopic {

    private ChannelType type ; //通道类型

    private String      topic; //通道主题

    private int bufferSize;

    private DisruptorWrap.Producer producer;

    private int consumerNum;

    private DisruptorWrap.Wait     wait;

    private List<Constructor<DataEventHandler<?>>> handlersList = new ArrayList<Constructor<DataEventHandler<?>>>();

    public static class Constructor<T> {

        private Class<T>   clazz;

        private Class<?>[] argTypes;

        private Object[]   args;

        public Class<T> getClazz() {
            return clazz;
        }

        public void setClazz(Class<T> clazz) {
            this.clazz = clazz;
        }

        public Class<?>[] getArgTypes() {
            return argTypes;
        }

        public void setArgTypes(Class<?>[] argTypes) {
            this.argTypes = argTypes;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }
    }


    public void addHandler(Constructor<DataEventHandler<?>> handlerConstructor){
        handlersList.add(handlerConstructor);
    }

    public DataEventHandler<?>[] toHandler(){
        DataEventHandler<?>[] handlers = new DataEventHandler[handlersList.size()];
        for (int i = 0; i < handlersList.size(); i++) {
            Constructor<DataEventHandler<?>> handlerConstructor = handlersList.get(i);
            if(handlerConstructor.getArgTypes() != null)
                handlers[i] = ReflectUtil.callConstructor(handlerConstructor.getClazz(),handlerConstructor.getArgTypes(),handlerConstructor.getArgs());
            else
                handlers[i] = ReflectUtil.callConstructor(handlerConstructor.getClazz());
        }
        return handlers;
    }

    public Constructor<DataEventHandler<?>>  removeInHandler(int index){
        return handlersList.remove(index);
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public DisruptorWrap.Producer getProducer() {
        return producer;
    }

    public void setProducer(DisruptorWrap.Producer producer) {
        this.producer = producer;
    }

    public int getConsumerNum() {
        return consumerNum;
    }

    public void setConsumerNum(int consumerNum) {
        this.consumerNum = consumerNum;
    }

    public DisruptorWrap.Wait getWait() {
        return wait;
    }

    public void setWait(DisruptorWrap.Wait wait) {
        this.wait = wait;
    }

    public  ChannelTopic(ChannelType type,String topic){
        this.type = type;
        this.topic= topic;
    }

    public ChannelType getType() {
        return type;
    }

    public void setType(ChannelType type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int hashCode() {
        int result = 17;
        if(type != null)
            result = 37 * result + type.ordinal();
        if(topic != null)
            result = 37 * result + topic.hashCode();
        return result;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ChannelTopic))
            return false;
        ChannelTopic channel = (ChannelTopic) obj;
        if(this.type  != null  && channel.getType() == null){
            return false;
        }
        if(this.topic != null  && channel.getTopic() == null){
            return false;
        }
        return this.type == channel.getType() && this.topic.equals(channel.getTopic());
    }

    public String toString() {
        return "Topic:{type:"+type+",topic:"+topic+"}";
    }

    public static enum ChannelType{
        BUSI; //业务通道

        public static ChannelType getChannelType(String type){
            if(type.equals("pay") || type.equals("manage") || type.equals("query")){
                return BUSI;
            }else{
                throw new RuntimeException("not found ChannelType, please set a ChannelType with type:" + type );
            }
        }
    }
}
