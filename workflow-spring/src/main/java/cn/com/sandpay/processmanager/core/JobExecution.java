package cn.com.sandpay.processmanager.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArraySet;



public class JobExecution {

	private volatile Collection<StepExecution> stepExecutions = new CopyOnWriteArraySet<StepExecution>();

	private volatile BatchStatus status = BatchStatus.STARTING;

	private volatile ExitStatus exitStatus = ExitStatus.UNKNOWN;

	private volatile ExecutionContext executionContext = new ExecutionContext();

	public JobExecution(Map<String, Object> map) {
		for (Entry<String, Object> entry : map.entrySet()) {
			executionContext.put(entry.getKey(), entry.getValue());
		}
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

	public void setExitStatus(ExitStatus exitStatus) {
		this.exitStatus = exitStatus;
	}

	public ExitStatus getExitStatus() {
		return exitStatus;
	}

	public Collection<StepExecution> getStepExecutions() {
		return Collections.unmodifiableList(new ArrayList<StepExecution>(stepExecutions));
	}

	public void setExecutionContext(ExecutionContext executionContext) {
		this.executionContext = executionContext;
	}

	public ExecutionContext getExecutionContext() {
		return executionContext;
	}

	void addStepExecution(StepExecution stepExecution) {
		stepExecutions.add(stepExecution);
	}

	public void addStepExecutions(List<StepExecution> stepExecutions) {
		if (stepExecutions!=null) {
			this.stepExecutions.removeAll(stepExecutions);
			this.stepExecutions.addAll(stepExecutions);
		}
	}
	
}