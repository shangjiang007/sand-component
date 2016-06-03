package cn.com.sand.component.mq.disruptor.factory;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import com.google.common.base.Preconditions;

import cn.com.sand.component.mq.disruptor.api.Dispatcher;
import cn.com.sand.component.mq.disruptor.api.DispatcherCreator;

public class DispatcherFactory {

  public final static DispatcherFactory dispatcherFactory = new DispatcherFactory();

  private   DispatcherFactory(){}

  public static DispatcherFactory getInstance(){
      return dispatcherFactory;
  }

  private ConcurrentHashMap<String,Dispatcher<?, ?>>     cacheDispatchers = new ConcurrentHashMap<String,Dispatcher<?, ?>>();

  private CopyOnWriteArraySet<DispatcherCreator<?, ?>>   cacheCreators    = new CopyOnWriteArraySet<DispatcherCreator<?, ?>>();

  public <R,T> Dispatcher<R, T> findDispatcher(String name){
      Dispatcher<R, T> dispatcher = (Dispatcher<R, T>) cacheDispatchers.get(name);
      Preconditions.checkNotNull(dispatcher,"Not found dispatcher with this name:"+name);
      return dispatcher;
  }

  public <R,T> boolean addDispatcherCreator(DispatcherCreator<R,T> creator){
      return this.cacheCreators.add(creator);
  }

  public <R,T> boolean deleteDispatcherCreator(DispatcherCreator<R,T> creator){
     return this.cacheCreators.remove(creator);
  }

  public <R,T> boolean updateDispatcherCreator(DispatcherCreator<R,T> creator){
      if(this.deleteDispatcherCreator(creator))
          return this.addDispatcherCreator(creator);
      return false;
  }

  public void loadAll(){
    Iterator<DispatcherCreator<?, ?>> it = cacheCreators.iterator();
    while(it.hasNext()){
       DispatcherCreator<?, ?> creator = it.next();
       this.addDispatcher(creator);
    }
  }

  private <R,T> void addDispatcher(DispatcherCreator<R,T> creator){
      this.cacheDispatchers.putIfAbsent(creator.getName(),creator.create());
  }

  public <R,T> void reload(DispatcherCreator<R,T> creator){
      if(this.updateDispatcherCreator(creator)){
          this.addDispatcher(creator);
      }
  }

  public void reloadAll(){
      this.loadAll();
  }

  public void flush(){
      this.clear();
      this.loadAll();
  }

  public void clear(){
      this.cacheCreators.clear();
      this.cacheDispatchers.clear();
  }
}
