package org.vgr.app.dao;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.vgr.app.common.AbstractTest;
import org.vgr.app.domain.Profile;
import org.vgr.app.service.ProfileService;
import org.vgr.ioc.core.JDBCTemplate;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

public class DaoTestSuite  extends AbstractTest{
	 
	  static JDBCTemplate jdbcTemplate=null;
	
	  @AfterClass
	  public static void afterClass() {
	        // one-time cleanup code
	    	System.out.println("@AfterClass - oneTimeTearDown");
	   }
	  
	  
	@Test
	@Ignore
	public void insertBasicData(){
		
		List<String> queryList= new ArrayList<String>();
		queryList.add("INSERT INTO gender(gender_desc, active) values('Male','Y')");
		queryList.add("INSERT INTO gender(gender_desc, active) values('Female','Y')");
		
		queryList.add("INSERT INTO religion(religion_desc,active) values('Hindu','Y')");
		queryList.add("INSERT INTO religion(religion_desc,active) values('Christian','Y')");
		queryList.add("INSERT INTO religion(religion_desc,active) values('Muslim','Y')");
		queryList.add("INSERT INTO religion(religion_desc,active) values('Sikh','Y')");
		
		queryList.add("INSERT INTO marital(marital_desc ,active) values('Single','Y')");
		queryList.add("INSERT INTO marital(marital_desc ,active) values('Married','Y')");

		int[] xyz=jdbcTemplate.executeBatch(queryList);
		System.out.println(Arrays.toString(xyz));
	}
	
	@Test
	@Ignore
	public void createProfile() throws ParseException{
		ProfileService profileService=(ProfileService)factory.getBean("profileService");
		 Profile profile=new Profile();
	      profile.setFirstName("VenuGopal");
	      profile.setLastName("Reddy");
	      profile.setEmail("venugopal@venu.org");
	      profile.setPassword("venugopal");
	      String year="1987";
	      String month="10";
	      String day="10";
	      String yyyy_mm_dd=year+"-"+month+"-"+day;
	      SimpleDateFormat formatter=new SimpleDateFormat("yyyy-mm-dd");
	      Date dob = new Date(formatter.parse(yyyy_mm_dd).getTime());
	      profile.setDob(dob);
	      profile.setGenderNbr(3);
	      profile.setMaritalStatusId(3);
	      profile.setAboutMe("I am a java developer");
	      profile.setReligionId(4);
	      profileService.createProfile(profile);
	}
	    @Test(expected = ArithmeticException.class)
	    @Ignore
		public void divisionWithException() {  
		  int i = 1/0;
		}  
	    @Test
	    @Ignore
	    public void emailCount(){
	    	  String SQL = "{call emailCount(?, ?)}";
			  Map<Integer, Object> inMap=new HashMap<Integer, Object>();
			  inMap.put(1, "venugopal2@venu.org");
			  Map<Integer, Object> outMap=new HashMap<Integer, Object>();
			  outMap.put(2, null);
		      jdbcTemplate.callProcWithIndex(SQL, inMap, outMap);
		      System.out.println("Count from procedure : "+outMap.get(2));
	    }
	    
	    @Test
	    @Ignore
	    public void emailFunct(){
	    	  String SQL = "{ ? = call emailFunct(?)}";
			  Map<Integer, Object> inMap=new HashMap<Integer, Object>();
			  inMap.put(1, "venugopal@venu.org");
			  Map<Integer, Object> outMap=new HashMap<Integer, Object>();
			  outMap.put(1, null);
		      
		      jdbcTemplate.callProcWithIndex(SQL, inMap, outMap);
		      System.out.println("Count from fnction : "+outMap.get(1));
	    	
	    }


		@Override
		public Set<String> getClasses() {
			// TODO Auto-generated method stub
			return null;
		}
	    
}
