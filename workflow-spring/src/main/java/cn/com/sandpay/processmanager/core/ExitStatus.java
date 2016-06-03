package cn.com.sandpay.processmanager.core;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import org.springframework.util.StringUtils;

public class ExitStatus implements Serializable, Comparable<ExitStatus> {

	public static final ExitStatus UNKNOWN = new ExitStatus("UNKNOWN");

	public static final ExitStatus EXECUTING = new ExitStatus("EXECUTING");

	public static final ExitStatus COMPLETED = new ExitStatus("COMPLETED");

	public static final ExitStatus NOOP = new ExitStatus("NOOP");

	public static final ExitStatus FAILED = new ExitStatus("FAILED");

	public static final ExitStatus STOPPED = new ExitStatus("STOPPED");

	private final String exitCode;

	private final String exitDescription;

	public ExitStatus(String exitCode) {
		this(exitCode, "");
	}

	public ExitStatus(String exitCode, String exitDescription) {
		super();
		this.exitCode = exitCode;
		this.exitDescription = exitDescription == null ? "" : exitDescription;
	}

	public String getExitCode() {
		return exitCode;
	}

	public String getExitDescription() {
		return exitDescription;
	}

	public ExitStatus and(ExitStatus status) {
		if (status == null) {
			return this;
		}
		ExitStatus result = addExitDescription(status.exitDescription);
		if (compareTo(status) < 0) {
			result = result.replaceExitCode(status.exitCode);
		}
		return result;
	}
	
	public int compareTo(ExitStatus status) {
		if (severity(status) > severity(this)) {
			return -1;
		}
		if (severity(status) < severity(this)) {
			return 1;
		}
		return this.getExitCode().compareTo(status.getExitCode());
	}

	private int severity(ExitStatus status) {
		if (status.exitCode.startsWith(EXECUTING.exitCode)) {
			return 1;
		}
		if (status.exitCode.startsWith(COMPLETED.exitCode)) {
			return 2;
		}
		if (status.exitCode.startsWith(NOOP.exitCode)) {
			return 3;
		}
		if (status.exitCode.startsWith(STOPPED.exitCode)) {
			return 4;
		}
		if (status.exitCode.startsWith(FAILED.exitCode)) {
			return 5;
		}
		if (status.exitCode.startsWith(UNKNOWN.exitCode)) {
			return 6;
		}
		return 7;
	}

	public String toString() {
		return String.format("exitCode=%s;exitDescription=%s", exitCode, exitDescription);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		return toString().equals(obj.toString());
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public ExitStatus replaceExitCode(String code) {
		return new ExitStatus(code, exitDescription);
	}

	public boolean isRunning() {
		return "RUNNING".equals(this.exitCode) || "UNKNOWN".equals(this.exitCode);
	}

	public ExitStatus addExitDescription(String description) {
		StringBuffer buffer = new StringBuffer();
		boolean changed = StringUtils.hasText(description) && !exitDescription.equals(description);
		if (StringUtils.hasText(exitDescription)) {
			buffer.append(exitDescription);
			if (changed) {
				buffer.append("; ");
			}
		}
		if (changed) {
			buffer.append(description);
		}
		return new ExitStatus(exitCode, buffer.toString());
	}

	public ExitStatus addExitDescription(Throwable throwable) {
		StringWriter writer = new StringWriter();
		throwable.printStackTrace(new PrintWriter(writer));
		String message = writer.toString();
		return addExitDescription(message);
	}

}
