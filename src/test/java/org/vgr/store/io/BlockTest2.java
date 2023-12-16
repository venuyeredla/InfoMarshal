package org.vgr.store.io;
import static org.vgr.store.io.StoreConstants.BLOCK_SIZE;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BlockTest2 {
	@BeforeClass
	public void writeTest() {
		String file="";
		DataWriter writer=new DataWriter(file,false);
		for(int i=0;i<10;i++) {
			ByteBuf block=new ByteBuf();
			block.write("Block and venu "+i);
			int offset=i*BLOCK_SIZE;
			writer.writeBlock(offset, block);
		 }
		writer.close();
	}
	
	@Test
	public void testWriteRead() {
	  System.out.println("Writing complted and reading started");	
	}
	
	@AfterClass
   public void readTest() {
		String file="";
	   DataReader reader=new DataReader(file);
	   for(int i=0;i<10;i++) {
		    int offset=i*BLOCK_SIZE;
		    ByteBuf block=reader.readBlock(offset);
		    System.out.println("Block num:"+i+" and value is:"+block.readString());
		 }
	   reader.close();
	}

}
