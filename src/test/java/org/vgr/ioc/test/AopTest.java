package org.vgr.ioc.test;

import java.lang.reflect.InvocationTargetException;

import org.vgr.ioc.core.Car;
import org.vgr.ioc.core.IVehicle;
import org.vgr.ioc.core.LoggingAspect;
import org.vgr.ioc.core.ProxyFactory;


public class AopTest {
	
	public static void main(String...strings) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		ProxyFactory p=new ProxyFactory();
		p.setTargetObject(new Car("Venugopal"));
		p.setInterfaceName("org.framework.aop.IVehicle");
		p.setAspect(new LoggingAspect());
 	    IVehicle xyz=(IVehicle)p.getProxy();
	    xyz.start();
	    xyz.stop();
	   
	}

}
