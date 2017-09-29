package org.vgr.ioc.annot;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.ioc.core.BeanDefinition;
import org.vgr.ioc.core.BeanProperty;
import org.vgr.ioc.core.IocContainer;
import org.vgr.ioc.web.HandlerConfig;

public class AnnotaionReader {
	private static final Logger LOGGER=LoggerFactory.getLogger(AnnotaionReader.class);
	public AnnotaionReader() {}
	public AnnotaionReader(IocContainer iocContainer,Set<String> classesToScan) {
		this.createConfig(iocContainer, classesToScan);
	}
	public AnnotaionReader(IocContainer iocContainer, String fileName,boolean isWeb){
		LOGGER.info("Web Resource?"+isWeb+"File : "+fileName);
		try {
		Set<String> classesToScan=null;
		if(!isWeb) {
			URL url=ClassLoader.getSystemResource(fileName);
			LOGGER.info("Absolute path : "+url.getPath());
			classesToScan = Files.lines(Paths.get(url.getFile())).filter(line -> !line.startsWith("#")).collect(Collectors.toSet());
		 }else {
			 classesToScan = Files.lines(Paths.get(fileName)).filter(line -> !line.startsWith("#")).collect(Collectors.toSet());
		 }
		this.createConfig(iocContainer, classesToScan);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Provides  application config by reading annotation information.
	 */
	private void createConfig(IocContainer iocContainer , Set<String> classesToScan){
			HashMap<String, BeanDefinition> beansConfigMap=new HashMap<String, BeanDefinition>();
			HashMap<String, HandlerConfig> handlers = new HashMap<String, HandlerConfig>();
		    classesToScan.stream().forEach(className ->{
			  try {
				Class<?> clazz=Class.forName(className);
				Arrays.asList(clazz.getDeclaredAnnotations())
				    .stream()
				    .filter(classAnnotation ->  classAnnotation instanceof Controller || classAnnotation instanceof Service || classAnnotation instanceof Dao)
				    .forEach(classAnnotation ->{
						 BeanDefinition beanDef=null;
						 String beandId=null;
						 if(classAnnotation instanceof Controller){
							 Controller controller=(Controller)classAnnotation;
							 beandId=controller.id();
							 beanDef=new BeanDefinition(beandId,clazz.getName(),controller.scope());
							 //To read handlers.
							 Arrays.asList(clazz.getDeclaredMethods()).stream()
							        .filter(method -> method.isAnnotationPresent(Handler.class))
							        .forEach(method -> {
							        	Arrays.asList(method.getDeclaredAnnotations()).stream()
							        	.filter(handler -> handler instanceof Handler)
							        	.forEach(handler -> {
							        		Handler h=(Handler)handler;
											HandlerConfig config=new HandlerConfig(h.path(), controller.id(), method.getName(),h.mimeType());								
											handlers.put(h.path(), config);
							        	});
							        });

						 	}else if(classAnnotation instanceof Service){
							 Service service=(Service)classAnnotation;
							 beandId=service.id();
							 beanDef=new BeanDefinition(beandId,clazz.getName(),service.scope());
						    }else if(classAnnotation instanceof Dao){
						    	Dao dao=(Dao)classAnnotation;
						    	beandId=dao.id();
						    	beanDef=new BeanDefinition(beandId,clazz.getName(),dao.scope());
						     }
						 
						 List<BeanProperty> beanProperties=new ArrayList<BeanProperty>();
						 Arrays.asList(clazz.getDeclaredFields())
						     .stream()
						     .forEach(field -> {
							    Arrays.asList(field.getAnnotations())
							      .stream()
							      .filter(fieldAnnotation -> fieldAnnotation instanceof Inject)
							      .forEach(fieldAnnotation ->{
							    	  Inject di=(Inject)fieldAnnotation;
							          if(di.ref().equals("")){
							    	  		beanProperties.add(new BeanProperty(field.getName(), di.value(), true));
							    	  }else{
										   beanProperties.add(new BeanProperty(field.getName(), di.ref()));
									  }
							  });
						  });
						beanDef.setProperties(beanProperties);
						beansConfigMap.put(beandId,beanDef);
				    });
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		  });
		    
		    iocContainer.setBeanContainer(beansConfigMap);
		    iocContainer.setHandlers(handlers);
			LOGGER.info("Handlers :{ "+handlers.keySet().stream().collect(Collectors.joining(","))+" }");
			LOGGER.info("Beans : { "+beansConfigMap.keySet().stream().collect(Collectors.joining(",")) +" }");
	}
}