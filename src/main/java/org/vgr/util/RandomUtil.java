package org.vgr.util;

import java.util.HashSet;

public class RandomUtil {
	
	/**
	 * @param count Number of integers
	 * @param range range from 0-to mentioned.
	 * @return
	 */
	public static int[] randomInts(int count, int range){
		int[] randomNumbers=new int[count];
		for(int i=0;i<count;i++){
			randomNumbers[i]=(int) (Math.random()*range);
		}
		return randomNumbers;
	}
	
	
	public static int[] randomIntsBW(int count, int from, int to){
		int[] randomNumbers=new int[count];
		for(int i=0;i<count;){
			int num=(int) (Math.random()*to);
			if(num>=from && num<=to){
				randomNumbers[i]=num;
				i++;
			}
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
