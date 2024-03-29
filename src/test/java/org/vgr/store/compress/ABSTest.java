package org.vgr.store.compress;

import org.junit.Ignore;
import org.junit.Test;
import org.vgr.store.compress.Abs;
import org.vgr.store.io.ByteBuf;

public class ABSTest {
	
	@Test
	@Ignore
	public void testBC() {
		String bitStr="00101000101110101001010";
		Encoder encoder =new Encoder(Mode.COMPRESS, new ByteBuf());
		for(int i=0;i<bitStr.length();i++) {
			 int bit=bitStr.charAt(i)=='1'?1:0;
			 encoder.encode(bit);
	     }
	  }

	@Test
	public void testABS() {
		String bitStr="00101000101110101001010";
		Abs abs=new Abs();
		abs.buildModel(bitStr);
	  }
	

}
