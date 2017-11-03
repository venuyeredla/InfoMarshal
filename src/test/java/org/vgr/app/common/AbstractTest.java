package org.vgr.app.common;

import java.util.Set;

import org.junit.BeforeClass;
import org.vgr.ioc.core.BeanFactory;
import org.vgr.ioc.core.IocContainer;

public abstract class AbstractTest {
	protected Set<String> classes;
	
	public BeanFactory factory=null;
	@BeforeClass
    public void beforClass() {
		  factory=new IocContainer(this.getClasses());
	}
    public abstract Set<String> getClasses();
}
