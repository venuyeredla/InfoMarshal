package org.vgr.app.service;

import java.util.List;

import org.vgr.app.dao.FriendsDao;
import org.vgr.app.domain.Friend;
import org.vgr.app.domain.Profile;
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.annot.Service;

@Service(id="friendsService")
public class FriendsService {

	@Inject(ref="friendsDao")
	private FriendsDao friendsDao = null;

	/**
	 * This method gives the List of user friends
	 * @param userId
	 * @return
	 */
	public List<Profile> getUserFriends(int userId) {

		return this.friendsDao.getFriends(userId);
	}
	

	public List<Friend> getPeople(int userId) {

		return this.friendsDao.getPeople(userId);
	}

	public List<Friend> getOnlineFriends(int id) {
		return null;
	}
	
	public Friend makeFriend(int id) {
		return null;
	}

	public List<Friend>  getSuggestFriends() {
		
		return null;
	}


	public FriendsDao getFriendsDao() {
		return friendsDao;
	}


	public void setFriendsDao(FriendsDao friendsDao) {
		this.friendsDao = friendsDao;
	}
	
	
}
