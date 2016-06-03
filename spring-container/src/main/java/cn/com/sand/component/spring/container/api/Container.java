package cn.com.sand.component.spring.container.api;

public interface Container {

    /** init container */
    public boolean init();

    /**  get bean of container */
    public <T> T getBean(String id,Class<T> clazz);

    public Object getBean(String id);

    /** release resource */
    public void release();

}
