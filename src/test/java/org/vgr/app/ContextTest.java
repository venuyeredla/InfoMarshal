package org.vgr.app;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.vgr.Application;
import org.vgr.ioc.core.AppContext;
import org.vgr.ioc.core.ClassesProvider;

public class ContextTest {
	
	@Test
	public void test() {
		//fail("Not yet implemented");
		ClassesProvider classesProvider=()->{
		  return Stream.of("org.vgr.Application").collect(Collectors.toSet());
		};
		AppContext applicationContext=new AppContext(classesProvider);
		Application  log=(Application)applicationContext.getBean("application");
		log.testLogleves();
		System.out.println();
		Assert.assertTrue(true);
	}
}
