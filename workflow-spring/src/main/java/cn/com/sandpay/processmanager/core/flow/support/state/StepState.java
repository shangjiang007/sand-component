package cn.com.sandpay.processmanager.core.flow.support.state;


import cn.com.sandpay.processmanager.core.Step;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionException2;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionStatus;
import cn.com.sandpay.processmanager.core.flow.FlowExecutor;
import cn.com.sandpay.processmanager.core.step.StepHolder;

public class StepState extends AbstractState implements StepHolder {

	private final Step step;

	public StepState(Step step) {
		super(step.getName());
		this.step = step;
	}

	public StepState(String name, Step step) {
		super(name);
		this.step = step;
	}

	@Override
	public FlowExecutionStatus handle(FlowExecutor executor) throws FlowExecutionException2 {
		// 执行step
		String executeResult = executor.executeStep(step);
		// 返回执行状态
		return new FlowExecutionStatus(executeResult);
	}

	public Step getStep() {
		return step;
	}
	
	public boolean isEndState() {
		return false;
	}

}
