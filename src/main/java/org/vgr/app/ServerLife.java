package org.vgr.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.ioc.annot.After;
import org.vgr.ioc.annot.Before;

public class ServerLife implements IServerLifeCycle{
	
	private static final Logger LOG=LoggerFactory.getLogger(ServerLife.class);

	@Override
	@Before(objInterface = IServerLifeCycle.class,method="before")
	public void start() {
		LOG.info("Server start in progress...");
	}

	@Override
	@After(objInterface = IServerLifeCycle.class,method="after")
	public void stop() {
		LOG.info("Server stop in progress...");
	}
}
