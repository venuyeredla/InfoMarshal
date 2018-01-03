package org.vgr.store.search;

import org.junit.Test;

public class PatternMatchingTest {
	
	
	@Test
	public void testLps() {
		String pat="AABAACAABAA";
		PatternMatching matching=new PatternMatching();
		int[] lps=matching.buildLps(pat);;
        for(int i=0;i<lps.length;i++)
        	System.out.print(lps[i]+",");
	}

}
