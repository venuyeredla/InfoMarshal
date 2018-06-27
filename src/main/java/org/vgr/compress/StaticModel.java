package org.vgr.compress;

import java.util.Arrays;

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
		    		System.out.print(i+"-"+freqs[i]+" , ");
		    	}
		    }
		    
		   // writeFreqs();
	  }
	  public int[] writeFreqs(ByteBuf byteBuf) {
		  int keys[]=new int[keyValues.length];
		   int i=0;
		   for (KeyVal kv : keyValues) {
			   keys[i]=kv.key;
			   i++;
		    }
		   IntegerCompressor integerCompressor=new IntegerCompressor();
		   byte[] keysCompressed=integerCompressor.compress(keys, INT_MODE.DELTA_BITPACKING);
		   byteBuf.writeByte(0);
		   byteBuf.write(keysCompressed);
		   int valsPointer=byteBuf.size();
		   byteBuf.setByte(0, (byte)valsPointer);
		   System.out.println("Size of byteBuf :"+valsPointer);
		   System.out.println("Writing values : ");
		   for (KeyVal kv : keyValues) {
				  byteBuf.writeVInt(kv.val);
			}
		   System.out.println("Freqs size: "+byteBuf.size());
		   
		   return keys;
	   }
	  
	  public int[] readFreqs(ByteBuf byteBuf) {
		  IntegerCompressor integerCompressor=new IntegerCompressor();
		  int valsPointer=byteBuf.readByte();
		  byte[] bytes=byteBuf.getActualBytes();
		  int[] keys=integerCompressor.decompress(Arrays.copyOfRange(bytes, 1, valsPointer),INT_MODE.DELTA_BITPACKING);
		  byteBuf.setReadPos(valsPointer-1);
		  keyValues=new KeyVal[keys.length];
		  for(int i=0;i<keys.length;i++) {
			  KeyVal keyVal=new KeyVal(keys[i], byteBuf.readVInt()); 
			  keyValues[i]=keyVal;
			  System.out.print(keyVal.key+"-"+keyVal.val+" , ");
		  }
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

  
