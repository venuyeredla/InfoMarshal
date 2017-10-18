package org.vgr.app.dao;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.vgr.app.common.AbstractTest;
import org.vgr.app.service.FriendsService;
import org.vgr.app.service.ProfileService;
import org.vgr.ioc.core.BeanFactory;
import org.junit.Ignore;
import org.junit.Test;

public class ProfileDaoTest  extends AbstractTest{
	 static BeanFactory factory=null;
		
		
	 @Test
	 @Ignore
	  public void testGetfriends() {
	    FriendsService friendsService=(FriendsService)factory.getBean("friendsService");
       int size=friendsService.getUserFriends(2).size();
       assertEquals(1, size);
	      }

	 
	 @Test
	 @Ignore
	    public void isValidMail() {
		 ProfileService profileService =(ProfileService)factory.getBean("profileService");
         int count=profileService.isValidMail("venugopal@venu.org");
          assertEquals(1, count);
	    }


	@Override
	public Set<String> getClasses() {
		// TODO Auto-generated method stub
		return null;
	}
}
