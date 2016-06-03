package cn.com.sandpay.processmanager.core.configuration.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import cn.com.sandpay.processmanager.core.flow.Flow;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionException2;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionStatus;
import cn.com.sandpay.processmanager.core.flow.FlowExecutor;
import cn.com.sandpay.processmanager.core.flow.FlowHolder;
import cn.com.sandpay.processmanager.core.flow.State;
import cn.com.sandpay.processmanager.core.flow.support.SimpleFlow;
import cn.com.sandpay.processmanager.core.flow.support.StateTransition;
import cn.com.sandpay.processmanager.core.flow.support.state.AbstractState;
import cn.com.sandpay.processmanager.core.flow.support.state.StepState;

public class SimpleFlowFactoryBean implements FactoryBean, InitializingBean {

	private String name;

	private List<StateTransition> stateTransitions;

	private String prefix;

	public void setName(String name) {
		this.name = name;
		this.prefix = name + ".";
	}

	public void setStateTransitions(List<StateTransition> stateTransitions) {
		this.stateTransitions = stateTransitions;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.hasText(name, "The flow must have a name");
	}

	public Object getObject() throws Exception {

		SimpleFlow flow = new SimpleFlow(name);

		List<StateTransition> updatedTransitions = new ArrayList<StateTransition>();
		for (StateTransition stateTransition : stateTransitions) {
			State state = getProxyState(stateTransition.getState());
			updatedTransitions.add(StateTransition.switchOriginAndDestination(stateTransition, state,
					getNext(stateTransition.getNext())));
		}

		flow.setStateTransitions(updatedTransitions);
		flow.afterPropertiesSet();
		return flow;

	}

	private String getNext(String next) {
		if (next == null) {
			return null;
		}
		return (next.startsWith(this.prefix) ? "" : this.prefix) + next;
	}

	private State getProxyState(State state) {
		String oldName = state.getName();
		if (oldName.startsWith(prefix)) {
			return state;
		}
		String stateName = prefix + oldName;
		if (state instanceof StepState) {
			return new StepState(stateName, ((StepState) state).getStep());
		}
		return new DelegateState(stateName, state);
	}

	public Class<?> getObjectType() {
		return SimpleFlow.class;
	}

	public boolean isSingleton() {
		return true;
	}

	private static class DelegateState extends AbstractState implements FlowHolder {
		private final State state;

		private DelegateState(String name, State state) {
			super(name);
			this.state = state;
		}

		public boolean isEndState() {
			return state.isEndState();
		}

		@Override
		public FlowExecutionStatus handle(FlowExecutor executor) throws FlowExecutionException2 {
			return state.handle(executor);
		}

		public Collection<Flow> getFlows() {
			return (state instanceof FlowHolder) ? ((FlowHolder)state).getFlows() : Collections.<Flow>emptyList();
		}
		
	}

}
