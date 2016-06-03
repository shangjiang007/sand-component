package cn.com.sand.component.comm.route.api;


import java.util.HashMap;

public class RouteManager {

    static final RouteManager routeManager = new RouteManager();

    private RouteManager(){}

    public static RouteManager getInstance(){
        return routeManager;
    }

    public HashMap<String,Route> routes = new HashMap<String,Route>();

    public synchronized void addRoute(String name,Route route){
        this.routes.put(name,route);
    }

    public Route getRoute(String name){
        return this.routes.get(name);
    }
}
