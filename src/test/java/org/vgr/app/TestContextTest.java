package org.vgr.app;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.http.server.ServerLife;
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.annot.Service;
import org.vgr.ioc.annot.TestConfig;

@TestConfig(dependents= {
		                 "org.vgr.http.server.ServerLife",
		                 "org.vgr.http.server.ServerLogAspect",
						})
@Service
public class TestContextTest extends AbstractTest{
	private static final Logger LOG=LoggerFactory.getLogger(TestContextTest.class);
	
	@Inject
	ServerLife serveLife;
			
	@Test
	public void test() {
		//fail("Not yet implemented");
		LOG.info("Running : test");
		System.out.println();
		Assert.assertTrue(true);
	}
	
	@Test
	public void testAop() {
		serveLife.start();
		serveLife.stop();
	}
}