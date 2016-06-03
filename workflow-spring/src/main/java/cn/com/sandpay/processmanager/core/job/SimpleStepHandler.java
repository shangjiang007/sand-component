package cn.com.sandpay.processmanager.core.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import cn.com.sandpay.processmanager.core.BatchStatus;
import cn.com.sandpay.processmanager.core.JobExecution;
import cn.com.sandpay.processmanager.core.Step;
import cn.com.sandpay.processmanager.core.StepExecution;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionException2;

public class SimpleStepHandler implements StepHandler, InitializingBean {

	private static final Log logger = LogFactory.getLog(SimpleStepHandler.class);

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	public StepExecution handleStep(Step step, JobExecution execution) throws FlowExecutionException2 {
		// 相较于原始代码，改动了特别多
		StepExecution currentStepExecution = new StepExecution(step.getName(), execution);
		currentStepExecution.setExecutionContext(execution.getExecutionContext());
		// 这个日志会体现在系统日志中
		// 2013.11.19 不再打日志了。
		//logger.info("执行步骤: [" + step.getName() + "]");
		try {
			step.execute(currentStepExecution);
		}
		catch (FlowExecutionException2 e) {
			throw e;
		}

		if (currentStepExecution.getStatus() == BatchStatus.STOPPING 
		 || currentStepExecution.getStatus() == BatchStatus.STOPPED) {
			execution.setStatus(BatchStatus.STOPPING);
			throw new FlowExecutionException2("Job interrupted by step execution");
		}

		return currentStepExecution;
	}

}
