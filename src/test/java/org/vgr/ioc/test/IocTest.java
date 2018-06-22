package org.vgr.ioc.test;


import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.vgr.app.ServerLife;
import org.vgr.app.IServerLifeCycle;
import org.vgr.app.LogLevels;
import org.vgr.app.LogAspect;
import org.vgr.ioc.core.AopProxyFactory;
import org.vgr.ioc.core.IocContainer;

public class IocTest{
	static IocContainer ioc=null;
	@BeforeClass
	public static void init() {
		Set<String> classes=new HashSet<String>();
		classes.add("org.vgr.app.LogLevels");
		ioc=new IocContainer(classes);
	}
	
	@Test
	@Ignore
	public void tetIoc() {
		LogLevels log=(LogLevels)ioc.getBean("log");
		log.testLogleves();
	}
	@Test
	public void testAop() {
		ServerLife car=new ServerLife();
		IServerLifeCycle proxyCar=(IServerLifeCycle)AopProxyFactory.getProxy(car, IServerLifeCycle.class, new LogAspect());
		proxyCar.start();
		proxyCar.stop();
	}
}