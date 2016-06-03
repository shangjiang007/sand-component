package cn.com.sandpay.processmanager.core.flow;

public interface State {

	String getName();

	FlowExecutionStatus handle(FlowExecutor executor) throws FlowExecutionException2;

	boolean isEndState();

}
