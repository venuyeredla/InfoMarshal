package org.vgr.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.ioc.annot.After;
import org.vgr.ioc.annot.Aop;
import org.vgr.ioc.annot.Before;
import org.vgr.ioc.annot.Service;

@Service
@Aop(objInterface = IServerLifeCycle.class)
public class ServerLife implements IServerLifeCycle{
	private static final Logger LOG=LoggerFactory.getLogger(ServerLife.class);
	@Override
	@Before(aspectMethod = "before", aspectObj = "serverLogAspect" )
	public void start() {
		LOG.info("Server start in progress...");
	}

	@Override
	@After(aspectMethod="after", aspectObj = "serverLogAspect")
	public void stop() {
		LOG.info("Server stop in progress...");
	}
}
