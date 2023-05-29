package org.vgr.http.server;

import org.junit.Ignore;
import org.junit.Test;
import org.vgr.app.design.MutexingThreads;
import org.vgr.app.design.PubSubModel;


public class MultiThreadTest {
	
	@Test
	@Ignore
	public void testThread() {
		MutexingThreads multiThreading = new MutexingThreads();
		multiThreading.synchronizeThreads();
	}

	@Test
	public void pubsubModel() {
		PubSubModel pubsubModel=new PubSubModel();
		pubsubModel.runPubSubModel();
	}
	
	
	
}
