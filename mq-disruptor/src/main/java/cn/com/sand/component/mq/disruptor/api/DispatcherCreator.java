package cn.com.sand.component.mq.disruptor.api;

public abstract class DispatcherCreator<R,T> {

   private final String name;

   public DispatcherCreator(String name){
       this.name = name;
   }

   public String getName(){
       return this.name;
   }

   public abstract Dispatcher<R,T> create();

}
