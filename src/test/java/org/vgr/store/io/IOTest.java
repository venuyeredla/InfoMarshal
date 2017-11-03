package org.vgr.store.io;

import java.util.LinkedHashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IOTest {
	String file=FileUtil.getPath("io.data");
	RamStorage ramStorage=new RamStorage();
	@Before
	public void writeTest() {
		DataWriter writer=new DataWriter(file);
		Block block=new Block(100);
		block.write(575);
		block.write("venugopal Reddy");
		block.write((byte)127);
		LinkedHashMap<String,String> map=new LinkedHashMap<>();
		map.put("field-1", "fvalue-1");
		map.put("field-2", "fvalue-2");
		map.put("field-3", "fvalue-3");
		block.write(map);
		writer.writeBlock(block);
		writer.close();
		System.out.println("Integer:"+block.readInt());
		System.out.println("String :"+block.readString());
		System.out.println("byte:"+block.readByte());
		block.readMap().forEach((k,v)-> {System.out.println(k+" - "+v);});
	   }
	
	@Test
	public void test() {
		
	}
	
	@After
	public void readTest() {
		DataReader reader=new DataReader(file);
		Block block=reader.readBlock();
		System.out.println("Integer:"+block.readInt());
		System.out.println("String :"+block.readString());
		System.out.println("byte:"+block.readByte());
		block.readMap().forEach((k,v)-> {System.out.println(k+" - "+v);});
		reader.close();
	   }
	
	
	
	
}
