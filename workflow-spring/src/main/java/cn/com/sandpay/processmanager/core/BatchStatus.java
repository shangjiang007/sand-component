package cn.com.sandpay.processmanager.core;

public enum BatchStatus {

	COMPLETED, STARTING, STARTED, STOPPING, STOPPED, FAILED, ABANDONED, UNKNOWN;

	public static BatchStatus max(BatchStatus status1, BatchStatus status2) {
		return status1.isGreaterThan(status2) ? status1 : status2;
	}

	public boolean isRunning() {
		return this == STARTING || this == STARTED;
	}

	public boolean isUnsuccessful() {
		return this == FAILED || this.isGreaterThan(FAILED);
	}

	public BatchStatus upgradeTo(BatchStatus other) {
		if (isGreaterThan(STARTED) || other.isGreaterThan(STARTED)) {
			return max(this, other);
		}
		// Both less than or equal to STARTED
		if (this == COMPLETED || other == COMPLETED)
			return COMPLETED;
		return max(this, other);
	}

	public boolean isGreaterThan(BatchStatus other) {
		return this.compareTo(other) > 0;
	}

	public boolean isLessThan(BatchStatus other) {
		return this.compareTo(other) < 0;
	}

	public boolean isLessThanOrEqualTo(BatchStatus other) {
		return this.compareTo(other) <= 0;
	}

	public static BatchStatus match(String value) {
		for (BatchStatus status : values()) {
			if (value.startsWith(status.toString())) {
				return status;
			}
		}
		// Default match should be the lowest priority
		return COMPLETED;
	}
}
