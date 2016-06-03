package cn.com.sandpay.processmanager.core.flow;

import cn.com.sandpay.processmanager.core.JobExecution;
import cn.com.sandpay.processmanager.core.StepExecution;

public interface JobExecutionDecider {

	FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution);

}
