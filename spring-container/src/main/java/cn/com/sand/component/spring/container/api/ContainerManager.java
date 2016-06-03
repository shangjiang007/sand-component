package cn.com.sand.component.spring.container.api;


public class ContainerManager {

    public static final ContainerManager cm = new ContainerManager();

    private ContainerManager(){}

    public static ContainerManager getInstance(){
        return cm;
    }

    private Container container;

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public <T> T getBean(String id,Class<T> clazz){
        return this.container.getBean(id,clazz);
    }

    public Object getBean(String id){
        return this.container.getBean(id);
    }
}
