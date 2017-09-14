package org.vgr.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.vgr.app.domain.Friend;
import org.vgr.app.domain.Profile;
import org.vgr.ioc.annot.Dao;
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.jdbc.JDBCTemplate;
import org.vgr.ioc.jdbc.RowsMpper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dao(id="friendsDao")
public class FriendsDao {
	@Inject(ref="jdbcTemplate")
	JDBCTemplate jdbcTemplate=null;
	public static final Logger logger=LoggerFactory.getLogger(FriendsDao.class);
		
	public List<Profile> getFriends(int userId) {
		String sql = "SELECT profile.* FROM profile INNER JOIN friends  ON profile.usrid=friends.friendid and friendid=2";
		@SuppressWarnings("unchecked")
		List<Profile> friends =(List<Profile>) jdbcTemplate.queryForObjList(sql, new RowsMpper<Profile>() {
			public List<Profile> mapRowsToObjList(ResultSet resultSet) throws SQLException {
					List<Profile> friends=new ArrayList<Profile>();
					while(resultSet.next()) {
						Profile friend=new Profile();
						friend.setUserId(resultSet.getInt("usrid"));
						friend.setHomeurl(resultSet.getString("homeurl"));					
						friend.setPassword(resultSet.getString("password"));
						friend.setFirstName(resultSet.getString("f_name"));
						friend.setLastName(resultSet.getString("l_name"));
						friend.setEmail(resultSet.getString("email"));/*
						friend.setPhoneNbr(resultSet.getLong("phone"));
						friend.setAddress(resultSet.getString("address"));
						friend.setDob(resultSet.getDate("dob"));
						friend.setGenderNbr(resultSet.getInt("gender_id"));
						friend.setReligionId(resultSet.getInt("religion_id"));
						friend.setReligionId(resultSet.getInt("marital_id"));*/
						friend.setImg(resultSet.getString("img"));
						
						friends.add(friend);
					}
				return friends;
			} 
			
		});
		
		return friends;
		
	}
	
	public List<Friend> getPeople(int userId)
	{
		List<Friend> people=new ArrayList<Friend>();
		return people;
	}
	
	public List<Friend> getOnlineFriends(int id) {
		return null;
	}
	
	public Friend makeFriend(int id) {
		return null;
	}
	
	public List<Friend> getSuggestFriends() {
		return null;
	}
	
	public JDBCTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JDBCTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
