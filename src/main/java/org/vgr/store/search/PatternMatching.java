package org.vgr.store.search;

public class PatternMatching {
	
	public int[] buildLps(String pat) {
		  int[] lps=new int[pat.length()];
		  int i=0,j=1;
		  lps[i]=0;
		  while(j<pat.length()) {
			  if(pat.charAt(i)==pat.charAt(j)) {
				  lps[j]=i+1;
				  i++; j++;
			  }else {
				  if(i!=0) {
					  i=lps[i-1];
				  }else {
					  lps[j]=i;
					  j++;
				  }
			  }
		  }
		return lps;
	}

}
