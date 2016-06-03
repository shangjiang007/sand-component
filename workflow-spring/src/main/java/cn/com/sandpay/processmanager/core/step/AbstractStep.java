package cn.com.sandpay.processmanager.core.step;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ClassUtils;

import cn.com.sandpay.processmanager.core.BatchStatus;
import cn.com.sandpay.processmanager.core.Step;
import cn.com.sandpay.processmanager.core.StepExecution;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionException2;

public abstract class AbstractStep implements Step, InitializingBean, BeanNameAware {

	private static final Log logger = LogFactory.getLog(AbstractStep.class);

	private String name;
    // 新增params、returns
	private String params;
	
	private String returns;
	
	private String route;

	public AbstractStep() {
		super();
	}

	public void afterPropertiesSet() throws Exception {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public final String getParams()
    {
        return params;
    }

    public final void setParams(String params)
    {
        this.params = params;
    }

    public final String getReturns()
    {
        return returns;
    }

    public final void setReturns(String returns)
    {
        this.returns = returns;
    }
    
    public final String getRoute()
    {
        return route;
    }

    public final void setRoute(String route)
    {
        this.route = route;
    }

    public void setBeanName(String name) {
		if (this.name == null) {
			this.name = name;
		}
	}

	public AbstractStep(String name) {
		this.name = name;
	}

	protected abstract void doExecute(StepExecution stepExecution) throws FlowExecutionException2;

	public final void execute(StepExecution stepExecution) throws FlowExecutionException2 {
		// FIXME 可以再次优化？
		
		stepExecution.setStatus(BatchStatus.STARTED);

		try {
			doExecute(stepExecution);
		}
		catch (FlowExecutionException2 e) {
			throw e;
		}
		
		stepExecution.upgradeStatus(BatchStatus.COMPLETED);
		
	}

	public String toString() {
		return ClassUtils.getShortName(getClass()) + ": [name=" + name + "]";
	}

}