package org.vgr.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.ioc.annot.Service;

@Service
public class ServerLogAspect {
	private static final Logger LOGGER=LoggerFactory.getLogger(ServerLogAspect.class);
	public void before(){
		LOGGER.info(" ********  Starting Server... **********");
	}
    public void after(){
		LOGGER.info(" ********  Server stopped...  **********");
	}
}
