package cn.com.sandpay.processmanager.core.job;

import cn.com.sandpay.processmanager.core.JobExecution;
import cn.com.sandpay.processmanager.core.Step;
import cn.com.sandpay.processmanager.core.StepExecution;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionException2;

public interface StepHandler {

	StepExecution handleStep(Step step, JobExecution jobExecution) throws FlowExecutionException2;

}
