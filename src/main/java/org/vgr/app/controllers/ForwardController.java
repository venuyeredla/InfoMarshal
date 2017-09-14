package org.vgr.app.controllers;

import org.vgr.http.server.HttpRequest;
import org.vgr.http.server.HttpResponse;
import org.vgr.ioc.annot.Controller;
import org.vgr.ioc.annot.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller(id="forwardController")
public class ForwardController {
	private static final Logger LOG=LoggerFactory.getLogger(ForwardController.class);

	 @Handler(path="/error.htm")
	 public String errorPage(HttpRequest servletRequest,HttpResponse servletResponse) {
     		 return "error";
	 }
	 
	 @Handler(path="/js.htm")
	 public String loginPage(HttpRequest servletRequest,HttpResponse servletResponse) {
		 return "js";
	 }
	
}
