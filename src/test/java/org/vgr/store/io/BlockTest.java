package org.vgr.store.io;

import java.util.LinkedHashMap;

import org.junit.Ignore;
import org.junit.Test;

public class BlockTest {
	Block block=null;
	@Test
	public void writeTest() {
		block=new Block(100);
		block.write(575);
		block.write("venugopal Reddy");
		byte b=(byte)127;
		block.write(b);
		LinkedHashMap<String,String> map=new LinkedHashMap<>();
		map.put("field-1", "fvalue-1");
		map.put("field-2", "fvalue-2");
		map.put("field-3", "fvalue-3");
		block.write(map);
		//Reading
		System.out.println("Integer:"+block.readInt());
		System.out.println("String :"+block.readString());
		System.out.println("byte:"+block.readByte());
		block.readMap().forEach((k,v)-> {System.out.println(k+" - "+v);});
	}
	
	@Test
	@Ignore
	public void readTest() {
		
	}
}
