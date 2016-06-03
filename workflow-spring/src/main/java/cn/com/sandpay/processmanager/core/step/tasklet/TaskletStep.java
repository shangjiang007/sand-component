package cn.com.sandpay.processmanager.core.step.tasklet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sandpay.processmanager.core.BatchStatus;
import cn.com.sandpay.processmanager.core.ExecutionContext;
import cn.com.sandpay.processmanager.core.StepExecution;
import cn.com.sandpay.processmanager.core.flow.FlowExecutionException2;
import cn.com.sandpay.processmanager.core.step.AbstractStep;

public class TaskletStep extends AbstractStep
{

    private static final Log logger = LogFactory.getLog(TaskletStep.class);

    private Tasklet tasklet;

    /**
     * Default constructor.
     */
    public TaskletStep()
    {
        this(null);
    }

    /**
     * @param name
     */
    public TaskletStep(String name)
    {
        super(name);
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        super.afterPropertiesSet();
    }

    /**
     * Public setter for the {@link Tasklet}.
     * 
     * @param tasklet
     *            the {@link Tasklet} to set
     */
    public void setTasklet(Tasklet tasklet)
    {
        this.tasklet = tasklet;
    }

    /**
     * Process the step and update its context so that progress can be monitored
     * by the caller. The step is broken down into chunks, each one executing in
     * a transaction. The step and its execution and execution context are all
     * given an up to date {@link BatchStatus}, and the {@link JobRepository} is
     * used to store the result. Various reporting information are also added to
     * the current context governing the step execution, which would normally be
     * available to the caller through the step's {@link ExecutionContext}.<br/>
     * 
     * @throws JobInterruptedException
     *             if the step or a chunk is interrupted
     * @throws RuntimeException
     *             if there is an exception during a chunk execution
     * 
     */
    @Override
    protected void doExecute(StepExecution stepExecution) throws FlowExecutionException2
    {
        // 打印步骤名称。 2013.11.20
        tasklet.logStepName("[STEP][" + this.getName() + "]," + "[PARAMS][" + this.getParams() + "]," + "[RETURNS]["
                + this.getReturns() + "]," + "[ROUTE][" + this.getRoute() + "]");
        // 新增params、returns 2015.11.2
        ExecutionContext exec = stepExecution.getExecutionContext();
        exec.put("taskletParams", this.getParams());
        exec.put("taskletReturns", this.getReturns());
        exec.put("taskletRoute", this.getRoute());
        // 执行步骤。
        tasklet.execute(exec);
    }

}
