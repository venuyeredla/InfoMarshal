package org.vgr.store.rdbms;

import java.util.LinkedHashMap;

import org.junit.Ignore;
import org.junit.Test;
import org.vgr.app.util.RandomUtil;
import org.vgr.store.io.DataReader;
import org.vgr.store.io.DataWriter;
import org.vgr.store.io.IoUtil;

public class DMLTest {
	String basePath="C:\\Work\\testdata\\io\\";
	String db=basePath+"db\\db.db";
	@Test
	@Ignore
	public void testInsert() {
		int[] keys= new RandomUtil().randomNumbers(50, 100);
		 DML dml=new DML(IoUtil.getDw(db));
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
		dml.closeWriter();
	}
			
	 @Test
	 @Ignore
	  public void testSelect() {
		//int[] keys= {36,45,78,32,13,61,94,50,74,47,91,21,84,94,41,54,70,19,61,73};
		int[] keys= {31,95,12,90,79,5,45};
		LinkedHashMap<String,String> map=null;
		DML dml=new DML(IoUtil.getDr(db),1113);
		//dml.loadIndex(42503);
		System.out.println("Pointer\tId\tName\tSub1\tSub2\tSub3");
		for(int i=0;i<keys.length;i++) {
  		    int key=keys[i];
			map=dml.select(key);
			if(map!=null) {
				 map.forEach((k,v) ->{
				 System.out.print(v+"\t");
			  });
			}
			System.out.println();
		 	}
		dml.closeReader();
  }
	 
	 
	 @Test
	 public void readTest() {
		 DataReader reader=IoUtil.getDr(db);
		 DataWriter writer=IoUtil.getDw(db);
		 Integer integer=new Integer(25);
		 
		 System.out.println("keysize:"+reader.readInt());
		 System.out.println("indexlocation:"+reader.readInt());
		 reader.close();
	 }

}
