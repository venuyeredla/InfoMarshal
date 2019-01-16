package org.vgr.app;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.http.server.ServerLife;
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.annot.Service;
import org.vgr.ioc.annot.TestConfig;

@TestConfig(dependents= {"org.vgr.app.LogLevels",
		                 "org.vgr.http.server.ServerLife",
		                 "org.vgr.http.server.ServerLogAspect",
						})
@Service
public class TestContextTest extends AbstractTest{
	private static final Logger LOG=LoggerFactory.getLogger(LogLevels.class);
	
	@Inject
	ServerLife serveLife;
	@Inject
	LogLevels log;
			
	@Test
	public void test() {
		//fail("Not yet implemented");
		log.testLogleves();
		LOG.info("Running : test");
		System.out.println();
		Assert.assertTrue(true);
	}
	
	@Test
	public void testSecond() {
		log.testLogleves();
	}
	
	@Test
	public void testAop() {
		serveLife.start();
		serveLife.stop();
	}
}