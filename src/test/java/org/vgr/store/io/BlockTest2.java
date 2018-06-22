package org.vgr.store.io;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BlockTest2 {
	static String blockFile=FileUtil.getPath("block.dat");
	
	
	@BeforeClass
	public void writeTest() {
		DataWriter writer=new DataWriter(blockFile,false);
		
		for(int i=0;i<10;i++) {
			ByteBuf block=new ByteBuf();
			block.write("Block and venu "+i);
			int offset=i*ByteBuf.BLOCK_SIZE;
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
	   DataReader reader=new DataReader(blockFile);
	   for(int i=0;i<10;i++) {
		    int offset=i*ByteBuf.BLOCK_SIZE;
		    ByteBuf block=reader.readBlock(offset);
		    System.out.println("Block num:"+i+" and value is:"+block.readString());
		 }
	   reader.close();
	}

}
