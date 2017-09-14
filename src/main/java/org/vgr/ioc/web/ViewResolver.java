package org.vgr.ioc.web;

public class ViewResolver {
	
	private String prefix="/views/";
	private String suffix=".jsp";
	
	public String getViewPath(String viewName){
		 String path=this.prefix+viewName+this.suffix;
		return path;
	}
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
