package org.vgr.store.search;

public class PatternMatching {
	

	public boolean startingWith(String pat, String txt) {
		
		  int patLength=pat.length();
		  int textLength=pat.length();
		  
		  int[][] TF=new int[patLength+1][]; 
		
		  return false;
		}
	
	
	public boolean endingWith(String pat, String txt) {
		
		  return false;
		}
	
	public boolean match(String pat, String txt) {
		
	  return false;
	}
	
	
	
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
