package cn.com.sandpay.processmanager.core.flow;

import cn.com.sandpay.processmanager.core.JobExecution;
import cn.com.sandpay.processmanager.core.Step;
import cn.com.sandpay.processmanager.core.StepExecution;

public interface FlowExecutor {

	String executeStep(Step step) throws FlowExecutionException2;

	JobExecution getJobExecution();

	StepExecution getStepExecution();

	void close(FlowExecution result);

	void updateJobExecutionStatus(FlowExecutionStatus status);

	void addExitStatus(String code);

}
