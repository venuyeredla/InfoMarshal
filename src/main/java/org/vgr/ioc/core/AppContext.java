package org.vgr.ioc.core;

import static org.vgr.ioc.annot.BeanScope.SINGLETON;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.http.server.IServerLifeCycle;
import org.vgr.ioc.annot.After;
import org.vgr.ioc.annot.AnnotaionReader;
import org.vgr.ioc.annot.AopAdviceType;
import org.vgr.ioc.annot.Around;
import org.vgr.ioc.annot.Before;
   
/**
 * Implements IOC/DI inject pattern and maintains application beans.
 * @author vyeredla
 *
 */
public class AppContext implements BeanFactory{
	private static final Logger LOG=LoggerFactory.getLogger(AppContext.class);
	public static final   String INT_TYPE="int";
	public static final   String LONG_TYPE="long";
	public static final   String BYTE_TYPE="byte";
	public static final   String BOOLEAN_TYPE="boolean";
	public static final   String FLOAT_TYPE="float";
	public static final   String DOUBLE_TYPE="double";
	public static final   String CHAR_TYPE="char";
	public static final   String SHORT_TYPE="short";
	
	private HashMap<String,BeanDefinition> beanConfigs=null;
	private HashMap<String, HandlerConfig>  handlersConfigs=null;
	public AppContext(ClassesProvider provider){
   		this(AppMode.CORE,provider);
    }
	public AppContext(AppMode iocMode,ClassesProvider provider){
		 this.loadContext(provider,iocMode);
	  }
	
	/**
	 * Loads and initializes the context.
	 * @param iocConfig
	 * @param iocMode
	 */
	private void loadContext(ClassesProvider provider,AppMode iocMode) {
		LOG.info("Initilizing IOC container.");
		new AnnotaionReader(this, provider,iocMode);
		LOG.debug("Initializing singleton beans...");
		beanConfigs.values().stream()
							.filter(beanDef -> beanDef.getScope()==SINGLETON)
		                    .forEach(beanDef-> beanDef.setObject(this.getNewBean(beanDef.getId())));
	 }
	
	public Object getBean(String beanId) {
		try {
			BeanDefinition beanDefinition=Optional.of(beanConfigs.get(beanId))
												  .orElseThrow(() -> new Exception("Invalid Bean id :"+beanId));
			Object obj=null;
			switch (beanDefinition.getScope()) {
			case SINGLETON:
				obj=Optional.ofNullable(beanDefinition.getObject()).
							 orElseGet(()-> {
								        beanDefinition.setObject(this.getNewBean(beanId));
										return beanDefinition.getObject();}
							        );
				 break;
			case PROTOTYPE:
				obj=this.getNewBean(beanId);
				 break;
			default:
				break;
			}
		  this.injectContainer(obj);
		/*  if(beanDefinition.isHasProxy()) {
			  return beanDefinition.getProxy();
		   }*/
		  return obj;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			LOG.error("Exiting the application");
			System.exit(1);
		}
		 return null;
	}
	
