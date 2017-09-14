package org.vgr.ioc.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import org.vgr.ioc.annot.AnnotaionReader;
import org.vgr.ioc.web.HandlerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

    public class IocContainer implements BeanFactory {
	private static final Logger LOGGER=LoggerFactory.getLogger(IocContainer.class);
	private HashMap<String,BeanDefinition> beanContainer=null;
	private HashMap<String, HandlerConfig>  handlers=null;
	private static final String SINGLTON="singleton";
	private static final String PROTOTYPE="prototype";
	public IocContainer(){}
	
	public IocContainer(String classPathTxt){
		new AnnotaionReader().classPath(this,classPathTxt);
		this.initializeSingletons();
	}
	
	public IocContainer(String classPathTxt,boolean web){
		new AnnotaionReader().webPath(this,classPathTxt);
		this.initializeSingletons();
	}
	
	private void initializeSingletons(){
		beanContainer.forEach((key,beanDef) -> {
			if(beanDef.getScope().equals(SINGLTON)){
				Object obj=BeanCreation.createNewBean(key ,this);
				beanDef.setObject(obj);
			 }
			else{
				beanDef.setObject(null);
			 }
		});
	}
	
	public Object getBean(String beanID) {
		BeanDefinition beanDefinition= beanContainer.get(beanID);
		Object object=null;
		if(SINGLTON.equals(beanDefinition.getScope())){
			if(beanDefinition.getObject()!=null)
			object=beanDefinition.getObject();	
			else{
				 object=BeanCreation.createNewBean(beanID,this);
				 beanDefinition.setObject(object);
			}
		}
		else if (PROTOTYPE.equals(beanDefinition.getScope())) {
			   object=BeanCreation.createNewBean(beanID,this);
		   }
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
