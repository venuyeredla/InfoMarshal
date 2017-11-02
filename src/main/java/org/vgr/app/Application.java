package org.vgr.app;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.http.server.HttpServer;
import org.vgr.ioc.core.ClassesProvider;
import org.vgr.ioc.core.IocContainer;
/**
 * @author Vengopal
 */
public class Application{
	private static final Logger LOG=LoggerFactory.getLogger(Application.class);
	public static void main(String...strings) throws InterruptedException {
		LOG.info("Application is gonna started...");
		IocContainer container = new IocContainer(new ClassesProivdeImpl().getClasses(null, null));
		HttpServer httpServer =(HttpServer)container.getBean("httpServer");
		httpServer.start();
		//httpServer.stop();
	}
}

class ClassesProivdeImpl implements ClassesProvider{
	@Override
	public Set<String> getClasses(String fileName, String classsName) {
		Set<String> classes=new HashSet<>();
		classes.add("org.vgr.ioc.jdbc.JDBCTemplate");
		classes.add("org.vgr.app.controllers.ProfileController");
		classes.add("org.vgr.app.controllers.FriendsController");
		classes.add("org.vgr.app.controllers.AdminController");
		classes.add("org.vgr.app.controllers.ForwardController");
		classes.add("org.vgr.app.controllers.FileUploadController");
		classes.add("org.vgr.app.controllers.SearchController");
		classes.add("org.vgr.app.controllers.AppController");
		classes.add("org.vgr.app.service.ProfileService");
		classes.add("org.vgr.app.service.LoginService");
		classes.add("org.vgr.app.service.AdminService");
		classes.add("org.vgr.app.service.FriendsService");
		classes.add("org.vgr.app.dao.ProfileDao");
		classes.add("org.vgr.app.dao.LoginDao");
		classes.add("org.vgr.app.dao.AdminDao");
		classes.add("org.vgr.app.dao.FriendsDao");
		classes.add("org.vgr.http.server.HttpServer");
		classes.add("org.vgr.ioc.web.RequestDispatcher");
		return classes;
	}
}