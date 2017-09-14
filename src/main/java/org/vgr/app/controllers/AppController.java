package org.vgr.app.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.app.domain.Profile;
import org.vgr.app.service.LoginService;
import org.vgr.http.server.Cookie;
import org.vgr.http.server.HttpRequest;
import org.vgr.http.server.HttpResponse;
import org.vgr.http.server.HttpSession;
import org.vgr.ioc.annot.Controller;
import org.vgr.ioc.annot.Handler;
import org.vgr.ioc.annot.Inject;

@Controller(id="appController")
public class AppController {
	private static final Logger LOG=LoggerFactory.getLogger(AppController.class);
	
 	@Inject(ref="loginService")
    private LoginService loginService=null;
   // AppCacheManager cacheManager=null;
	
	/**
	 * This mehtod returns the Home Page Of User
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 */

 @Handler(path="/home.htm")
 public String indexPage(HttpRequest servletRequest,HttpResponse servletResponse) {
	 return "home";
 }
 
 @Handler(path="/login.htm")
		public String authenticate(HttpRequest servletRequest,HttpResponse servletResponse) {
		try {
		      String userEmail=servletRequest.getParameter("uname");
		      String passWord=servletRequest.getParameter("pwd");
		      StringBuilder jsonString=new StringBuilder("{\"token\":\"");
		     // Profile loginUser =loginService.authenticateUser(userEmail ,passWord);
		     // cacheManager.put("loginUser", loginUser);
		      Profile loginUser=new Profile();
		      loginUser.setUserId(2);
		      loginUser.setFirstName("venugopal");
		      loginUser.setLastName("Reddy");
		      loginUser.setEmail("venugopal@venu.org");
		      if(loginUser!=null && loginUser.getUserId()!=0){
		    	  jsonString.append("true");
		    	  
		    	  HttpSession session=  servletRequest.getSession(true);
		    	  session.setAttribute("loginUser", loginUser);
		    	  session.setAttribute("valid", true);
		    	  
		    	  LOG.debug("Session ID : "+session.getId());
		    	  Cookie firstName=new Cookie("fname", loginUser.getFirstName());
		    	  Cookie lastName=new Cookie("lname", loginUser.getLastName());
		    	  Cookie email=new Cookie("email", loginUser.getEmail());
		    	  servletResponse.addCookie(firstName);
		    	  servletResponse.addCookie(lastName);
		    	  servletResponse.addCookie(email);
		        }
		      else{
		    	  jsonString.append("false");
		      }
		      jsonString.append("\"}");
	    	  servletRequest.setAttribute("jsonString", jsonString);
		      
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
