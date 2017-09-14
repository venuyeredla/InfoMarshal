package org.vgr.app.service;

import java.sql.SQLException;
import java.util.List;

import org.vgr.app.dao.AdminDao;
import org.vgr.app.domain.Profile;
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.annot.Service;

@Service(id="adminService")
public class AdminService {
	
	@Inject(ref="adminDao")
	private AdminDao adminDao;
	
	public List<Profile> getAllUsers() throws SQLException {
		return adminDao.getAllUsers();
	}
	
	public Profile getUserInfo(int userId) {
		return adminDao.getUserInfoById(userId);
	}

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	
}
