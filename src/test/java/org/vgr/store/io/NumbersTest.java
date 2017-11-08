package org.vgr.store.io;

import org.junit.Test;

public class NumbersTest {
	
	@Test
	public void testDecToBinary() {
		int dec=4104;
		String binary=NumberSystems.dectToBinary(dec);
		byte b=(byte)dec;
		System.out.println("Dec to bin :"+dec+ " - "+binary);
		System.out.println("Byte to bin :"+b+ " - "+NumberSystems.dectToBinary(b));
		System.out.println(binary + ": "+NumberSystems.binaryToDec(binary));
	}
}
