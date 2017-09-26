package org.vgr.store.io;

import org.junit.Test;

public class IOTest {
	String file="/home/venugopal/Documents/Work/io/db/io.data";
	
	@Test
	public void testWrite() {
		DataWriter dw=IoUtil.getDw(file);
		dw.writeInt(225);
		dw.close();
		
		
	}
	
	@Test
   public void testRead() {
	   DataReader dw=IoUtil.getDr(file);
		int num=dw.readInt();
		System.out.println("Number :"+num);
		dw.close();
	}
	

}
