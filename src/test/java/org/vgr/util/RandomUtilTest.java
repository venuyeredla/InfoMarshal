package org.vgr.util;

import org.junit.Test;

public class RandomUtilTest {
	
	@Test
	public void testRandomArray(){
		int iterations=3;
		while(iterations>0) {
			int[] numbers=RandomUtil.randomInts(5,10);
			StringBuffer stringBuffer=new StringBuffer();
			for(int i=0;i<numbers.length;i++){
				stringBuffer.append(numbers[i]+",");
			}
			System.out.println(stringBuffer);
			iterations--;
		}
	}
	
	@Test
	public void testRandomArrayRange(){
		int iterations=3;
		while(iterations>0) {
			int[] numbers=RandomUtil.randomInts(5,5,10);
			StringBuffer stringBuffer=new StringBuffer();
			for(int i=0;i<numbers.length;i++){
				stringBuffer.append(numbers[i]+",");
			}
			System.out.println(stringBuffer);
			iterations--;
		}
	}

}

