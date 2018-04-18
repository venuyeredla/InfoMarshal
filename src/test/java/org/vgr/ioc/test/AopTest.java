package org.vgr.ioc.test;

import java.lang.reflect.InvocationTargetException;

import org.vgr.ioc.core.AopCar;
import org.vgr.ioc.core.AopIVehicle;
import org.vgr.ioc.core.AopProxyFactory;
import org.vgr.ioc.core.LoggingAspect;


public class AopTest {
	
	public static void main(String...strings) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		AopProxyFactory p=new AopProxyFactory();
		p.setTargetObject(new AopCar("Venugopal"));
		p.setInterfaceName("org.framework.aop.IVehicle");
		p.setAspect(new LoggingAspect());
 	    AopIVehicle xyz=(AopIVehicle)p.getProxy();
	    xyz.start();
	    xyz.stop();
	   
	}

}
