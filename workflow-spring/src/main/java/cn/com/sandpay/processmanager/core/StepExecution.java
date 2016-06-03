package cn.com.sandpay.processmanager.core;

import org.springframework.util.Assert;


public class StepExecution {

	private final JobExecution jobExecution;

	private final String stepName;

	private volatile BatchStatus status = BatchStatus.STARTING;

	private volatile ExecutionContext executionContext = new ExecutionContext();

	private volatile ExitStatus exitStatus = ExitStatus.EXECUTING;

	public StepExecution(String stepName, JobExecution jobExecution) {
		super();
		Assert.hasLength(stepName);
		this.stepName = stepName;
		this.jobExecution = jobExecution;
	}

	public ExecutionContext getExecutionContext() {
		return executionContext;
	}

	public void setExecutionContext(ExecutionContext executionContext) {
		this.executionContext = executionContext;
	}

	public BatchStatus getStatus() {
		return status;
	}

	public void setStatus(BatchStatus status) {
		this.status = status;
	}

	public void upgradeStatus(BatchStatus status) {
		this.status = this.status.upgradeTo(status);
	}

	public String getStepName() {
		return stepName;
	}

	public void setExitStatus(ExitStatus exitStatus) {
		this.exitStatus = exitStatus;
	}

	public ExitStatus getExitStatus() {
		return exitStatus;
	}

	public JobExecution getJobExecution() {
		return jobExecution;
	}

}
