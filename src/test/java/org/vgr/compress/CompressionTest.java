package org.vgr.compress;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompressionTest {
	private static final Logger LOG = LoggerFactory.getLogger(CompressionTest.class);
	private static Compressor compressor=null;

	@BeforeClass
	public static void init() {
	 LOG.info("Initializing compressor");
	 compressor=new HuffManCoding();
	 //compressor=new ArthimeticCoding();
	}
	@Test
	public void testCompress() {
		String text=this.getText();
		byte[] bytes=compressor.compress(text);
		String decompressed=compressor.decompressToTxt(bytes);
	    boolean result=text.equals(decompressed);
	    System.out.println("\nActual="+text);
		System.out.println("Decompressed ="+decompressed);
		Assert.assertEquals("Two strings are equal",true,result);
	 }
	public String getText() {
		//String text="venugopalananyavenkatraghu";
		String text="venugopalvenugopalvenugopalvenugopal";
		return text;
	}
}
