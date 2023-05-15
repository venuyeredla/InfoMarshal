package org.vgr.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.ioc.annot.Service;

@Service
public class LogLevels { 
	private static final Logger LOG=LoggerFactory.getLogger(LogLevels.class);
	public boolean testLogleves() {
		//LOG.fatal("FATAL");
		LOG.error("ERROR");
		LOG.warn("WARN");
		LOG.info("INFO");
		LOG.debug("DEBUG");
		LOG.trace("TRACE");
		return true;
	}
}
