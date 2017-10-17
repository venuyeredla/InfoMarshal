package org.vgr.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.app.domain.Profile;
import org.vgr.app.service.LoginService;
import org.vgr.http.server.HttpRequest;
import org.vgr.http.server.HttpResponse;
import org.vgr.http.server.HttpSession;
import org.vgr.http.server.MimeType;
import org.vgr.ioc.annot.Controller;
import org.vgr.ioc.annot.Handler;
import org.vgr.ioc.annot.Inject;

@Controller(id="appController")
public class AppController {
	private static final Logger LOG=LoggerFactory.getLogger(AppController.class);
	
 	@Inject(ref="loginService")
    private LoginService loginService=null;

	@Handler(path="/home.htm")
    public String indexPage(HttpRequest servletRequest,HttpResponse servletResponse) {
		 return "home";
	 }
	 @Handler(path="/welcome.htm")
	 public String welcome(HttpRequest servletRequest,HttpResponse servletResponse) {
		 return "welcome";
	 }
	 @Handler(path="/login.htm",mimeType=MimeType.JSON)
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
	    
	    @Handler(path="/user.htm")
	    public String homePage(HttpRequest servletRequest,HttpResponse servletResponse) {
	   		try {
	   			  HttpSession sess=servletRequest.getSession(false);
	   	          Profile u=(Profile)sess.getAttribute("loginUser");
	   	          Profile xyz=u; //(Profile)cacheManager.getValue("loginUser");
	   	          if(xyz!=null){
	   	        	LOG.info("From cache name is :"+xyz.getFirstName());
	   	          }
	   			} catch (Exception e) {
	   			e.printStackTrace();
	       		}
	   		return "user";
	   	}

	 @Handler(path="/logout.htm")
		public String logout(HttpRequest servletRequest,HttpResponse servletResponse){
		     	  HttpSession sess=servletRequest.getSession(false);
		     	  sess.getAttribute("loginUser");
		     	  sess.invalidate();
    	 return "home";
		}
		
		public LoginService getLoginService() {
			return loginService;
		}

		public void setLoginService(LoginService loginService) {
			this.loginService = loginService;
		}

	/*	public AppCacheManager getCacheManager() {
			return cacheManager;
		}

		public void setCacheManager(AppCacheManager cacheManager) {
			this.cacheManager = cacheManager;
		}
*/
}
