package org.vgr.crypt;

import java.util.zip.Adler32;

import org.junit.Test;

public class CheckSumTest {
	
	@Test
	public void testCheckSum() {
		  Adler32 adler32=new Adler32();
		  String input="testing checksum";
		  adler32.update(input.getBytes());
		  System.out.println("Checksum for: "+ new String(input) +"  -- Checksum : "+adler32.getValue());
		  adler32.update("testing check".getBytes());
		  System.out.println("Checksum for: testing check "+ "  -- Checksum : "+adler32.getValue());
	}

}
