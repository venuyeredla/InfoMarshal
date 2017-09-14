package org.vgr.ioc.test;

import static org.junit.Assert.assertNotNull;

import org.vgr.app.common.AbstractTest;
import org.vgr.app.controllers.ForwardController;
import org.junit.AfterClass;
import org.junit.Test;

public class IocTest extends AbstractTest{
	
	@Test
	public void test(){
	 ForwardController forwardController=(ForwardController)factory.getBean("forwardController");
	 assertNotNull(forwardController);
	}
	
	@AfterClass
	public static void destroy()
	{
		factory.destroy();
	}
}
