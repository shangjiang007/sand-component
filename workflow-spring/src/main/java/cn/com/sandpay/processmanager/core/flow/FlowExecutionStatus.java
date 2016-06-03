package cn.com.sandpay.processmanager.core.flow;

public class FlowExecutionStatus implements Comparable<FlowExecutionStatus> {

	public static final FlowExecutionStatus COMPLETED = new FlowExecutionStatus(Status.COMPLETED.toString());

	public static final FlowExecutionStatus STOPPED = new FlowExecutionStatus(Status.STOPPED.toString());

	public static final FlowExecutionStatus FAILED = new FlowExecutionStatus(Status.FAILED.toString());

	public static final FlowExecutionStatus UNKNOWN = new FlowExecutionStatus(Status.UNKNOWN.toString());

	private final String name;

	private enum Status {

		COMPLETED, STOPPED, FAILED, UNKNOWN;

		static Status match(String value) {
			for (int i = 0; i < values().length; i++) {
				Status status = values()[i];
				if (value.startsWith(status.toString())) {
					return status;
				}
			}
			// Default match should be the lowest priority
			return COMPLETED;
		}

	};

	public FlowExecutionStatus(String status) {
		this.name = status;
	}

	public boolean isStop() {
		return name.startsWith(STOPPED.getName());
	}

	public boolean isFail() {
		return name.startsWith(FAILED.getName());
	}


	public boolean isEnd() {
		return isStop() || isFail() || isComplete();
	}

	private boolean isComplete() {
		return name.startsWith(COMPLETED.getName());
	}
	
	public int compareTo(FlowExecutionStatus other) {
		Status one = Status.match(this.name);
		Status two = Status.match(other.name);
		int comparison = one.compareTo(two);
		if (comparison == 0) {
			return this.name.compareTo(other.name);
		}
		return comparison;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof FlowExecutionStatus)) {
			return false;
		}
		FlowExecutionStatus other = (FlowExecutionStatus) object;
		return name.equals(other.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}

}
