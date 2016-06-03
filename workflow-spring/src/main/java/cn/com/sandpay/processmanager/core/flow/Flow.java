package cn.com.sandpay.processmanager.core.flow;


public interface Flow {

	String getName();

	FlowExecution start(FlowExecutor executor) throws FlowExecutionException2;

}