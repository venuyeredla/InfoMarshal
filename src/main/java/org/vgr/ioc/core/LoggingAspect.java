package org.vgr.ioc.core;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAspect implements AopAdvices{
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
	
	public HashMap<String ,AopAdviceType> pointCuts(){
		HashMap<String,AopAdviceType> pointcuts=new HashMap<String, AopAdviceType>();
		pointcuts.put("org.vgr.ioc.core.AopIVehicle.start", AopAdviceType.BEFORE);
		pointcuts.put("org.vgr.ioc.core.AopIVehicle.stop", AopAdviceType.AFTER);
		return pointcuts;
	}
	
	
	
	
	
	
}
