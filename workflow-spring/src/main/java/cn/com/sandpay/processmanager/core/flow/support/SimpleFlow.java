package cn.com.sandpay.processmanager.core.flow.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import cn.com.sandpay.processmanager.core.flow.Flow;
import cn.com.sandpay.processmanager.core.flow.FlowExecution;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionException2;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionStatus;
import cn.com.sandpay.processmanager.core.flow.FlowExecutor;
import cn.com.sandpay.processmanager.core.flow.State;

public class SimpleFlow implements Flow, InitializingBean {

	private static final Log logger = LogFactory.getLog(SimpleFlow.class);

	private State startState;

	private Map<String, SortedSet<StateTransition>> transitionMap = new HashMap<String, SortedSet<StateTransition>>();

	private Map<String, State> stateMap = new HashMap<String, State>();

	private List<StateTransition> stateTransitions = new ArrayList<StateTransition>();

	private final String name;

	public SimpleFlow(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setStateTransitions(List<StateTransition> stateTransitions) {
		this.stateTransitions = stateTransitions;
	}

	public void afterPropertiesSet() throws Exception {
		initializeTransitions();
	}

	public FlowExecution start(FlowExecutor executor) throws FlowExecutionException2 {
		logger.debug("SimpleFlow 开始");
		if (startState == null) {
			initializeTransitions();
		}
		State state = startState;
		String stateName = state.getName();
		return resume(stateName, executor);
	}

	// 原来是接口方法，现改为private
	private FlowExecution resume(String stateName, FlowExecutor executor) throws FlowExecutionException2 {
		
		FlowExecutionStatus status = FlowExecutionStatus.UNKNOWN;
		State state = stateMap.get(stateName);

		logger.debug("Resuming state="+stateName+" with status="+status);

		// Terminate if there are no more states
		while (state != null && status!=FlowExecutionStatus.STOPPED) {

			stateName = state.getName();

			try {
				logger.debug("Handling state="+stateName);
				status = state.handle(executor);
			}
			catch (FlowExecutionException2 e) {
				executor.close(new FlowExecution(stateName, status));
				throw e;
			}
			
			logger.debug("Completed state="+stateName+" with status="+status);

			state = nextState(stateName, status);

		}

		FlowExecution result = new FlowExecution(stateName, status);
		executor.close(result);
		
		logger.debug("SimpleFlow 结束");
		return result;

	}

	private State nextState(String stateName, FlowExecutionStatus status) throws FlowExecutionException2 {

		Set<StateTransition> set = transitionMap.get(stateName);

		if (set == null) {
			throw new FlowExecutionException2(String.format("No transitions found in flow=%s for state=%s", getName(), stateName));
		}

		String next = null;
		String exitCode = status.getName();
		for (StateTransition stateTransition : set) {
			if (stateTransition.matches(exitCode)) {
				if (stateTransition.isEnd()) {
					// End of job
					return null;
				}
				next = stateTransition.getNext();
				break;
			}
		}

		if (next == null) {
			throw new FlowExecutionException2(String.format("Next state not found in flow=%s for state=%s with exit status=%s", getName(), stateName, status.getName()));
		}

		if (!stateMap.containsKey(next)) {
			throw new FlowExecutionException2(String.format("Next state not specified in flow=%s for next=%s", getName(), next));
		}

		return stateMap.get(next);

	}

	private void initializeTransitions() {
		startState = null;
		transitionMap.clear();
		stateMap.clear();
		boolean hasEndStep = false;

		if (stateTransitions.isEmpty()) {
			throw new IllegalArgumentException("No start state was found. You must specify at least one step in a job.");
		}

		for (StateTransition stateTransition : stateTransitions) {
			State state = stateTransition.getState();
			String stateName = state.getName();
			stateMap.put(stateName, state);
		}

		for (StateTransition stateTransition : stateTransitions) {

			State state = stateTransition.getState();

			if (!stateTransition.isEnd()) {

				String next = stateTransition.getNext();

				if (!stateMap.containsKey(next)) {
					throw new IllegalArgumentException("Missing state for [" + stateTransition + "]");
				}

			}
			else {
				hasEndStep = true;
			}

			String name = state.getName();

			SortedSet<StateTransition> set = transitionMap.get(name);
			if (set == null) {
				set = new TreeSet<StateTransition>();
				transitionMap.put(name, set);
			}
			set.add(stateTransition);

		}

		if (!hasEndStep) {
			throw new IllegalArgumentException("No end state was found.  You must specify at least one transition with no next state.");
		}

		startState = stateTransitions.get(0).getState();

	}
}
