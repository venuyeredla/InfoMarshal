package org.vgr.compress;

import org.junit.Test;

public class HuffmanTest {
	
	@Test
	public void testCompress() {
		
		HuffManCoding huffManCoding=new HuffManCoding();
		huffManCoding.compress("aaaabbbccccdeeeeeeeffffffiiiiiiii");
	}

}
