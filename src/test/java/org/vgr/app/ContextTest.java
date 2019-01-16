package org.vgr.app;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.vgr.ioc.core.AppContext;
import org.vgr.ioc.core.ClassesProvider;

public class ContextTest {
	
	@Test
	public void test() {
		//fail("Not yet implemented");
		ClassesProvider classesProvider=()->{
		 Set<String> classes=new HashSet<>();
		  classes.add("org.vgr.app.LogLevels");
		  return classes;
		};
		AppContext applicationContext=new AppContext(classesProvider);
		LogLevels  log=(LogLevels)applicationContext.getBean("logLevels");
		log.testLogleves();
		System.out.println();
		Assert.assertTrue(true);
	}
}
