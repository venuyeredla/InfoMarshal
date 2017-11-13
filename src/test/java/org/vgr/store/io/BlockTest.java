package org.vgr.store.io;

import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class BlockTest {
	  Block block=null;

	  @Before
	  public void beore() {
		  block=new Block(50);
	  }
	
	  @Test
	  @Ignore
	  public void testByte() {
		  // -128-- 127
		  byte b=(byte)128;
		  System.out.println("Before byte:"+b);
		  block.write(b);
		  System.out.println("After byte :"+block.readByte());
	    }

		@Test
		@Ignore
	  	public void testShort() {
			  // 32,767 ---32,768
			 short s=(short)32770; 
			 System.out.println("Before short:"+s);
			 block.write(s);
			 System.out.println("After short :"+block.readShort());
		 }
		
		@Test
		public void testInt() {
			  // -2,147,483,648  ---2,147,483,647
			 int integer=200; 
			 System.out.println("Before int:"+integer);
			 block.write(integer);
			 System.out.println("After int :"+block.readInt());
			
		}
		
		@Test
		@Ignore
		public void testVInt() {
			 int integer=50000; 
			 System.out.println("Before Vint:"+integer);
			 block.writeVInt(integer);
			 System.out.println("After Vint :"+block.readVInt());
		}
		@Test
		@Ignore
		public void testString() {
			 String str="Block test string"; 
			 System.out.println("Before Vint:"+str);
			 block.write(str);
			 System.out.println("After Vint :"+block.readString());
		}
		@Test
		@Ignore
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
}
