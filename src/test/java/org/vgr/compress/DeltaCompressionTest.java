package org.vgr.compress;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class DeltaCompressionTest {

	DeltaCompression delta=new DeltaCompression();
	
	 @Test
		public void testDelta() {
			//int[] ints= RandomUtil.randomIntsBW(10, 69, 128);
		 int[] ints={44,45,46,48,49,50,55,57,58,59,65,66,67,72,73,74,77,78,82,83,84,89,93,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,121,122};
			Arrays.sort(ints);
			byte[] compressed=delta.deltaCompress(ints);
			int[] decompressed=delta.deltaDCompress(compressed);
			for(int i=0;i<ints.length;i++) {
	    		if(ints[i]!=decompressed[i]) {
	    			Assert.fail("Numbers don't matched at position :"+i );
	    		}
	    	}
			System.out.println("Acutal bytes:"+(ints.length*4)+" Compressed:"+compressed.length);
		}
}
