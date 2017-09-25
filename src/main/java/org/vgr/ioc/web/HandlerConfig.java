package org.vgr.ioc.web;

import org.vgr.http.server.MimeType;

public class HandlerConfig {
   private String path;
   private String controller;
   private String method;
   private MimeType mimeType;

	public HandlerConfig() {}
	public HandlerConfig(String path,String controller,String method,MimeType mimeType) {
		this.path=path;
		this.controller=controller;
		this.method=method;
		this.mimeType=mimeType;
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

	public MimeType getMimeType() {
		return mimeType;
	}
	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
	}
	@Override
	public String toString() {
		return "HandlerConfig [path=" + path + ", controller=" + controller
				+ ", method=" + method + "]";
	}
		
}
