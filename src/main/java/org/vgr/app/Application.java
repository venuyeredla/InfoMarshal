package org.vgr.app;

import org.vgr.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Vengopal
 */
public class Application{
	private static final Logger LOG=LoggerFactory.getLogger(Application.class);
	public static void main(String...strings) throws InterruptedException {
		LOG.info("Starting applicatin....");
		HttpServer httpServer=new HttpServer();
		httpServer.start();
		httpServer.stop();
	}
}
	