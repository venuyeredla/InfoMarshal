package org.vgr.ioc.core;

import java.util.List;

/**
 * This class bean is  useful for holding bean defintion from Xml files 
 * @author venugopal
 *
 */
public class BeanDefinition {
	private String id;
	private String className;
	private String scope;
	private Object object;
	private Object proxy;
	private List<BeanProperty> properties=null;
	
	public BeanDefinition(){}

	public BeanDefinition(String id,String className,String scope){
		this.id=id;
		this.className=className;
		this.scope=scope;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public List<BeanProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<BeanProperty> properties) {
		this.properties = properties;
	}

	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public Object getProxy() {
		return proxy;
	}
	public void setProxy(Object proxy) {
		this.proxy = proxy;
	}

	@Override
	public String toString() {
		return "BeanDefinition [id=" + id + ", className=" + className
				+ ", scope=" + scope + ", object=" + object + ", proxy="
				+ proxy + ", properties=" + properties + "]";
	}
}
