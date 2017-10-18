package org.vgr.app.dao;

import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.vgr.app.common.AbstractTest;
import org.vgr.app.domain.Profile;
import org.junit.Test;

public class LoginDaoTest extends AbstractTest{
	
    @Test
	public void testAuthenticate(){
		 LoginDao  loginDao=(LoginDao) factory.getBean("loginDao");
		 Profile profile=loginDao.authenticate("venugopal@venu.org", "venugopal");
         assertNotNull(profile);
     }

	@Override
	public Set<String> getClasses() {
		// TODO Auto-generated method stub
		return null;
	}
}
