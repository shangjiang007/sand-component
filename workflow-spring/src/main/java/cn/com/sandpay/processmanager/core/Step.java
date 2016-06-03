package cn.com.sandpay.processmanager.core;

import cn.com.sandpay.processmanager.core.flow.FlowExecutionException2;

public interface Step {

	String getName();

	void execute(StepExecution stepExecution) throws FlowExecutionException2;

}
