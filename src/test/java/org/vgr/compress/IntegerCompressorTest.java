package org.vgr.compress;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.vgr.compress.IntegerCompressor.INT_MODE;
import org.vgr.util.RandomUtil;

public class IntegerCompressorTest {
	
	   IntegerCompressor integerCompressor=new IntegerCompressor();
	    @Test
	    @Ignore
		public void testBitPacking() {
			int[] ints= RandomUtil.randomIntsBW(10, 1, 16);
			byte[] compressed=integerCompressor.compress(ints, INT_MODE.BITPACKING);
			int[] decompressed=integerCompressor.decompress(compressed,INT_MODE.BITPACKING);
			for(int i=0;i<ints.length;i++) {
	    		if(ints[i]!=decompressed[i]) {
	    			Assert.fail("Numbers don't matched at position :"+i );
	    		}
	    	}
		}	
	    
	    @Test
		public void testDelta() {
			//int[] ints= RandomUtil.randomIntsBW(10, 69, 128);
		 int[] ints={44,45,46,48,49,50,55,57,58,59,65,66,67,72,73,74,77,78,82,83,84,89,93,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,121,122};
			Arrays.sort(ints);
			byte[] compressed=integerCompressor.compress(ints, INT_MODE.DELTA_BITPACKING);
			int[] decompressed=integerCompressor.decompress(compressed,INT_MODE.DELTA_BITPACKING);
			for(int i=0;i<ints.length;i++) {
	    		if(ints[i]!=decompressed[i]) {
	    			Assert.fail("Numbers don't matched at position :"+i );
	    		}
	    	}
		}
	    
	    
	    BitPacking bitPacking=new BitPacking();
	    @Test
	    @Ignore
	    public void test2BitPacking() {
	    	int[] nums= {1,2,3,1};
	    	
	    	int packedNumber=bitPacking.pack2(nums);
	    	int[] unpacked=bitPacking.unpack2(packedNumber);
	    	for(int i=0;i<4;i++) {
	    		if(nums[i]!=unpacked[i]) {
	    			Assert.fail("Numbers don't matched at position :"+i );
	    		}
	    	}
	    }

	    @Test
	    @Ignore
	    public void test3BitPacking() {
	    	int[] nums= {7,5,1,2,0};
	    	int packedNumber=bitPacking.pack3(nums);
	    	int[] unpacked=bitPacking.unpack3(packedNumber);
	    	for(int i=0;i<2;i++) {
	    		if(nums[i]!=unpacked[i]) {
	    			Assert.fail("4 Bit numbers don't matched at position :"+i );
	    		}
	    	}
	    }
	    
	    
	    @Test
	    @Ignore
	    public void test4BitPacking() {
	    	int[] nums= {6,14};
	    	int packedNumber=bitPacking.pack4(nums);
	    	int[] unpacked=bitPacking.unpack4(packedNumber);
	    	for(int i=0;i<2;i++) {
	    		if(nums[i]!=unpacked[i]) {
	    			Assert.fail("4 Bit numbers don't matched at position :"+i );
	    		}
	    	}
	    	
	    }
	    
	    @Test
	    @Ignore
	    public void test5BitPacking() {
	    	int[] nums= {21,31,16};
	    	int packedNumber=bitPacking.pack5(nums);
	    	int[] unpacked=bitPacking.unpack5(packedNumber);
	    	for(int i=0;i<3;i++) {
	    		if(nums[i]!=unpacked[i]) {
	    			Assert.fail("4 Bit numbers don't matched at position :"+i );
	    		}
	    	}
	    }

}
