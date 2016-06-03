package cn.com.sandpay.processmanager.core.flow.support.state;

import cn.com.sandpay.processmanager.core.flow.FlowExecutionException2;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionStatus;
import cn.com.sandpay.processmanager.core.flow.FlowExecutor;
import cn.com.sandpay.processmanager.core.flow.State;


public abstract class AbstractState implements State {
	
	private final String name;

	public AbstractState(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+": name=["+name+"]";
	}
	
	public abstract FlowExecutionStatus handle(FlowExecutor executor) throws FlowExecutionException2;

}
