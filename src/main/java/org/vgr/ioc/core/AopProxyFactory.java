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
public class AopProxyFactory  {
	 public static Object getProxy(Object tarObj,Class<?> clazz,AopAdvices aspect){
			 Object proxy=Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, new ProxyInvocationHandler(tarObj,aspect)); 
			 return proxy;
	 }
}

class ProxyInvocationHandler  implements InvocationHandler {
 Object tarObj=null;
 AopAdvices aspect=null;
 public ProxyInvocationHandler(Object tarObj,AopAdvices aspect){
       this.tarObj=tarObj;
       this.aspect=aspect;
 }
	/**
	 * Intercepting logic will go here
	 */
   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	   Object returnObject=null;
	   HashMap<String, AopAdviceType> methodAdvice=aspect.pointCuts();
	   String excutionMethod=method.getDeclaringClass().getName()+"."+method.getName();
	   AopAdviceType adviceType=methodAdvice.get(excutionMethod);
	   
	 switch (adviceType) {
	   case BEFORE:
		/* Method beforeMethod= aspect.getClass().getDeclaredMethod("before", new Class[]{});
   		 beforeMethod.invoke(aspect, new Object[]{});*/
   		 aspect.before();
   	     returnObject= method.invoke(tarObj, args);
		break;
	case AFTER:
		 returnObject= method.invoke(tarObj, args);
		 aspect.after();
		/* Method after= aspect.getClass().getDeclaredMethod("after", new Class[]{});
   		 after.invoke(aspect, new Object[]{});*/
		break;
	case AROUND:
		 Method before= aspect.getClass().getDeclaredMethod("before", new Class[]{});
   		 before.invoke(aspect, new Object[]{});
   		 returnObject= method.invoke(tarObj, args);
		 Method after= aspect.getClass().getDeclaredMethod("after", new Class[]{});
   		 after.invoke(aspect, new Object[]{});
		break;

	default:
		returnObject= method.invoke(tarObj, args);
		break;
	}
	return returnObject;
}
}