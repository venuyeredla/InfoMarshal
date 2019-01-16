package org.vgr.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.http.server.HttpRequest;
import org.vgr.http.server.HttpResponse;
import org.vgr.ioc.annot.Controller;
import org.vgr.ioc.annot.Handler;

@Controller("forwardController")
public class ForwardController {
	private static final Logger LOG=LoggerFactory.getLogger(ForwardController.class);
	 @Handler("/error.htm")
	 public String errorPage(HttpRequest servletRequest,HttpResponse servletResponse) {
     		 return "error";
	 }
	 @Handler("/js.htm")
	 public String loginPage(HttpRequest servletRequest,HttpResponse servletResponse) {
		 return "js";
	 }
}
