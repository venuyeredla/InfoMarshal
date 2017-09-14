package org.vgr.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.vgr.app.domain.Profile;
import org.vgr.ioc.annot.Dao;
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.jdbc.JDBCTemplate;
import org.vgr.ioc.jdbc.RowMapper;
import org.vgr.ioc.jdbc.RowsMpper;

@Dao(id="adminDao")
public class AdminDao {
	@Inject(ref="jdbcTemplate")
	JDBCTemplate jdbcTemplate=null;
	
	public List<Profile> getAllUsers() throws SQLException {
	String sql=" select * from profile";
	@SuppressWarnings("unchecked")
	List<Profile> userList=(List<Profile>)jdbcTemplate.queryForObjList(sql, new RowsMpper<Profile>() {
			public List<Profile> mapRowsToObjList(ResultSet resultSet)	throws SQLException {
				List<Profile> users = new ArrayList<Profile>();
				while (resultSet.next()) {
					Profile profile=new Profile();
					profile.setUserId(resultSet.getInt("usrid"));
					profile.setHomeurl(resultSet.getString("homeurl"));
					profile.setFirstName(resultSet.getString("f_name"));
					profile.setLastName(resultSet.getString("l_name"));
					profile.setEmail(resultSet.getString("email"));
					users.add(profile);
				}
				return users;
			}
		});
	
		return userList;
	}
  /** Getting user info by userid 
   * 
   * @param userId
   * @return
   */
	public Profile getUserInfoById(int userId){
	        
	    	String sql="SELECT * FROM profile INNER JOIN profile_detail ON profile.usrid =PROFILEDETAIL.PROFILEID where PROFILE.usrid="+userId;
	    	 Profile profile=(Profile)jdbcTemplate.queryForObj(sql, new RowMapper<Profile>() {
				@Override
				public Profile mapRowToObj(ResultSet resultSet) throws SQLException {
					Profile login=new Profile();
					while(resultSet.next()){
						login.setUserId(resultSet.getInt("usrid"));
						login.setHomeurl(resultSet.getString("homeurl"));					
						login.setPassword(resultSet.getString("password"));
						login.setFirstName(resultSet.getString("f_name"));
						login.setLastName(resultSet.getString("l_name"));
						login.setEmail(resultSet.getString("email"));
						login.setPhoneNbr(resultSet.getLong("phone"));
						login.setAddress(resultSet.getString("address"));
						login.setDob(resultSet.getDate("dob"));
						login.setGenderNbr(resultSet.getInt("gender_id"));
						login.setReligionId(resultSet.getInt("religion_id"));
						login.setReligionId(resultSet.getInt("marital_id"));
					}
					return login;
				}
			});
    	 return profile;
	}
	
	public JDBCTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JDBCTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}

