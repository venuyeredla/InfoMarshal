package org.vgr.store.io;

import java.util.LinkedHashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ByteBuffTest {
	 ByteBuf byteBuf=null;

	  @Before
	  public void beore() {
		  byteBuf=new ByteBuf(50);
	  }
	
	  @Test
	  @Ignore
	  public void testByte() {
		  // -128-- 127
		     int input=252;
			 byteBuf.writeByte(input);
			 int output=byteBuf.read();
			 Assert.assertEquals(input, output);
	    }

		@Test
		@Ignore
	  	public void testShort() {
			  // 32,767 ---32,768
			 short input=(short)32770; 
			 byteBuf.write(input);
			 int output=byteBuf.readShort();
			 Assert.assertEquals(input, output);
		 }
		
		@Test
		@Ignore
		public void testInt() {
			  // -2,147,483,648  ---2,147,483,647
			 int input=1000;
			 byteBuf.write(input);
			 int output=byteBuf.readInt();
			 Assert.assertEquals(input, output);
		}
		
		@Test
		@Ignore
		public void testVInt() {
			 int input=50000 ;
			 byteBuf.write(input);
			 int output=byteBuf.readVInt();
			 Assert.assertEquals(input, output);
		}
		
		@Test
		@Ignore
		public void testString() {
			 String input="Block test string";
			 byteBuf.write(input);
			 String output=byteBuf.readString();
			 Assert.assertEquals(input, output);
		}
		@Test
		public void writeTest() {
			byteBuf=new ByteBuf(100);
			byteBuf.write(575);
			byteBuf.write("venugopal Reddy");
			byte b=(byte)127;
			byteBuf.write(b);
			LinkedHashMap<String,String> map=new LinkedHashMap<>();
			map.put("field-1", "fvalue-1");
			map.put("field-2", "fvalue-2");
			map.put("field-3", "fvalue-3");
			byteBuf.write(map);
			//Reading
			System.out.println("Integer:"+byteBuf.readInt());
			System.out.println("String :"+byteBuf.readString());
			System.out.println("byte:"+byteBuf.readByte());
			byteBuf.readMap().forEach((k,v)-> {System.out.println(k+" - "+v);});
		}
}
