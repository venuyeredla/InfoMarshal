package org.vgr.compress;

import java.util.Arrays;

import org.vgr.store.io.ByteBuf;

public class DeltaCompression {
	
	 
    /**
     * Numbers should be in non decreasing pattern
     * @param ints
     */
	public byte[] deltaCompress(int[] ints) {
		 Arrays.sort(ints);
		 ByteBuf byteBuf=new ByteBuf();
		 int first=ints[0];
		 byteBuf.writeVInt(ints.length);
		 byteBuf.writeVInt(first);
		 
		 int[] deltas=new int[ints.length];
		 deltas[0]=0;
		 for(int j=1;j<ints.length;j++) {
			 deltas[j]=ints[j]-ints[j-1];
		 }
		 for(int j:deltas) System.out.print(j+",");
		 
		 BitPacking bitPacking=new BitPacking();
		 byte[] compressed=bitPacking.compress(deltas);
		 byteBuf.write(compressed);
		 
		 int bitWidth=getBitWidth(deltas);
		 int maxNum=(1<<bitWidth)-1;
		 System.out.println("\nRequred bit width :"+bitWidth+" Max number can be :"+maxNum);
		 return byteBuf.getActualBytes();
		}

	 /**
     * Numbers should be in non decreasing pattern
     * @param ints
     */
	public int[] deltaDCompress(byte[] bytes) {
		   ByteBuf byteBuf=new ByteBuf(bytes);
		   int size=byteBuf.readVInt();
		   int first=byteBuf.readVInt();
		
		   BitPacking bitPacking=new BitPacking();
		   int[] deltas=bitPacking.decompress(bytes,byteBuf);
		   
		   int[] uncompressed=new int[deltas.length];
			 uncompressed[0]=first;
			 for(int k=1;k<deltas.length;k++) {
				 uncompressed[k]=uncompressed[k-1]+deltas[k];
			 }
		
		return uncompressed;
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
