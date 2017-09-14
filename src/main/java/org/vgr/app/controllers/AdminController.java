package org.vgr.app.controllers;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.app.domain.Profile;
import org.vgr.app.service.AdminService;
import org.vgr.http.server.HttpRequest;
import org.vgr.http.server.HttpResponse;
import org.vgr.ioc.annot.Controller;
import org.vgr.ioc.annot.Handler;
import org.vgr.ioc.annot.Inject;
/**
 * @author Vengopal
 */
@Controller(id="adminController")
public class AdminController {
	private static final Logger LOG=LoggerFactory.getLogger(AdminController.class);
	
	@Inject(ref="adminService")
	private AdminService adminService;
	public String getUserInfo(HttpRequest request,HttpResponse response) {
		String uid=request.getParameter("uid");
		int userId=Integer.parseInt(uid);
		Profile profile=adminService.getUserInfo(userId);
		request.setAttribute("profile", profile);
		return "userinfo";
	}
	
	@Handler(path="/admin.htm")
	public String adminPage(HttpRequest request,HttpResponse response){
		try {
			List<Profile> users=adminService.getAllUsers();
			LOG.info("users count : "+users.size());
			System.out.println();
			request.setAttribute("users", users);
			} catch (SQLException e) {
				return "error";
			}
			return "admin";
	}
	public AdminService getAdminService() {
		return adminService;
	}
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
}
