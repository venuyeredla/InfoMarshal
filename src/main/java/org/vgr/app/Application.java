package org.vgr.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.http.server.HttpServer;
import org.vgr.ioc.core.IocContainer;
/**
 * @author Vengopal
 */
public class Application{
	private static final Logger LOG=LoggerFactory.getLogger(Application.class);
	public static void main(String...strings) throws InterruptedException {
		LOG.info("Reading classes from the file :classes.json");
		IocContainer container = new IocContainer("classes.json");
		HttpServer httpServer =(HttpServer)container.getBean("httpServer");
		httpServer.start();
		//httpServer.stop();
	}
}
	