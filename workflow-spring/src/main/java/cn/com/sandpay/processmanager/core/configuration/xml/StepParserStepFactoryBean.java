package cn.com.sandpay.processmanager.core.configuration.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;

import cn.com.sandpay.processmanager.core.step.AbstractStep;
import cn.com.sandpay.processmanager.core.step.tasklet.Tasklet;
import cn.com.sandpay.processmanager.core.step.tasklet.TaskletStep;

class StepParserStepFactoryBean<I, O> implements FactoryBean, BeanNameAware {

	private static final Log logger = LogFactory.getLog(StepParserStepFactoryBean.class);

	private String name;
    // 新增params、returns
	private String params;
    
    private String returns;
    
    private String route;

	private Tasklet tasklet;

	public final Object getObject() throws Exception {
		if (tasklet != null) {
			TaskletStep ts = new TaskletStep();
			configureTaskletStep(ts);
			return ts;
		} else {
			logger.error("不可能");
			throw new RuntimeException("不可能");
		}
	}

	private void configureAbstractStep(AbstractStep ts) {
		if (name != null) {
			ts.setName(name);
		}
		if(params != null)
		{
		    ts.setParams(params);
		}
		if(returns != null)
		{
		    ts.setReturns(returns);
		}
		if(route != null)
		{
		    ts.setRoute(route);
		}
	}

	private void configureTaskletStep(TaskletStep ts) {
		configureAbstractStep(ts);
		if (tasklet != null) {
			ts.setTasklet(tasklet);
		}
	}

	public Class<TaskletStep> getObjectType() {
		return TaskletStep.class;
	}

	public boolean isSingleton() {
		return true;
	}

	// =========================================================
	// Step Attributes
	// =========================================================

	public void setBeanName(String name) {
		if (this.name == null) {
			this.name = name;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	// =========================================================
	// Tasklet Elements
	// =========================================================
	
	public void setTasklet(Tasklet tasklet) {
		this.tasklet = tasklet;
	}

    public final void setParams(String params)
    {
        this.params = params;
    }

    public final void setReturns(String returns)
    {
        this.returns = returns;
    }

    public final void setRoute(String route)
    {
        this.route = route;
    }
    
	
}
