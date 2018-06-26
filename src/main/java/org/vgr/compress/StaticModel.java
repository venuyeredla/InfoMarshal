package org.vgr.compress;

import org.vgr.compress.IntegerCompressor.INT_MODE;
import org.vgr.store.io.ByteBuf;

/**
 * Iterates all byte content and counts symbol frequencies. 
 * @author vyeredla
 *
 */
public class StaticModel {
	  int[] freqs=new int[256];
	  KeyVal[] keyValues;
	  public StaticModel() {}

	  public void calculateFreq(byte[] bytes) {
		    for (byte c : bytes) freqs[c]++;
		    int count=0;
		    for(int f:freqs) {
		    	if(f!=0) {
		    		count++;
		    	 }
		    }
		    int j=0;
		    keyValues=new KeyVal[count];
		    for(int i=0;i<freqs.length;i++) {
		    	if(freqs[i]!=0) {
		    		keyValues[j++]=new KeyVal(i, freqs[i]);
		    	}
		    }
		    
		   // writeFreqs();
	  }
	  public int[] writeFreqs(ByteBuf byteBuf) {
		   int keys[]=new int[keyValues.length];
		   int values[]=new int[keyValues.length];
		   int i=0;
		   for (KeyVal kv : keyValues) {
			   keys[i]=kv.key;
			   System.out.print(kv.key+",");
			   values[i]=kv.val;
			   i++;
		    }
		   IntegerCompressor integerCompressor=new IntegerCompressor();
		   byte[] keysCompressed=integerCompressor.compress(keys, INT_MODE.DELTA_BITPACKING);
		   byteBuf.write(keysCompressed);
		   
		   return keys;
	   }
	  
	  public int[] readFreqs(ByteBuf byteBuf) {
		  IntegerCompressor integerCompressor=new IntegerCompressor();
		  int[] keys=integerCompressor.decompress(byteBuf.getActualBytes(),INT_MODE.DELTA_BITPACKING);
		  for(int i:keys) System.out.print(i+",");
		  return keys;
	  }
}


class KeyVal{
   	int key;
   	int val;
	public KeyVal(int key, int val) {
		super();
		this.key = key;
		this.val = val;
	}
   }

  
