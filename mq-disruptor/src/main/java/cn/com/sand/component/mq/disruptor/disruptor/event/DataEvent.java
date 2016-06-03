package cn.com.sand.component.mq.disruptor.disruptor.event;


import com.lmax.disruptor.EventFactory;

public class DataEvent {

    private Event event;

    public  Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public final static EventFactory<DataEvent> EVENT_FACTORY = new EventFactory<DataEvent>() {

        public DataEvent newInstance() {
            return new DataEvent();
        }
    };

}
