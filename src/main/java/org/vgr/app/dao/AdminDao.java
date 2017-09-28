package org.vgr.app.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.vgr.app.domain.Profile;
import org.vgr.ioc.annot.Dao;
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.jdbc.JDBCTemplate;

@Dao(id="adminDao")
public class AdminDao {
	@Inject(ref="jdbcTemplate")
	JDBCTemplate jdbcTemplate=null;
	
	public List<Profile> getAllUsers() throws SQLException {
	List<Profile> users = new ArrayList<Profile>();
	jdbcTemplate.queryForObjList(" select * from profile", (result)->{
				while (result.next()) {
					Profile profile=new Profile();
					profile.setUserId(result.getInt("usrid"));
					profile.setHomeurl(result.getString("homeurl"));
					profile.setFirstName(result.getString("f_name"));
					profile.setLastName(result.getString("l_name"));
					profile.setEmail(result.getString("email"));
					users.add(profile);
				}
			});
		return users;
	}
	
  /** Getting user info by userid 
   * 
   * @param userId
   * @return
   */
	public Profile getUserInfoById(int userId){
	    	String sql="SELECT * FROM profile INNER JOIN profile_detail ON profile.usrid =PROFILEDETAIL.PROFILEID where PROFILE.usrid="+userId;
	    	Profile profile=new Profile();
	    	jdbcTemplate.queryForObj(sql, result ->{
					while(result.next()){
						profile.setUserId(result.getInt("usrid"));
						profile.setHomeurl(result.getString("homeurl"));					
						profile.setPassword(result.getString("password"));
						profile.setFirstName(result.getString("f_name"));
						profile.setLastName(result.getString("l_name"));
						profile.setEmail(result.getString("email"));
						profile.setPhoneNbr(result.getLong("phone"));
						profile.setAddress(result.getString("address"));
						profile.setDob(result.getDate("dob"));
						profile.setGenderNbr(result.getInt("gender_id"));
						profile.setReligionId(result.getInt("religion_id"));
						profile.setReligionId(result.getInt("marital_id"));
					}
					return profile;
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

