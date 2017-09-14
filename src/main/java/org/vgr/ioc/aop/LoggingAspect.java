package org.vgr.ioc.aop;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAspect implements Advices{
	private static final Logger LOGGER=LoggerFactory.getLogger(LoggingAspect.class);
	public void before(){
		LOGGER.info(" ********  Before Advice **********");
	}
    public void after(){
		LOGGER.info(" ********  After Advice **********");
	}

	public void afterReturning() {
		LOGGER.info(" ********  afterReturning Advice **********");
	}

	public void afterThrowing() {
		LOGGER.info(" ********  afterThrowing Advice **********");
	}
	
	public HashMap<String ,String> pointCuts(){
		
		HashMap<String,String> pointcuts=new HashMap<String, String>();
		pointcuts.put("org.framework.aop.IVehicle.start", "before");
		pointcuts.put("org.framework.aop.IVehicle.stop", "after");
		return pointcuts;
	}
	
	
	
	
	
	
}
