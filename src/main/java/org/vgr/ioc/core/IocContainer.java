package org.vgr.ioc.core;

import static org.vgr.ioc.annot.BeanScope.PROTOTYPE;
import static org.vgr.ioc.annot.BeanScope.SINGLETON;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.ioc.annot.AnnotaionReader;
   
public class IocContainer implements BeanFactory {
	private static final Logger LOG=LoggerFactory.getLogger(IocContainer.class);
	private HashMap<String,BeanDefinition> beanContainer=null;
	private HashMap<String, HandlerConfig>  handlers=null;
	public IocContainer(Set<String> classes){
		System.out.println("****   Starting ioc container   ******");
		LOG.info("****   Starting ioc container   ******");
		new AnnotaionReader(this,classes);
		this.initializeSingletons();
	}
	
	public IocContainer(Set<String> classes,IocMode mode){
		System.out.println("****   Starting ioc container   ******");
		LOG.info("****   Starting ioc container   ******");
		new AnnotaionReader(this,classes,mode);
		this.initializeSingletons();
	}
	
	private void initializeSingletons(){
		LOG.debug("Initializing singleton beans...");
		beanContainer.values().stream().filter(beanDef -> beanDef.getScope()==SINGLETON).forEach(beanDef-> beanDef.setObject(BeanCreation.createNewBean(beanDef.getId(),this)));
	   }
	
	public Object getBean(String beanID) {
		BeanDefinition beanDefinition= beanContainer.get(beanID);
		if(beanDefinition==null) {
			LOG.error("Bean doesn't exist. beadid : "+beanID);
			LOG.error("Exiting the application");
			System.exit(1);
		}
		Object object=null;
		if(beanDefinition.getScope()==SINGLETON){
			if(beanDefinition.getObject()!=null)
				object=beanDefinition.getObject();	
			else{
				object=BeanCreation.createNewBean(beanID,this);
				beanDefinition.setObject(object);
			}
		 }else if (beanDefinition.getScope()==PROTOTYPE) {
			   object=BeanCreation.createNewBean(beanID,this);
		   }
		 this.injectContainer(object);
		 return object;
	}
	
	
	public boolean isValidPath(String path){
		return handlers.keySet().contains(path);
	}
	public HandlerConfig getHandlerConfig(String path){
		return handlers.get(path);
	}
	
	static class BeanCreation {
		private static final   String INT_TYPE="int";
		private static final   String LONG_TYPE="long";
		private static final   String BYTE_TYPE="byte";
		private static final   String BOOLEAN_TYPE="boolean";
		private static final   String FLOAT_TYPE="float";
		private static final   String DOUBLE_TYPE="double";
		private static final   String CHAR_TYPE="char";
		private static final   String SHORT_TYPE="short";
		
		public static Object  createNewBean(String beanName,  IocContainer iocContainer ) {
		   HashMap<String, BeanDefinition> container=iocContainer.getBeanContainer();
		   BeanDefinition beanDef= container.get(beanName);
		   try {
			   Class<?> clazz = Class.forName(beanDef.getClassName());
			   final Object object=clazz.newInstance();
			   beanDef.getProperties().stream().forEach(beanProp ->{
				   String propertyName=beanProp.getName().trim();
				   String setMethod="set"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
				   Arrays.asList(clazz.getDeclaredMethods()).stream()
						   .filter(method -> setMethod.equals(method.getName()))
						   .findFirst().ifPresent(method -> {
							   if(beanProp.isPrimitive()){ 
								   injectPrimitive(object, method, beanProp.getValue());
							    } else{
							        Object value=iocContainer.getBean(beanProp.getRef());
							        injectRef(object, method, value);
							     }
					   });
			   });
			   return object;
		   
		   } catch (Exception e) {
			  e.printStackTrace();
		   }
		   return null;
			}
		    
		private static void injectPrimitive(Object obj ,Method method,String properyValue ){
		    	 try {
			      Object value=null;
			      Class<?>[] pType=method.getParameterTypes();
	   		      String type= pType[0].getName();
			        switch (type) {
					case INT_TYPE:    value=new Integer(properyValue);   break;
					case LONG_TYPE:   value=new Long(properyValue);    break;
					case BYTE_TYPE:   value=new Byte(properyValue);    break;
					case BOOLEAN_TYPE: value=new Boolean(properyValue);      break;
					case FLOAT_TYPE:    value=new Float(properyValue);   break;
					case DOUBLE_TYPE:   value=new Double(properyValue);    break;
					case SHORT_TYPE:     value=new Short(properyValue);  break;
					case CHAR_TYPE:      value=new Character(properyValue.charAt(0)); break;
					default: 		value=properyValue;	break;
					}
			        injectRef(obj, method, value);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				
		         }
	      }
	
    private static void injectRef(Object object ,Method method,Object value ){
    	  try {
			  method.invoke(object, value);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
      }

    public void injectContainer(Object obj) {
    	if(obj instanceof ContainerAware) {
    		((ContainerAware) obj).setContainer(this);
    		}
    	}
    
	@Override
	public boolean destroy() {
		this.beanContainer=null;
		return true;
	}
	
	public int beansSize() {
		return 0;
	}

	public boolean isBeanExisted(String beanId) {
		return false;
	}

	public HashMap<String, BeanDefinition> getBeanContainer() {
		return beanContainer;
	}

	public void setBeanContainer(HashMap<String, BeanDefinition> beanContainer) {
		this.beanContainer = beanContainer;
	}

	public HashMap<String, HandlerConfig> getHandlers() {
		return handlers;
	}

	public void setHandlers(HashMap<String, HandlerConfig> handlers) {
		this.handlers = handlers;
	}
    
    
}
