package cn.com.sand.component.spring.container.spring;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.sand.component.spring.container.api.Container;

public class SpringContainer implements Container{

    public static final SpringContainer springContainer = new SpringContainer();

    private SpringContainer(){}

    public  String DEFAULT_SPRING_CONFIG = "classpath*:META-INF/spring/*.xml";

    public ClassPathXmlApplicationContext context;

    public static SpringContainer getInstance() {
        return springContainer;
    }

    public boolean init() {
        String configPath = DEFAULT_SPRING_CONFIG;
        if (configPath == null || configPath.length() == 0) {
            configPath = DEFAULT_SPRING_CONFIG;
        }
        context = new ClassPathXmlApplicationContext(configPath.split("[,\\s]+"));
        context.start();
        return true;
    }

    public <T> T getBean(String id, Class<T> clazz) {
        return context.getBean(id,clazz);
    }

    public Object getBean(String id) {
        return context.getBean(id);
    }

    public void release() {
        if (context != null) {
             context.stop();
             context.close();
             context = null;
        }
    }
}
