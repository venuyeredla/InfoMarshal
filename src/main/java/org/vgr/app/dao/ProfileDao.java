package org.vgr.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vgr.app.domain.Image;
import org.vgr.app.domain.Profile;
import org.vgr.ioc.annot.Dao;
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.jdbc.JDBCTemplate;
import org.vgr.ioc.jdbc.RowsMpper;

@Dao(id="profileDao")
public class ProfileDao {
	
	@Inject(ref="jdbcTemplate")
	private JDBCTemplate  jdbcTemplate=null;
	
	public int saveProfile(Profile profile) {
		String sql="insert into profile(homeurl,pass,f_name,l_name,email)" +
				"values('"+profile.getHomeurl()+"','"+profile.getPassword()+"','"+profile.getFirstName()+"','"+profile.getLastName()+"','"+profile.getEmail()+"')";
     		int PK=jdbcTemplate.executePK(sql);
			return PK;
	}
	
	public int saveProfileDetails (Profile profile) {
		String sql="insert into profile_detail(profileid,dob,active,gender_id)" +
				"values("+profile.getUserId()+",'"+profile.getDob()+"','"+profile.getActiveInd()+"',"+profile.getGenderNbr()+")";
				 
		int num=jdbcTemplate.executeUpdate(sql);
		
		return num;
	}
	
	public List<Image>  getImageList(int userid){
		
	String sql ="select * from images where userid="+userid;
		
	 @SuppressWarnings("unchecked")
		List<Image> images=(List<Image>)jdbcTemplate.queryForObjList(sql, new RowsMpper<Image>() {   // Anonymous innner class implemetation
	    		public List<Image> mapRowsToObjList(ResultSet resultSet) throws SQLException {
	    			List<Image> images=new ArrayList<Image>();
	    			while(resultSet.next()){
	    				Image img=new Image();
	    				img.setNbr(resultSet.getInt("imgid"));
	    				img.setHeader(resultSet.getString("header"));
	    				img.setDesc(resultSet.getString("des"));
	    				img.setPath(resultSet.getString("path"));
	    				images.add(img);
	    			}
	    			return images;
	    		}
		});
	 return images;
	}
	
  public int emailCount(String mail){
		 String SQL = "{call emailCount(?, ?)}";
		 Map<Integer, Object> map=new HashMap<Integer, Object>();
	     map.put(1, mail);
		 Map<Integer, Object> outMap=new HashMap<Integer, Object>();
		 outMap.put(2, null);
	     jdbcTemplate.callProcWithIndex(SQL, map, outMap);
		 return   new Integer((String)outMap.get(2));
	}

  public int updateImg(int userId,String img){
    	  String sql="update PROFILE set img='"+img+"' where usrid="+userId;
    	  int num=jdbcTemplate.executeUpdate(sql);
    	  return num;
    }
	
	public JDBCTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JDBCTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
