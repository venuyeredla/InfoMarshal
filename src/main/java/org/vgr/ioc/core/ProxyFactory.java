package org.vgr.ioc.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
/**
 *  Implementation of proxy Desing patterns
 * @author venugopal
 *
 */
public class ProxyFactory  {
	 private Object targetObject;
	 private String interfaceName;
	 private Object proxy;
	 private Object aspect;        //Aspect Object
	 public Object getProxy(){
		 Class<?> clazz=null;
		 try {
			 clazz=Class.forName(interfaceName);
			 proxy=Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, new ProxyInvocationHandler(targetObject)); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	       
		 return proxy;
	 }
	  
	private class ProxyInvocationHandler  implements InvocationHandler {
		 @SuppressWarnings("unused")
		Object obj=null;
		 public ProxyInvocationHandler(Object object){
		       this.obj=object;
		 }
			/**
			 * Intercepting logic will go here
			 */
		   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			   Object returnObject=null;
			 
			   Method pointcutsMethod=aspect.getClass().getDeclaredMethod("pointCuts", new Class[]{});
			 		   @SuppressWarnings("unchecked")
			   HashMap<String, String> methodAdvice=(HashMap<String,String>) pointcutsMethod.invoke(aspect, new Object[]{});
			   
			   String excutionMethod=method.getDeclaringClass().getName()+"."+method.getName();
			   String adviceType=methodAdvice.get(excutionMethod);
			   if(aspect!=null && adviceType!=null)
			   {
				 if(adviceType.equals("before"))			   
				 {
					 Method before= aspect.getClass().getDeclaredMethod("before", new Class[]{});
			   		 before.invoke(aspect, new Object[]{});
			   	     returnObject= method.invoke(targetObject, args);
				 }
				 else if(adviceType.equals("after")){
					 returnObject= method.invoke(targetObject, args);
					 Method after= aspect.getClass().getDeclaredMethod("after", new Class[]{});
			   		 after.invoke(aspect, new Object[]{});
					 
				 }
				 else if(adviceType.equals("around")){
					 Method before= aspect.getClass().getDeclaredMethod("before", new Class[]{});
			   		 before.invoke(aspect, new Object[]{});
			   		 returnObject= method.invoke(targetObject, args);
					 Method after= aspect.getClass().getDeclaredMethod("after", new Class[]{});
			   		 after.invoke(aspect, new Object[]{});
					 }
			   }
			   else{
			   		   returnObject= method.invoke(targetObject, args);
			   }
			return returnObject;
		}
	 }
	 
	 
	public Object getTargetObject() {
		return targetObject;
	}


	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}


	public String getInterfaceName() {
		return interfaceName;
	}


	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}


	public void setProxy(Object proxy) {
		this.proxy = proxy;
	}

	public Object getAspect() {
		return aspect;
	}

	public void setAspect(Object aspect) {
		this.aspect = aspect;
	}

		   
}
