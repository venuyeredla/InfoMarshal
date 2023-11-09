package org.vgr.ioc.annot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.ioc.core.AppContext;
import org.vgr.ioc.core.AppMode;
import org.vgr.ioc.core.BeanDefinition;
import org.vgr.ioc.core.BeanProperty;
import org.vgr.ioc.core.ClassesProvider;
import org.vgr.ioc.core.HandlerConfig;

public class AnnotaionReader {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(AnnotaionReader.class);
	
	/**
	 * Scans annotated class builds application configuration.
	 */
	public static void generateConfig(AppContext iocContainer , ClassesProvider provider,AppMode mode){
		
			HashMap<String, BeanDefinition> beansConfigMap=new HashMap<String, BeanDefinition>();
			HashMap<String, HandlerConfig> handlers = new HashMap<String, HandlerConfig>();
			
			LOGGER.info("Scanning classes for annotations and creating appliction configuration.");
			provider.classesToScan().stream().forEach(className ->{
			  try {
				LOGGER.info("Reading class meta data."+className);
				Class<?> clazz=Class.forName(className);
				
				//Controller controller1 = clazz.getDeclaredAnnotation(Controller.class);
				Arrays.asList(clazz.getDeclaredAnnotations())
				    .stream()
				    .filter(classAnnotation -> classAnnotation instanceof Component ||  classAnnotation instanceof Controller || classAnnotation instanceof Service || classAnnotation instanceof Dao)
				    .findFirst().ifPresent(classAnnotation ->{
						 String beanId=null;
 						 BeanScope beanScope=null;
 						 if (classAnnotation instanceof Component) {
 							 Component component=(Component)classAnnotation;
 							 beanId=component.value();
 							 beanScope=component.scope();
 						 }else if(classAnnotation instanceof Controller){
							 Controller controller=(Controller)classAnnotation;
							 beanId=controller.value();
							 beanScope=controller.scope();
							 //To read handlers.
							 Arrays.asList(clazz.getDeclaredMethods()).stream()
							        .filter(method -> method.isAnnotationPresent(Handler.class))
							        .forEach(method->{
							        	Handler handlerAnnot=method.getDeclaredAnnotation(Handler.class);
							        	String controllerId=getBeanId(clazz.getName(),controller.value());
							        	HandlerConfig handlerConfig=new HandlerConfig(handlerAnnot.value(), controllerId, method.getName(),handlerAnnot.mimeType());								
										handlers.put(handlerAnnot.value(), handlerConfig);
							        });
						 	}else if(classAnnotation instanceof Service){
							 Service service=(Service)classAnnotation;
							 beanId=service.value();
							 beanScope=service.scope();
						    }else if(classAnnotation instanceof Dao){
						    	Dao dao=(Dao)classAnnotation;
						    	beanId=dao.value();
						    	beanScope=dao.scope();
						     }
						 beanId=getBeanId(clazz.getName(),beanId);
						 BeanDefinition beanDef=new BeanDefinition(beanId,clazz.getName(),beanScope);
						 List<BeanProperty> beanProperties=new ArrayList<BeanProperty>();
						 Arrays.asList(clazz.getDeclaredFields()).stream()
						 	   .filter(field -> field.isAnnotationPresent(Inject.class) )
						 	   .forEach(f ->{
						 		  Inject inject=f.getDeclaredAnnotation(Inject.class);
						 		  if(inject!=null) {
						 			 String refBeanId=getBeanId(f.getType().getName(), inject.value());
						 			 beanProperties.add(new BeanProperty(f.getName(),refBeanId));
						 		  }else if(f.getDeclaredAnnotation(Value.class)!=null) {
						 			 beanProperties.add(new BeanProperty(f.getName(), f.getDeclaredAnnotation(Value.class).value(), true));
						 		  }
						 	   });
						if(clazz.isAnnotationPresent(Aop.class)) {
							Aop aopAnnotaion=clazz.getDeclaredAnnotation(Aop.class);
							beanDef.setObjInterface(aopAnnotaion.objInterface());
							beanDef.setHasProxy(true);
						}
						beanDef.setProperties(beanProperties);
						beansConfigMap.put(beanId,beanDef);
				    });
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		  });
		    
		    iocContainer.setBeanContainer(beansConfigMap);
		    LOGGER.info("IOC managed beans = {"+beansConfigMap.keySet().stream().collect(Collectors.joining(",")) +"}");
		    if(mode==AppMode.WEB) {
		    	iocContainer.setHandlers(handlers);
				LOGGER.info("Handlers :{ "+handlers.keySet().stream().collect(Collectors.joining(","))+" }");
		    }
	}
	
	private static String getBeanId(String className, String beanId) {
		 if(StringUtils.isEmpty(beanId)) {
			 className=className.substring(className.lastIndexOf(".")+1, className.length());
			 beanId=className.substring(0, 1).toLowerCase() + className.substring(1);
		 }
		return beanId;
	}
	
}