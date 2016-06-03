package cn.com.sand.component.mq.disruptor.disruptor;


import cn.com.sand.component.mq.disruptor.disruptor.event.Event;

public class DisruptorEntity {

    private final ChannelTopic channel;

    private final DisruptorWrap  disruptorWrap;


    public DisruptorEntity(ChannelTopic channel,DisruptorWrap disruptorWrap){
        this.channel         = channel;
        this.disruptorWrap   = disruptorWrap;
    }

    public boolean publishEvent(Event event){
       boolean publish =  disruptorWrap.publish(event);
       return publish;
    }

    public boolean start(){
        return this.disruptorWrap.start() ;
    }

    public boolean stop(){
        try{
            this.disruptorWrap.stop();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean isActive(){
        return this.disruptorWrap.isActive();
    }

    public String toString() {
        return "DisruptorEntity(channel:"+channel.toString()+",disruptorWrap:"+disruptorWrap.toString()+")";
    }
}
