package org.vgr.app.tst.util;

import org.junit.Test;
import org.vgr.app.util.RandomUtil;

public class RandomUtilTest {
	
	@Test
	public void testRandomArray(){
		RandomUtil randomUtil=new RandomUtil();
		int[] numbers=randomUtil.randomNumbers(30,100);
		for(int i=0;i<numbers.length;i++){
			System.out.print(numbers[i]+",");
		}
		
	}

}
