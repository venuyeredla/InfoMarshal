package org.vgr.ioc.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Component {
	 String value() default "";
	 BeanScope scope() default BeanScope.SINGLETON;
}
