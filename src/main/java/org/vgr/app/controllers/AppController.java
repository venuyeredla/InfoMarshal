package org.vgr.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.http.server.HttpRequest;
import org.vgr.http.server.HttpResponse;
import org.vgr.http.server.HttpSession;
import org.vgr.http.server.MimeType;
import org.vgr.ioc.annot.Controller;
import org.vgr.ioc.annot.Handler;

@Controller(/*"appController"*/)
public class AppController {
	private static final Logger LOG=LoggerFactory.getLogger(AppController.class);
	
	@Handler("/")
    public String home(HttpRequest servletRequest,HttpResponse servletResponse) {
		 return "home";
	 }
	
	@Handler("/home.htm")
    public String indexPage(HttpRequest servletRequest,HttpResponse servletResponse) {
		 return "home";
	 }
	 @Handler("/welcome.htm")
	 public String welcome(HttpRequest servletRequest,HttpResponse servletResponse) {
		 return "welcome";
	 }
	 @Handler(value="/login.htm",mimeType=MimeType.JSON)
	 public String authenticate(HttpRequest req,HttpResponse res) {
		try {
		      String user=req.getParameter("name");
		      String pass=req.getParameter("pwd");
		      Map<String,String> data=new HashMap<String,String>(); 
		      if(user.equals("venu")&&pass.equals("gopal")) {
		    	  data.put("sucess", "true");
		    	  data.put("user", "true");
		    	  data.put("password", "true");
		      }else {
		    	  data.put("sucess", "false");  
		      }
		      res.setData(data);
			 }catch (Exception e) {
				e.printStackTrace();
			}
			return "json";
		}
	    
	   @Handler("/logout.htm")
		public String logout(HttpRequest servletRequest,HttpResponse servletResponse){
		     	  HttpSession sess=servletRequest.getSession(false);
		     	  sess.getAttribute("loginUser");
		     	  sess.invalidate();
    	 return "home";
		}
		
}
