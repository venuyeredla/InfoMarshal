package org.vgr.app.util;

public class RandomUtil {
	
	public int[] randomNumbers(int count, int range){
		int[] randomNumbers=new int[count];
		for(int i=0;i<count;i++){
			randomNumbers[i]=(int) (Math.random()*range);
		}
		return randomNumbers;
	}

}
