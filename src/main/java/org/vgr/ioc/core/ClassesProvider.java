package org.vgr.ioc.core;

import java.util.Set;

/**
 * Should return set of classes to scan for framework annotations.
 * @author vyeredla
 *
 */
@FunctionalInterface
public interface ClassesProvider {
	public Set<String> classesToScan();
}
