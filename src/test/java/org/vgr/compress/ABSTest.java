package org.vgr.compress;

import org.junit.Test;
import org.vgr.store.io.ByteBuf;

public class ABSTest {
	
	@Test
	public void testBC() {
		String bitStr="00101000101110101001010";
		
		Encoder encoder =new Encoder(Mode.COMPRESS, new ByteBuf());
		
		for(int i=0;i<bitStr.length();i++) {
			 int bit=bitStr.charAt(i)=='1'?1:0;
			 encoder.encode(bit);
	     }
		
		
	  }

}
