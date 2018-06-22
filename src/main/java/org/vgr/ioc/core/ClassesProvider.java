package org.vgr.ioc.core;

import java.util.Set;

@FunctionalInterface
public interface ClassesProvider {
	public Set<String> getClasses(String fileName);
}
