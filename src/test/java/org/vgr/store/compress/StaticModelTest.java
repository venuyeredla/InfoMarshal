package org.vgr.store.compress;

import org.junit.Assert;
import org.junit.Test;
import org.vgr.store.io.ByteBuf;

public class StaticModelTest {
	@Test
	public void testCounts() {
		StaticModel model=new StaticModel();
		String text=TextReader.getText();
		//System.out.println(text);
		model.calFreq(text.getBytes());
		ByteBuf byteBuf=new ByteBuf();
		int[] keysWritten=model.writeFreqs(byteBuf);
		int[] keysRead=model.readFreqs(byteBuf);
		for(int i=0;i<keysWritten.length;i++) {
    		if(keysWritten[i]!=keysRead[i]) {
    			Assert.fail("Numbers don't matched at position :"+i );
    		}
    	}
	   }
}