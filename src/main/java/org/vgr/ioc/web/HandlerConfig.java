package org.vgr.ioc.web;

public class HandlerConfig {
	   private String path;
	   private String controller;
	   private String method;

	public HandlerConfig() {}
	
	public HandlerConfig(String path,String controller,String method) {
		this.path=path;
		this.controller=controller;
		this.method=method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "HandlerConfig [path=" + path + ", controller=" + controller
				+ ", method=" + method + "]";
	}
		
}