	/**
	 * Creates bean based on bean defintion.
	 * @param beanName
	 * @return
	 */
	public Object getNewBean(String beanName) {
		   BeanDefinition beanDef= this.beanConfigs.get(beanName);
		   try {
			   Class<?> clazz = Class.forName(beanDef.getClassName());
			   final Object object=clazz.newInstance();
			   beanDef.getProperties().stream().forEach(beanProp ->{
				   try {
						 Field field=clazz.getDeclaredField(beanProp.getName());
						 field.setAccessible(true);
						 Object value=null;
						 if(beanProp.isPrimitive()){ 
							 value=getPrimitive(field, beanProp.getValue());
						  } else{
							  String refBeanId=beanProp.getRef();
						      value=this.getBean(refBeanId);
						  }
						 field.set(object, value);
						} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
			   });
			   
/*			   if(beanDef.isHasProxy()) {
				   beanDef.setProxy(getProxyObject(beanDef,object,this));
			   }
*/			   
			   return object;
		   } catch (Exception e) {
			  e.printStackTrace();
		   }
		   return null;
			}
	
	
	
	
	private static Object getPrimitive(Field field, String properyValue) {
		try {
			Object value = null;
			String type = field.getType().getName();
			switch (type) {
			case INT_TYPE:
				value = new Integer(properyValue);
				break;
			case LONG_TYPE:
				value = new Long(properyValue);
				break;
			case BYTE_TYPE:
				value = new Byte(properyValue);
				break;
			case BOOLEAN_TYPE:
				value = new Boolean(properyValue);
				break;
			case FLOAT_TYPE:
				value = new Float(properyValue);
				break;
			case DOUBLE_TYPE:
				value = new Double(properyValue);
				break;
			case SHORT_TYPE:
				value = new Short(properyValue);
				break;
			case CHAR_TYPE:
				value = new Character(properyValue.charAt(0));
				break;
			default:
				value = properyValue;
				break;
			}
			return value;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Returns proxy of given object based on provided below information.
	 * 
	 * @param targetObj
	 *            : Actual Object for which proxy is created.
	 * @param clazz
	 *            : Actual Object implemented interface.
	 * @param aspect
	 *            : AOP aspect, method of these classes are called as per advices
	 *            configuration.
	 * @return
	 */
	public static Object getProxyObject(BeanDefinition beanDefinition,Object targetObj,AppContext iocContainer ) {
		 try {
			Class<?> objInterface=beanDefinition.getObjInterface();
			Object proxy =Proxy.newProxyInstance(objInterface.getClassLoader(), new Class[] { objInterface },
					new ProxyInvocationHandler(targetObj, iocContainer));
			return proxy;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		 return null;
	}
	
	 public void injectContainer(Object obj) {
	    	if(obj instanceof ContainerAware) {
	    		((ContainerAware) obj).setContainer(this);
	    		}
	    	}
	public boolean isValidPath(String path){
		return handlersConfigs.keySet().contains(path);
	}
	
	public HandlerConfig getHandlerConfig(String path){
		return handlersConfigs.get(path);
	}
	
	@Override
	public boolean destroy() {
		this.beanConfigs=null;
		return true;
	}
	
	public int beansSize() {
		return 0;
	}

	public boolean isBeanExisted(String beanId) {
		return false;
	}

	public HashMap<String, BeanDefinition> getBeanContainer() {
		return beanConfigs;
	}

	public void setBeanContainer(HashMap<String, BeanDefinition> beanContainer) {
		this.beanConfigs = beanContainer;
	}

	public HashMap<String, HandlerConfig> getHandlers() {
		return handlersConfigs;
	}

	public void setHandlers(HashMap<String, HandlerConfig> handlers) {
		this.handlersConfigs = handlers;
	}
	
}

class ProxyInvocationHandler implements InvocationHandler {
	Object tarObj = null;
	AppContext iocContainer = null;
	public ProxyInvocationHandler(Object tarObj,AppContext iocContainer) {
		this.tarObj = tarObj;
		this.iocContainer=iocContainer;
	}

	/**
	 * Intercepting logic will go here
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object returnObject = null;
		Method objMethod = null;
		for (Method m : tarObj.getClass().getMethods()) {
			if (m.getName().equals(method.getName())) {
				objMethod = m;
				break;
			}
		}
		AopAdviceType adviceType = null;
		String beforeAdvice = null;
		String afterAdvice = null;
		String aspectObjId=null;
		
		if(objMethod.isAnnotationPresent(Around.class)) {
			Around around = objMethod.getDeclaredAnnotation(Around.class);
			adviceType = AopAdviceType.AROUND;
			beforeAdvice = around.aspectAfterMethod();
			afterAdvice = around.aspectBeforeMethod();
			aspectObjId=around.aspectObj();
		}else if(objMethod.isAnnotationPresent(Before.class)) {
			Before before = objMethod.getDeclaredAnnotation(Before.class);
			adviceType = AopAdviceType.BEFORE;
			beforeAdvice = before.aspectMethod();
			aspectObjId=before.aspectObj();
		}else if(objMethod.isAnnotationPresent(After.class)) {
			After after = objMethod.getDeclaredAnnotation(After.class);
			adviceType = AopAdviceType.AFTER;
			beforeAdvice = after.aspectMethod();
			aspectObjId=after.aspectObj();
		}
	    Object aspecObj=iocContainer.getBean(aspectObjId);
		Method aspectMethod = null;
		if (beforeAdvice != null) {
			aspectMethod = aspecObj.getClass().getDeclaredMethod(beforeAdvice, new Class[] {});
		}
		switch (adviceType) {
		case BEFORE:
			aspectMethod.invoke(aspecObj, new Object[] {});
			returnObject = method.invoke(tarObj, args);
			break;
		case AFTER:
			returnObject = method.invoke(tarObj, args);
			aspectMethod.invoke(aspecObj, new Object[] {});
			break;
		case AROUND:
			aspectMethod.invoke(aspecObj, new Object[] {});
			returnObject = method.invoke(tarObj, args);
			Method aspectMethod2 = aspecObj.getClass().getDeclaredMethod(afterAdvice, new Class[] {});
			aspectMethod2.invoke(aspecObj, new Object[] {});
			break;

		default:
			returnObject = method.invoke(tarObj, args);
			break;
		}
		return returnObject;
	}

}
