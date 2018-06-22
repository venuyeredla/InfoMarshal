package org.vgr.compress;

import org.junit.Test;

public class BitPackingTest {
@Test
	public void testBitPacking() {
		int size=5;
		int[] ints=new int[size]; 
		for(int i=0,j=127;i<size;i++,j+=4) {
			ints[i]=j;
		  }
		BitPacking bitPacking=new BitPacking();
		//bitPacking.compress(ints);
		bitPacking.compressDelta(ints);
	}
}
