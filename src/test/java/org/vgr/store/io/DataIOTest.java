package org.vgr.store.io;

import java.util.LinkedHashMap;

public class DataIOTest {
	String file=FileUtil.getPath("io.data");
	public void writeTest() {
		DataWriter writer=new DataWriter(file,false);
		ByteBuf block=new ByteBuf(100);
		block.write("venugopal Reddy");
		block.write(4104);
		block.write((byte)127);
		LinkedHashMap<String,String> map=new LinkedHashMap<>();
		map.put("field-1", "fvalue-1");
		map.put("field-2", "fvalue-2");
		map.put("field-3", "fvalue-3");
		block.write(map);
		writer.writeBlock(0,block);
		writer.close();
		System.out.println("String :"+block.readString());
		System.out.println("Integer:"+block.readInt());
		System.out.println("byte:"+block.readByte());
		block.readMap().forEach((k,v)-> {System.out.println(k+" - "+v);});
	   }
public void readTest() {
		DataReader reader=new DataReader(file);
		ByteBuf block=reader.readBlock(0);
		System.out.println("String :"+block.readString());
		System.out.println("Integer:"+block.readInt());
		System.out.println("byte:"+block.readByte());
		block.readMap().forEach((k,v)-> {System.out.println(k+" - "+v);});
		reader.close();
   }
}
