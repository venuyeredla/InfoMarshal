package org.vgr.app.common;

import org.vgr.ioc.core.BeanFactory;
import org.vgr.ioc.core.IocContainer;
import org.junit.BeforeClass;

public abstract class AbstractTest {
	public static BeanFactory factory=null;
	 @BeforeClass
      public static void beforClass() {
		  factory=new IocContainer("classes.txt");
	   }
}
