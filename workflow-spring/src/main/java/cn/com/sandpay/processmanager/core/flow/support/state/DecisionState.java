package cn.com.sandpay.processmanager.core.flow.support.state;

import cn.com.sandpay.processmanager.core.flow.FlowExecutionException2;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionStatus;
import cn.com.sandpay.processmanager.core.flow.FlowExecutor;
import cn.com.sandpay.processmanager.core.flow.JobExecutionDecider;

public class DecisionState extends AbstractState {

	private final JobExecutionDecider decider;

	public DecisionState(JobExecutionDecider decider, String name) {
		super(name);
		this.decider = decider;
	}

	@Override
	public FlowExecutionStatus handle(FlowExecutor executor) throws FlowExecutionException2 {
		// 执行decider
		return decider.decide(executor.getJobExecution(), executor.getStepExecution());
	}
	
	public boolean isEndState() {
		return false;
	}

}