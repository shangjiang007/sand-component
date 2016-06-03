package cn.com.sand.component.comm.route.api;


import cn.com.sand.component.comm.route.exception.RouteException;

/**
 *
 * Route for route, will throws RouteException
 *
 */
public interface  Route {

    /**  init the route */
    public void init();

    /**  route a processer bean */
    public <T> T route(Rule rule,Class<T> clazz) throws RouteException;

    /**  start the route */
    public void start();

    /**  stop the route */
    public void stop();

}
