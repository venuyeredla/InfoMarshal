package org.vgr.store.search;

import org.junit.Test;

public class PatternMatchingTest {
	
	String text="";
	String pattern="";
	
	PatternMatching matcher=new PatternMatching();

	@Test
	public void testNaiveMatching() {
		String text="venugopal";  //n size
		String pattern="gopal"; // m size
		int index=matcher.searchNaive(text,pattern);
		System.out.println("Matched index : "+index);
		 matcher.searchKMP(text,pattern);
	}
	
	@Test
	public void testLps() {
		String pat="AABAACAABAA";
		int[] lps=matcher.buildLps(pat);;
        for(int i=0;i<lps.length;i++)
        	System.out.print(lps[i]+",");
	}
	
}
