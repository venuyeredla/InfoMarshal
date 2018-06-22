package org.vgr.app.util;

import java.util.HashSet;

public class RandomUtil {
	
	/**
	 * @param count Number of integers
	 * @param range range from 0-to mentioned.
	 * @return
	 */
	public static int[] randomNumbers(int count, int range){
		int[] randomNumbers=new int[count];
		for(int i=0;i<count;i++){
			randomNumbers[i]=(int) (Math.random()*range);
		}
		return randomNumbers;
	}
	public static HashSet<Integer> randomNumsSet(int count, int range){
		HashSet<Integer> keySet=new HashSet<>(); 
		for(int i=0;i<count;i++){
			keySet.add((int) (Math.random()*range));
		}
		return keySet;
	}
}
