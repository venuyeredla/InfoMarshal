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
	 //compressor=new HuffManCoding();
	 compressor=new ArthimeticCompressor();
	}
	@Test
	public void testCompress() {
		String text=this.getText();
		byte[] compressed=compressor.compress(text.getBytes());
		System.out.println("Orignal: "+ text.getBytes().length+" Compressed Size:"+compressed.length);
		int ratio=text.getBytes().length/compressed.length;
		System.out.println("Compression Ratio: "+ratio );
		byte[] decompressed=compressor.decompress(compressed);
		StringBuilder adecompress=new StringBuilder();
		for(int i=0;i<decompressed.length;i++) {
			adecompress.append((char)decompressed[i]);
		}
		boolean result=text.equals(new String(decompressed));
	    System.out.println("\nBefore="+text);
		System.out.println("After ="+adecompress);
		Assert.assertEquals(true,result);
	 }
	public String getText() {
		String text="";
		/*String text="\r\n" + 
				"The prosecution case appeared to have been significantly bolstered by a confession by Aseemanand in 2010 in a magistrate court in Delhi, but his subsequent retraction cast a shadow over its voluntary nature. However, given the details in his statement on the planning and execution of some key terror attacks between 2006 and 2008, including bomb attacks in Malegaon, on the Samjhauta Express, at the Mecca Masjid in Hyderabad and the Ajmer Dargah, there will be inevitable questions about why the NIA failed to produce any significant evidence in the trial. Even after the judgment, one is at a loss to understand who was responsible for the blasts. If the initial knee-jerk response of the police was in keeping with the mood of the times, the record of the central agencies appears tainted by shoddy investigation and irresolute prosecution. The NIA has faced charges of going soft on Hindutva groups after the regime change at the Centre in 2014, once even from a public prosecutor handling the Malegaon blast case. That 66 out of 226 witnesses turned hostile reflects poorly on the investigating agency and exposes the lack of legal safeguards to protect witnesses. The investigating agencies face a credibility crisis, and how public faith in their impartiality can be restored is something the country ought to worry about now.";*/
		return text+text;
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
