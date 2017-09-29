package org.vgr.store.io;

public class NumberSystems {
	
	public static String dectToBinary(int i) {
		StringBuilder sb=new StringBuilder();
		while(i>2) {
			sb.append(i%2);
			i=i/2;
		}
		sb.append(i);
		return new String(sb.reverse());
	}
	
	public static int binaryToDec(String binary) {
		int num=0;
		int noofbits=binary.length()-1;// no of bits minus to perform decimal conversion
		int expo=noofbits;
		for(int i=0;i<=noofbits;i++,expo--) {
			int x=binary.charAt(i)=='0'?0:1;
			num+=x*Math.pow(2, expo);
		}
		return num;
	}

}
