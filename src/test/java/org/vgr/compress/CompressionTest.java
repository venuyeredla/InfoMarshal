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
	 compressor=new ArthimeticCoding();
	}
	@Test
	public void testCompress() {
		String text=this.getText();
		byte[] bytes=compressor.compress(text);
		System.out.println("Compressed Size:"+bytes.length);
		/*String decompressed=compressor.decompressToTxt(bytes);
	    boolean result=text.equals(decompressed);
	    System.out.println("\nBefore="+text);
		System.out.println("After ="+decompressed);
		Assert.assertEquals("Two strings are equal",true,result);*/
	 }
	public String getText() {
		String text="venugopalananyavenkatraghuvenugopalananyavenkatraghuvenugopalreddyvenugopalananyavenkatraghuvenugopalananyavenkatraghuvenugopalreddy";
		/*String text="the acquittal of five suspects in the Mecca Masjid bomb blast case is likely to reinforce public cynicism in the country about the state of the criminal justice system. Regardless of whether the acquittal was owing to the innocence of Swami Aseemanand and four others belonging to a Hindu right-wing group, or because the prosecuting agency lacked the resolve and freedom to obtain their conviction, the outcome is undoubtedly a substantial denial of justice for a crime that killed nine people and injured many others. It also shattered the lives of dozens of Muslims who were taken into custody by the Hyderabad police in the immediate aftermath of the blast in May 2007; their arbitrary incarceration, alleged custodial torture and the protracted court hearings amounted to grave miscarriage of justice. It has a striking similarity to the Malegaon blasts in Maharashtra: a group of Muslims being portrayed as the perpetrators in the initial stages, but the involvement of a Hindu group being uncovered later. The police first named the Harkat-ul-Jihadi-e-Islami as the organisation involved. The Central Bureau of Investigation, which took over the probe, chargesheeted 21 suspects, but they were acquitted in 2009 for want of evidence. It was only in 2010 that the CBI named some members of Abhinav Bharat, a few of whom were formerly RSS pracharaks, in a fresh charge sheet. The case changed hands again in 2011 and went to the National Investigation Agency.\r\n" + 
				"The prosecution case appeared to have been significantly bolstered by a confession by Aseemanand in 2010 in a magistrate court in Delhi, but his subsequent retraction cast a shadow over its voluntary nature. However, given the details in his statement on the planning and execution of some key terror attacks between 2006 and 2008, including bomb attacks in Malegaon, on the Samjhauta Express, at the Mecca Masjid in Hyderabad and the Ajmer Dargah, there will be inevitable questions about why the NIA failed to produce any significant evidence in the trial. Even after the judgment, one is at a loss to understand who was responsible for the blasts. If the initial knee-jerk response of the police was in keeping with the mood of the times, the record of the central agencies appears tainted by shoddy investigation and irresolute prosecution. The NIA has faced charges of going soft on Hindutva groups after the regime change at the Centre in 2014, once even from a public prosecutor handling the Malegaon blast case. That 66 out of 226 witnesses turned hostile reflects poorly on the investigating agency and exposes the lack of legal safeguards to protect witnesses. The investigating agencies face a credibility crisis, and how public faith in their impartiality can be restored is something the country ought to worry about now.";*/
		return text;
	}
	
	
	@Test
	@Ignore
	public void testFreq() {
		FreqTable freqTable=new FreqTable(63,256);
		String text=this.getText();
		freqTable.buildRanges(text.getBytes());
		System.out.println("v == Low :"+freqTable.getLow(118)+"  High:"+freqTable.getHigh(118));
	}
	
	@Test
	@Ignore
	public void testNum() {
		int num=16;
		System.out.println(num+" :"+NumSysUtil.decToBin(num));
	}
	
}
