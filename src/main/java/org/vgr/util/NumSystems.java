package org.vgr.util;

public class NumSystems {
	
	public static String decToBin(int num) {
		if(num==0) return ""+0;
		String bitStr="";
		while(num>0) {
			bitStr=(num&1)+bitStr;
			num=num>>1;
		}
		return bitStr;
	}
	
	public static int binToDec(String binary) {
		int num=0;
		for(int i=0;i<binary.length();i++) {
			int bit=binary.charAt(i)=='0'?0:1;
			num =num<<1 |bit;
		}
		return num;
	}
	
	/*public static int binaryToDec(String binary) {
		int num=0;
		int noofbits=binary.length()-1;// no of bits minus to perform decimal conversion
		int expo=noofbits;
		for(int i=0;i<=noofbits;i++,expo--) {
			int x=binary.charAt(i)=='0'?0:1;
			num+=x*Math.pow(2, expo);
		}
		return num;
	}*/

}
