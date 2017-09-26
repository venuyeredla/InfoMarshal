package org.vgr.store.rdbms;

import java.util.LinkedHashMap;

import org.junit.Ignore;
import org.junit.Test;
import org.vgr.app.util.RandomUtil;
import org.vgr.store.io.IoUtil;

public class DMLTest {
	
	String idx="/home/venugopal/Documents/Work/io/db/stud.idx";
	String data="/home/venugopal/Documents/Work/io/db/stud.data";

	  @Test
	  @Ignore
	  public void testInsert() {
			int[] keys= new RandomUtil().randomNumbers(1000, 1000000);
			 DML dml=new DML(IoUtil.getDw(idx),IoUtil.getDw(data));
			 dml.intIndex();
			 LinkedHashMap<String,String> map=null;
			for(int i=0;i<keys.length;i++) {
			 map=new LinkedHashMap<String,String>();
			    int key=keys[i];
				map.put("id", ""+key);
				map.put("name", "Std-"+key);
				map.put("sub1", ""+key);
				map.put("sub2", ""+key);
				map.put("sub3", ""+key);
				dml.insert("student", map);
				if(i%100==0) {
					System.out.println(key+",");
				}
				
			}
			dml.writeIndex();
			dml.closeWriters();
			}
			
			 @Test
			  public void testSelect() {
					
				// int[] keys= {36,45,78,32,13,61,94,50,74,47,91,21,84,94,41,54,70,19,61,73};
				     int[] keys= {997563};
					
					LinkedHashMap<String,String> map=null;
					DML dml=new DML(IoUtil.getDr(idx), IoUtil.getDr(data));
					dml.loadIndex();
					for(int i=0;i<keys.length;i++) {
						 int key=keys[i];
						 map=dml.select(key);
						  if(map!=null) {
							 map.forEach((k,v) ->{
								 System.out.print(k+"-"+v+" ,");
							 });
						  }
					}
		  
	  }

}
