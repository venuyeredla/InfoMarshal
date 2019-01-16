package org.vgr.app;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.http.server.HttpServer;
import org.vgr.ioc.core.ClassesProvider;
import org.vgr.ioc.core.AppContext;
import org.vgr.ioc.core.AppMode;
/**
 * @author Vengopal
 */
public class Application{
	private static final Logger LOG=LoggerFactory.getLogger(Application.class);
	public static void main(String...strings) throws InterruptedException {
		  ClassesProvider provider=()->{
			  try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource("classes.txt").toURI()))) {
					return stream.collect(Collectors.toSet());
				 }catch (Exception e) {
					  e.printStackTrace();
					  System.err.println("Error in loading 'classess.txt' file...");
					  System.exit(-1);
				}
				return Collections.emptySet();
			};
		  LOG.debug("Application is gonna started...");
		  AppContext container = new AppContext(AppMode.WEB, provider);
		  HttpServer httpServer =(HttpServer)container.getBean("httpServer");
		  httpServer.start();
	}
}

	