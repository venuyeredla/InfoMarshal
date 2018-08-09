package org.vgr.compress;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
	 //compressor=new ArthimeticCompressor();
	}
	@Test
	public void testCompress() {
		byte[] input=this.getText().getBytes();
		byte[] compressed=compressor.compress(input);
		byte[] decompressed=compressor.decompress(compressed);
		String text=new String(decompressed);
		System.out.println(text);
		for(int i=0;i<input.length;i++) {
    		if(input[i]!=decompressed[i]) {
    			Assert.fail("Numbers don't matched at position :"+i );
    		}
    	}
		int ratio=input.length/compressed.length;
		System.out.println("Orignal: "+ input.length+" Compressed Size:"+compressed.length);
		System.out.println("Compression Ratio: "+ratio );
	 }
	public String getText() {
		return TextReader.getText();
	}
	
	@Test
	@Ignore
	public void testFreq() {
		FreqTable freqTable=new FreqTable(63,256);
		String text=this.getText();
		freqTable.buildRanges(text.getBytes());
		System.out.println("v == Low :"+freqTable.getLow(118)+"  High:"+freqTable.getHigh(118));
	}
	
	
}
