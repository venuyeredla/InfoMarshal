package org.vgr.compress;

import org.junit.Test;

public class AbsCTest {
	
	@Test
	public void testABS() {
		String bitStr="001010001011101010";
		int[]  table={0,0};
		bitStr.chars().map(num->num-48).forEach(bit->{
			if(bit==0) table[0]++;
			if(bit==1) table[1]++;
		});
		double p1=(double)table[1]/(table[0]+table[1]);
		double p0=1-p1;
		p1=Double.parseDouble(String.format("%.2f", p1));
		p0=Double.parseDouble(String.format("%.2f", p0));
		System.out.println("p0="+p0+" , p1="+p1);
		 int binary=1;
		 int x=1;
		 
		 System.out.println(bitStr);
		 StringBuilder reverse=new StringBuilder(bitStr).reverse();
		 //Encoding
		for(int i=0;i<reverse.length();i++) {
			 int bit=reverse.charAt(i)=='1'?1:0;
			 binary=binary<<1|bit;
			// System.out.print(x+" -("+bit+")-> ");
			 if(bit==0) {
				// double temp=x/p0;
				double temp2=(x+1)/p0;
				int ij=(int) (Math.ceil(temp2)-1);
				 x=ij;
			 }else if(bit==1) {
				 double temp2=x/p1;
				 x=(int)(temp2);
			 }
	         
	     }
//		 System.out.print(x);
		//Decoding
		 //System.out.println();
		 System.out.println("Encoded : "+x);
		System.out.println("Decoded");
		while(x>1) {
			  double temp1=(x+1)*p1;
			  double temp2=x*p1;
			  double t1=Math.ceil(temp1);
			  double t2=Math.ceil(temp2);
		  int bit=(int)(t1-t2);
		  // System.out.print(" -("+bit+")-> ");
		   if(bit==0) {
			   x=(int) (x*p0);   
		   }else if(bit==1) {
			   x=(int) Math.ceil(x*p1);   
		   }
           System.out.print(bit);
		}
		System.out.println();
		System.out.println("\nBinary: "+binary);
	}

}
