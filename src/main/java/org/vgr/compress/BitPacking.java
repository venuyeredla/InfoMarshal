package org.vgr.compress;

import org.vgr.store.io.NumberSystems;

public class BitPacking {
	
	public void compress(int[] ints) {
	 int bitWidth=getBitWidth(ints);
	 int maxNum=(1<<bitWidth)-1;
	 System.out.println("Requred bit width :"+bitWidth+" Max number can be :"+maxNum);
	 for(int i=0;i<ints.length;i++){
		 String actual=NumberSystems.decToBin(ints[i]);
		 String bits=this.appendZeros(actual, bitWidth);
		 System.out.println(bits+" - "+ints[i]);
	  }
	}
    /**
     * Numbers should be in non decreasing pattern
     * @param ints
     */
	public void compressDelta(int[] ints) {
		 int first=ints[0];
		 int[] deltas=new int[ints.length];
		 deltas[0]=0;
		 System.out.println("First"+first+"\n0");
		 for(int j=1;j<ints.length;j++) {
			 deltas[j]=ints[j]-ints[j-1];
			 System.out.println(deltas[j]);
		 }
		 int bitWidth=getBitWidth(deltas);
		 int maxNum=(1<<bitWidth)-1;
		 System.out.println("Requred bit width :"+bitWidth+" Max number can be :"+maxNum);
		
	/*	 int bitWidth=getBitWidth(ints);
		 int maxNum=(1<<bitWidth)-1;
		 System.out.println("Requred bit width :"+bitWidth+" Max number can be :"+maxNum);
		 for(int i=0;i<ints.length;i++){
			 String actual=NumberSystems.decToBin(ints[i]);
			 String bits=this.appendZeros(actual, bitWidth);
			 System.out.println(bits+" - "+ints[i]);
		  }*/
		}
	
	
	
	private String appendZeros(String bitstr,int width) {
		int len=bitstr.length();
		int toFill=width-len;
		StringBuilder builder=new StringBuilder();
		for(int i=0;i<toFill;i++) {
			builder.append("0");
		}
		 builder.append(bitstr);
		return new String(builder);
	}
	
	/**
	 * Returns the required number of bits hold the information of max number.
	 * @param ints
	 * @return
	 */
	private int getBitWidth(int[] ints) {
		 int max=0;
		 for(int i=0;i<ints.length;i++) {
			 if(max<ints[i]) {
				 max=ints[i];
			 }
		 }
		for(int j=2;j<31;j++) {
			int num=(1<<j) -1;
			if(max<=num) {
				return j;
			}
		}
		return 0;
	}

}
