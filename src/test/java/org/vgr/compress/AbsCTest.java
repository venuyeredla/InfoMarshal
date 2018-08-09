package org.vgr.compress;

import org.junit.Ignore;
import org.junit.Test;

public class AbsCTest {
	@Test
	@Ignore
	public void testSBS() {
		int[][] stable=new int[8][2];
        int newState=0;
		for(int i=1;i<4;i++) {
			for(int j=0;j<2;j++) {
				if(j==0) {
					newState=2*i;
					stable[newState][j]=i;	
				}else if(j==1) {
					newState=2*i+1;
					stable[newState][j]=i;
				}
			}
		}

		for(int i=1;i<8;i++) {
			System.out.print(i+" : ");
			for(int j=0;j<2;j++) {
				if(stable[i][j]!=0) {
					System.out.print(stable[i][j]+"  ");
				}else {
					System.out.print("-"+"  ");
				}
			}
			System.out.println();
		}
		
     //Enocde
		String bibuf="001011";
		int num=1;
		int[] bs=new int[6];
		int bsi=0;
		System.out.println("\nOriginal : "+bibuf);
		for(int j=0;j<bibuf.length();j++) {
			 int bit=bibuf.charAt(j)=='1'?1:0;
			 if(num>3) {
				 bs[bsi++]=num&1;
				 num=num>>1;
			 }
			 num=num<<1|bit;
		 }
		while(num>1) {
			 bs[bsi++]=num&1;
			 num=num>>1;
		 }
		System.out.print("Encoded : ");
        for(int v:bs) {
        	System.out.print(v);
        }

		//Decode
		int j=5;
		for(;j>3;j--) {
			num=num<<1 | bs[j];
		}
		System.out.print("\nDecoded : ");
		while(num>1) {
			 if(stable[num][0]==0) {
				 System.out.print(1);
			 }else {
				 System.out.print(0);
			 }
			 num=num>>1;
			 if(num==2 && j>=0) {
				 num=num<<1 | bs[j--];
			 }
		}
	}

	@Test
	public void testABS() {
		String bitStr="001010001011101010010100";
		int ones=0;
		int zeros=0;
		for(int bit: bitStr.getBytes()){
			if(bit==48) zeros++;
			if(bit==49) ones++;
		}
		int total=ones+zeros;
		System.out.println("Total:"+total+" 1's:"+ones+"	0's:"+zeros);
		
		double p1=((double)ones)/total;
		double p0=1-p1;
		p1=Double.parseDouble(String.format("%.2f", p1));
		p0=Double.parseDouble(String.format("%.2f", p0));
		System.out.println("p0="+p0+" , p1="+p1);
		
		int[][] stable=new int[35][2];
        double newState=0;
		
		for(int i=1;i<=14;i++) {
			System.out.print("State : "+i +"  ");
			for(int j=0;j<2;j++) {
				if(j==0) {
					newState=Math.floor(i/p0);
					System.out.print(newState +" - "+p0+" 	 ");
					stable[(int)newState][1]=0;
				}else if(j==1) {
					newState=Math.floor(i/p1);
					System.out.print(newState +" -"+p1);
					stable[(int)newState][1]=1;
					
				}
				stable[(int)newState][0]=i;
			}
			System.out.println();
		}

		for(int i=1;i<25;i++) {
			System.out.print(i+" : ");
			/*for(int j=0;j<2;j++) {*/
				System.out.print(stable[i][0]+" - "+stable[i][1]);
				/*if(stable[i][j]!=0) {
					System.out.print(stable[i][j]+"  ");
				}else {
					System.out.print("-"+" 	");
				}*/
			/*}*/
			System.out.println();
		}

/*		System.out.println("0 's distribution");
		double temp=1;
		for(int i=1;i<=13;i++) {
		  temp=temp/p0;
		  System.out.print(temp+" ,");	
		}
		int x=1;
		System.out.println();
		for(int i=1;i<=13;i++) {
		  x=(int)Math.ceil(x/p0);
		  System.out.print(i+"-"+x+" ,");	
		}
		System.out.println();
		
		
		x=1;
		for(int i=1;i<=10;i++) {
			 x=((int)((x+1)/p1))-1;
			  System.out.print(i+"-"+x+" ,");	
		}
		*/
		
/*		 int binary=1;
		 int x=1;
		 System.out.println(bitStr);
		 StringBuilder reverse=new StringBuilder(bitStr).reverse();
		 
		 for(int j=0;j<bitStr.length();j++) {
			 int bit=bitStr.charAt(j)=='1'?1:0;
			 binary=binary<<1|bit;
		 }
		 
		 //Encoding
		for(int i=0;i<reverse.length();i++) {
			 int bit=reverse.charAt(i)=='1'?1:0;
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
		System.out.println("\nBinary: "+binary);*/
	}
	
	
}
