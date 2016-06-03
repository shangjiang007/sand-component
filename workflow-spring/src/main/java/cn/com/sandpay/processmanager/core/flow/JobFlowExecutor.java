package cn.com.sandpay.processmanager.core.flow;


import java.util.Map;

import cn.com.sandpay.processmanager.core.BatchStatus;
import cn.com.sandpay.processmanager.core.ExitStatus;
import cn.com.sandpay.processmanager.core.JobExecution;
import cn.com.sandpay.processmanager.core.Step;
import cn.com.sandpay.processmanager.core.StepExecution;
import cn.com.sandpay.processmanager.core.job.SimpleStepHandler;
import cn.com.sandpay.processmanager.core.job.StepHandler;

public class JobFlowExecutor implements FlowExecutor {

	private final ThreadLocal<StepExecution> stepExecutionHolder = new ThreadLocal<StepExecution>();

	private final JobExecution execution;

	private ExitStatus exitStatus = ExitStatus.EXECUTING;

	private final StepHandler stepHandler;
	
	// 这个类是flow的调用入口类
	public JobFlowExecutor(Map<String, Object> map) {
		this.execution = new JobExecution(map);
		this.stepHandler = new SimpleStepHandler();
		stepExecutionHolder.set(null);
	}

	public JobFlowExecutor(JobExecution execution) {
		this.stepHandler = new SimpleStepHandler();
		this.execution = execution;
		stepExecutionHolder.set(null);
	}
	
	public JobFlowExecutor(StepHandler stepHandler, JobExecution execution) {
		this.stepHandler = stepHandler;
		this.execution = execution;
		stepExecutionHolder.set(null);
	}

	@Override
	public String executeStep(Step step) throws FlowExecutionException2 {
		StepExecution stepExecution = stepHandler.handleStep(step, execution);
		stepExecutionHolder.set(stepExecution);
		if (stepExecution == null) {
			return ExitStatus.COMPLETED.getExitCode();			
		}
		return stepExecution.getExitStatus().getExitCode();
	}

	@Override
	public void updateJobExecutionStatus(FlowExecutionStatus status) {
		execution.setStatus(findBatchStatus(status));
		exitStatus = exitStatus.and(new ExitStatus(status.getName()));
		execution.setExitStatus(exitStatus);
	}

	@Override
	public JobExecution getJobExecution() {
		return execution;
	}

	@Override
	public StepExecution getStepExecution() {
		return stepExecutionHolder.get();
	}

	@Override
	public void close(FlowExecution result) {
		stepExecutionHolder.set(null);
	}

	@Override
	public void addExitStatus(String code) {
		exitStatus = exitStatus.and(new ExitStatus(code));
	}

	// BatchStatus
	private BatchStatus findBatchStatus(FlowExecutionStatus status) {
		for (BatchStatus batchStatus : BatchStatus.values()) {
			if (status.getName().startsWith(batchStatus.toString())) {
				return batchStatus;
			}
		}
		return BatchStatus.UNKNOWN;
	}

}