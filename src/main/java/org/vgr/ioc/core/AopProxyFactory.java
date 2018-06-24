package org.vgr.ioc.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.vgr.ioc.annot.After;
import org.vgr.ioc.annot.Around;
import org.vgr.ioc.annot.Before;

/**
 *  AOP is done by Proxy Design pattern.
 * @author venugopal
 *
 */
public class AopProxyFactory  {
	
	 /**
	  *  Returns proxy of given object based on provided below information.
	  *  
	  * @param tarObj :  Actual Object for which proxy is created.
	  * @param clazz  :  Actual Object implemented interface.
	  * @param aspect :  AOP aspect, method of these classes are called as per advices configuration.
	  * @return
	  */
	 public static Object getProxy(Object tarObj,Class<?> clazz,Object aspect){
			 Object proxy=Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, new ProxyInvocationHandler(tarObj,aspect)); 
			 return proxy;
	 }
}

/**
 *  Each target Object calls goes this class objects.
 * @author vyeredla
 *
 */
class ProxyInvocationHandler  implements InvocationHandler {
 Object tarObj=null;
 Object aspect=null;
 public ProxyInvocationHandler(Object tarObj,Object aspect){
       this.tarObj=tarObj;
       this.aspect=aspect;
 }
	/**
	 * Intercepting logic will go here
	 */
   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	    Object returnObject=null;
	    Method objMethod=null;
	    for (Method m : tarObj.getClass().getMethods()) {
			if(m.getName().equals(method.getName())) {
				objMethod=m;
				break;
			}
		}
	    AopAdviceType adviceType=null;
	    String adviceMethod=null;
	    String adviceMethod2=null;

	    Before before= objMethod.getDeclaredAnnotation(Before.class);
	    After after= objMethod.getDeclaredAnnotation(After.class);
	    Around around= objMethod.getDeclaredAnnotation(Around.class);
        
        if(before!=null) {
        	adviceType=AopAdviceType.BEFORE;
        	adviceMethod=before.method();
        }else if(after!=null) {
             	adviceType=AopAdviceType.AFTER;
             	adviceMethod=after.method();
        }else if(around!=null){
        	adviceType=AopAdviceType.AROUND;
         	adviceMethod=around.aMethod();
         	adviceMethod2=around.aMethod();
        }
        Method aspectMethod=null;
        if(adviceMethod!=null) {
        	aspectMethod= aspect.getClass().getDeclaredMethod(adviceMethod, new Class[]{}) ;
        }
	 switch (adviceType) {
	   case BEFORE:
		   aspectMethod.invoke(aspect, new Object[]{});
   	       returnObject= method.invoke(tarObj, args);
		break;
	case AFTER:
		 returnObject= method.invoke(tarObj, args);
		 aspectMethod.invoke(aspect, new Object[]{});
		break;
	case AROUND:
		aspectMethod.invoke(aspect, new Object[]{});
		returnObject= method.invoke(tarObj, args);
		Method aspectMethod2= aspect.getClass().getDeclaredMethod(adviceMethod2, new Class[]{}) ;
		aspectMethod2.invoke(aspect, new Object[]{});
		break;

	default:
		returnObject= method.invoke(tarObj, args);
		break;
	}
	return returnObject;
}
   
}