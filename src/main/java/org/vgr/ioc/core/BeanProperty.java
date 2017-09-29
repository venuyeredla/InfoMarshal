package org.vgr.ioc.core;

public class BeanProperty {

	private String name;
	private String value;
	private String ref;
	private boolean primitive=false;
	
	public BeanProperty(){}
	
	public BeanProperty(String name,String ref){
		this.name=name;
		this.ref=ref;
		this.primitive=false;
	}
	
	public BeanProperty(String name,String value,boolean primitive){
		this.name=name;
		this.value=value;
		this.primitive=primitive;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public boolean isPrimitive() {
		return primitive;
	}

	public void setPrimitive(boolean primitive) {
		this.primitive = primitive;
	}

	@Override
	public String toString() {
		return "BeanProperty [name=" + name + ", value=" + value + ", ref="
				+ ref + ", primitive=" + primitive + "]";
	}

	
	
}
