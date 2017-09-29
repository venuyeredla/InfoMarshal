package org.vgr.store.io;

import org.junit.Test;

public class IOTest {
	String file="/home/venugopal/Documents/Work/io/db/io.data";
	RamStorage ramStorage=new RamStorage();
	@Test
	public void intTest() {
		DataWriter dw=new DataWriter(ramStorage);
		DataReader dr=new DataReader(ramStorage);
		dw.writeInt(225);
		int num=dr.readInt();
		System.out.println("Number :"+num);
		dw.close();
		dr.close();
	   }
}
