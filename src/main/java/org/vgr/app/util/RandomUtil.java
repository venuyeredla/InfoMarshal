package org.vgr.app.util;

public class RandomUtil {
	
	/**
	 * 
	 * @param count Number of integers
	 * @param range range from 0-to mentioned.
	 * @return
	 */
	public int[] randomNumbers(int count, int range){
		int[] randomNumbers=new int[count];
		for(int i=0;i<count;i++){
			randomNumbers[i]=(int) (Math.random()*range);
		}
		return randomNumbers;
	}

}
