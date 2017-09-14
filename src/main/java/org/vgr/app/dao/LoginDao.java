package org.vgr.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.vgr.app.domain.Profile;
import org.vgr.ioc.annot.Dao;
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.jdbc.JDBCTemplate;
import org.vgr.ioc.jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dao(id="loginDao")
public class LoginDao {
	public static final Logger logger=LoggerFactory.getLogger(LoginDao.class);
	
	@Inject(ref="jdbcTemplate")
	JDBCTemplate jdbcTemplate=null;
	 
    public Profile authenticate(String mail, String password){
	        
	 String sql="  SELECT * FROM profile INNER JOIN profile_detail ON profile.profileid =profile_detail.profileid where profile.email='"+mail+"' and profile.pass='"+password+"'";
	    	 Profile profile=(Profile)jdbcTemplate.queryForObj(sql, new RowMapper<Profile>() {
				public Profile mapRowToObj(ResultSet resultSet) throws SQLException {
					Profile login=null;
					while(resultSet.next()){
						login=new Profile();
						login.setUserId(resultSet.getInt("profileid"));
						login.setHomeurl(resultSet.getString("homeurl"));					
						login.setPassword(resultSet.getString("pass"));
						login.setFirstName(resultSet.getString("f_name"));
						login.setLastName(resultSet.getString("l_name"));
						login.setEmail(resultSet.getString("email"));
						login.setPhoneNbr(resultSet.getLong("phone"));
						login.setAddress(resultSet.getString("address"));
						login.setDob(resultSet.getDate("dob"));
						login.setGenderNbr(resultSet.getInt("gender_id"));
						login.setReligionId(resultSet.getInt("religion_id"));
						login.setReligionId(resultSet.getInt("marital_id"));
						login.setImg(resultSet.getString("img"));
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
