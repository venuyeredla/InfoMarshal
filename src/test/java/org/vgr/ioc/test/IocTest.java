package org.vgr.ioc.test;


import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.vgr.app.LogLevels;
import org.vgr.ioc.core.AopCar;
import org.vgr.ioc.core.AopIVehicle;
import org.vgr.ioc.core.AopProxyFactory;
import org.vgr.ioc.core.IocContainer;
import org.vgr.ioc.core.LoggingAspect;

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
		AopCar car=new AopCar("Venugopal");
		AopIVehicle proxyCar=(AopIVehicle)AopProxyFactory.getProxy(car, AopIVehicle.class, new LoggingAspect());
		proxyCar.start();
		proxyCar.stop();
	}
}