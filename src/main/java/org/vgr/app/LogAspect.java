package org.vgr.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogAspect {
	private static final Logger LOGGER=LoggerFactory.getLogger(LogAspect.class);
	public void before(){
		LOGGER.info(" ********  Starting Server... **********");
	}
    public void after(){
		LOGGER.info(" ********  Server stopped...  **********");
	}
}
