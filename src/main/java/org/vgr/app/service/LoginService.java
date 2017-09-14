package org.vgr.app.service;

import org.vgr.app.dao.LoginDao;
import org.vgr.app.domain.Profile;
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.annot.Service;

@Service(id="loginService")
public class LoginService {

	@Inject(ref="loginDao")
	private LoginDao loginDao=null;

	public Profile authenticateUser(String email, String password){
	      Profile loginUser=loginDao.authenticate(email ,password);
	      if(loginUser!=null){
	    	  loginUser.setImg("static/"+loginUser.getUserId()+"/p_"+loginUser.getImg());  
	      }
		  return loginUser;
	}
    
	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
		

	
	
	
}
