package cn.com.sandpay.processmanager.core.flow.support;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import cn.com.sandpay.processmanager.core.flow.State;
import cn.com.sandpay.processmanager.support.PatternMatcher;

public final class StateTransition implements Comparable<StateTransition> {

	private final State state;

	private final String pattern;

	private final String next;

	public static StateTransition createEndStateTransition(State state) {
		return createStateTransition(state, null, null);
	}

	public static StateTransition createEndStateTransition(State state, String pattern) {
		return createStateTransition(state, pattern, null);
	}

	public static StateTransition switchOriginAndDestination(StateTransition stateTransition, State state, String next) {
		return createStateTransition(state, stateTransition.pattern, next);
	}

	public static StateTransition createStateTransition(State state, String next) {
		return createStateTransition(state, null, next);
	}

	public static StateTransition createStateTransition(State state, String pattern, String next) {
		return new StateTransition(state, pattern, next);
	}

	private StateTransition(State state, String pattern, String next) {
		super();
		if (!StringUtils.hasText(pattern)) {
			this.pattern = "*";
		}
		else {
			this.pattern = pattern;
		}

		Assert.notNull(state, "A state is required for a StateTransition");
		if (state.isEndState() && StringUtils.hasText(next)) {
			throw new IllegalStateException("End state cannot have next: " + state);
		}

		this.next = next;
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public String getNext() {
		return next;
	}

	public boolean matches(String status) {
		return PatternMatcher.match(pattern, status);
	}

	public boolean isEnd() {
		return next == null;
	}

	public int compareTo(StateTransition other) {
		String value = other.pattern;
		if (pattern.equals(value)) {
			return 0;
		}
		int patternCount = StringUtils.countOccurrencesOf(pattern, "*");
		int valueCount = StringUtils.countOccurrencesOf(value, "*");
		if (patternCount > valueCount) {
			return 1;
		}
		if (patternCount < valueCount) {
			return -1;
		}
		patternCount = StringUtils.countOccurrencesOf(pattern, "?");
		valueCount = StringUtils.countOccurrencesOf(value, "?");
		if (patternCount > valueCount) {
			return 1;
		}
		if (patternCount < valueCount) {
			return -1;
		}
		return pattern.compareTo(value);
	}

	public String toString() {
		return String.format("StateTransition: [state=%s, pattern=%s, next=%s]",
				state == null ? null : state.getName(), pattern, next);
	}

}
