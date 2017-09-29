package org.vgr.store.io;

import org.junit.Ignore;
import org.junit.Test;

public class NumbersTest {
	
	@Test
	public void testDecToBinary() {
		int dec=225;
		String binary=NumberSystems.dectToBinary(dec);
		System.out.println("Dec to bin :"+dec+ " - "+binary);
		System.out.println(binary + ": "+NumberSystems.binaryToDec(binary));
		
		
	}
	

}
