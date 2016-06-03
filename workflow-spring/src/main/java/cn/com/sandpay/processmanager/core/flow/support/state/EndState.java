package cn.com.sandpay.processmanager.core.flow.support.state;


import cn.com.sandpay.processmanager.core.BatchStatus;
import cn.com.sandpay.processmanager.core.StepExecution;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionException2;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionStatus;
import cn.com.sandpay.processmanager.core.flow.FlowExecutor;

public class EndState extends AbstractState {

	private final FlowExecutionStatus status;

	private final String code;

	public EndState(FlowExecutionStatus status, String name) {
		this(status, status.getName(), name);
	}

	public EndState(FlowExecutionStatus status, String code, String name) {
		this(status, code, name, false);
	}

	public EndState(FlowExecutionStatus status, String code, String name, boolean abandon) {
		super(name);
		this.status = status;
		this.code = code;
	}

	@Override
	public FlowExecutionStatus handle(FlowExecutor executor) throws FlowExecutionException2 {
		synchronized (executor) {
			// Special case. If the last step execution could not complete we
			// are in an unknown state (possibly unrecoverable).
			StepExecution stepExecution = executor.getStepExecution();
			if (stepExecution != null && executor.getStepExecution().getStatus() == BatchStatus.UNKNOWN) {
				return FlowExecutionStatus.UNKNOWN;
			}

			if (status.isStop()) {
				return FlowExecutionStatus.COMPLETED;
			}

			executor.addExitStatus(code);
			return status;

		}
	}

	public boolean isEndState() {
		return !status.isStop();
	}

	@Override
	public String toString() {
		return super.toString() + " status=[" + status + "]";
	}
}