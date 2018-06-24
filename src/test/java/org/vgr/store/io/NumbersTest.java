package org.vgr.store.io;

import org.junit.Test;
import org.vgr.util.NumSystems;

public class NumbersTest {
	@Test
	public void testNumberConversions() {
		for(int i=0;i<50;i++) {
		 int rand=(int)(Math.random()*1000);
		 String binary=NumSystems.decToBin(rand);
		 int converted=NumSystems.binToDec(binary);
		 if(!(rand==converted)) {
			 System.out.println("Failed (Actual,converted,binary) : "+rand+","+converted+ ","+binary);
		   }else {
			   System.out.println("sucess : "+rand);
		   }
		}
		
	}
}
