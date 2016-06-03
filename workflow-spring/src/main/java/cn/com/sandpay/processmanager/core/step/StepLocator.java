package cn.com.sandpay.processmanager.core.step;

import java.util.Collection;

import cn.com.sandpay.processmanager.core.Step;

public interface StepLocator {
	
	Collection<String> getStepNames();
	
	Step getStep(String stepName) throws NoSuchStepException;

}
