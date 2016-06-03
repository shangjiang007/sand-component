package cn.com.sandpay.processmanager.core.step.tasklet;

import cn.com.sandpay.processmanager.core.ExecutionContext;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionException2;

public interface Tasklet {
	
	void logStepName(String stepName);

	void execute(ExecutionContext ctx) throws FlowExecutionException2;
	
	

}
