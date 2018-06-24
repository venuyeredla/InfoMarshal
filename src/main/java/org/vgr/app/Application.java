package org.vgr.app;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.http.server.HttpServer;
import org.vgr.ioc.core.ClassesProvider;
import org.vgr.ioc.core.IocContainer;
import org.vgr.ioc.core.IocMode;
/**
 * @author Vengopal
 */
public class Application{
	private static final Logger LOG=LoggerFactory.getLogger(Application.class);
	public static void main(String...strings) throws InterruptedException {
		LOG.debug("Application is gonna started...");
		IocContainer container = new IocContainer(new ClassesProivdeImpl().getClasses(),IocMode.WEB);
		HttpServer httpServer =(HttpServer)container.getBean("httpServer");
		httpServer.start();
		//httpServer.stop();
	}
}
class ClassesProivdeImpl implements ClassesProvider{
	@Override
	public Set<String> getClasses() {
		Set<String> classes=new HashSet<>();
		classes.add("org.vgr.ioc.core.JdbcTemplate");
		classes.add("org.vgr.app.controllers.AppController");
		classes.add("org.vgr.http.server.HttpServer");
		classes.add("org.vgr.ioc.core.RequestDispatcher");
		return classes;
	 }
}