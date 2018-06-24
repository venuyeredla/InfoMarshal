package org.vgr.compress;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.vgr.util.RandomUtil;

public class BitPackingTest {
	
    BitPacking bitPacking=new BitPacking();
    @Test
	public void testBitPacking() {
		int[] ints= RandomUtil.randomIntsBW(60, 69, 128);
		byte[] compressed=bitPacking.compress(ints);
		int[] decompressed=bitPacking.decompress(compressed,null);
		for(int i=0;i<ints.length;i++) {
    		if(ints[i]!=decompressed[i]) {
    			Assert.fail("Numbers don't matched at position :"+i );
    		}
    	}
		System.out.println("Acutal bytes:"+(ints.length*4)+" Compressed:"+compressed.length);
		//bitPacking.deltaCompress(ints);
	}	
    
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
