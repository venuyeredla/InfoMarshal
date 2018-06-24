package org.vgr.app;

import java.util.Set;

import org.vgr.ioc.core.IocContainer;

public abstract class AbstractTest {
	protected Set<String> classes;
	
	public IocContainer getContainer(Set<String> classes){
		IocContainer ioc=new IocContainer(classes);
		return ioc;
	}
}
