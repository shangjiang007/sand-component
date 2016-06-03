package cn.com.sandpay.processmanager.core.flow;


public class FlowExecution implements Comparable<FlowExecution> {

	private final String name;
	private final FlowExecutionStatus status;

	public FlowExecution(String name, FlowExecutionStatus status) {
		this.name = name;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public FlowExecutionStatus getStatus() {
		return status;
	}

	public int compareTo(FlowExecution other) {
		return this.status.compareTo(other.getStatus());
	}

	@Override
	public String toString() {
		return String.format("FlowExecution: name=%s, status=%s", name, status);
	}

}
