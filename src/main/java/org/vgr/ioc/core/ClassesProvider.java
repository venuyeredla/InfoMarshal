package org.vgr.ioc.core;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Should return set of classes to scan for framework annotations.
 * @author vyeredla
 *
 */
@FunctionalInterface
public interface ClassesProvider {
	static final Logger LOG=LoggerFactory.getLogger(ClassesProvider.class);
	
	public Set<String> classesToScan();
	default void defaultMethod () {
		LOG.info("Coming from deafult method");
	}
}
