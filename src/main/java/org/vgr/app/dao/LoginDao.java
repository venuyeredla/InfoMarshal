package org.vgr.app.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.app.domain.Profile;
import org.vgr.ioc.annot.Dao;
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.jdbc.JDBCTemplate;

@Dao(id="loginDao")
public class LoginDao {
	public static final Logger logger=LoggerFactory.getLogger(LoginDao.class);
	
	@Inject(ref="jdbcTemplate")
	JDBCTemplate jdbcTemplate=null;
	 
    public Profile authenticate(String mail, String password){
	        
	 String sql="  SELECT * FROM profile INNER JOIN profile_detail ON profile.profileid =profile_detail.profileid where profile.email='"+mail+"' and profile.pass='"+password+"'";
	 Profile profile=new Profile();;   	
	 jdbcTemplate.queryForObj(sql, resultSet->{
					while(resultSet.next()){
						profile.setUserId(resultSet.getInt("profileid"));
						profile.setHomeurl(resultSet.getString("homeurl"));					
						profile.setPassword(resultSet.getString("pass"));
						profile.setFirstName(resultSet.getString("f_name"));
						profile.setLastName(resultSet.getString("l_name"));
						profile.setEmail(resultSet.getString("email"));
						profile.setPhoneNbr(resultSet.getLong("phone"));
						profile.setAddress(resultSet.getString("address"));
						profile.setDob(resultSet.getDate("dob"));
						profile.setGenderNbr(resultSet.getInt("gender_id"));
						profile.setReligionId(resultSet.getInt("religion_id"));
						profile.setReligionId(resultSet.getInt("marital_id"));
						profile.setImg(resultSet.getString("img"));
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
