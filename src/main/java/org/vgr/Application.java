package org.vgr;

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
 * @author Venugopal
 */
public class Application{
	private static final Logger LOG=LoggerFactory.getLogger(Application.class);
	
	public static void main(String...strings){
		  String fileName="classes.txt";
		  LOG.info("Bootstrapping context from : {} ",fileName);
		  ClassesProvider provider=Application.initializeProvider(fileName);
		  provider.defaultMethod();
		  AppContext container = new AppContext(AppMode.WEB, provider);
		  LOG.debug("Application is gonna started...");
		  HttpServer httpServer =(HttpServer)container.getBean("httpServer");
		  httpServer.start();
	}
	
	 private static ClassesProvider initializeProvider(String fileName) {
		 //Lambda expression.
		  ClassesProvider provider=()->{
			     try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {
					return stream.collect(Collectors.toSet());
				 }catch (Exception e) {
					  e.printStackTrace();
					  System.err.println("Error in loading 'classess.txt' file...");
					  System.exit(-1);
				}
				return Collections.emptySet();
			};
			return provider;
	}
}