package org.vgr.compress;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapper over ByteBuf
 * @author vyeredla
 *
 */
public class BitStream {
	private static final Logger LOG=LoggerFactory.getLogger(BitStream.class);
	private static final int maxSize=4098;
	private byte[] bytes;
	private int wPos;
	private int rPos;
	private int bitBuf;
	private short widx;
	private short ridx;
	private int initCapacity=512;
	private int capacity;
	
	public BitStream() {
		capacity=initCapacity;
		bytes=new byte[capacity];
		wPos=0;//points to the next position to be inserted.
		rPos=-1;
	}
	
	public BitStream(byte[] b) {
		bytes=new byte[b.length];
		for(int i=0;i<b.length;i++) 
			bytes[wPos++]=b[i];
		wPos=b.length;
		rPos=-1;
	  }
	
     public void grow() {
    	 if(widx>=maxSize) {
    		 bytes=new byte[initCapacity];
    	 }else {
    		 capacity=capacity+512;
        	 int old=this.bytes.length;
        	 LOG.debug("Size will be increased");
        	 byte[] newArr=Arrays.copyOf(bytes, capacity);
        	 this.bytes=newArr;
        	 LOG.debug("byte array resized from - to :"+old+" - "+this.bytes.length);
    	 }
    	
     }
	 public void writeByte(int b) {
		if(wPos+1>=capacity) {
			this.grow();
		 }
		 bytes[wPos++]=(byte)b;
	  }
	
	 private short read() {
		 try {
		   if(++rPos>=wPos) throw new ArrayIndexOutOfBoundsException("There are no bytes at postion:"+rPos);
		   else{
			   byte b=bytes[rPos];
			   return (b&0x80)==0x80?(short)(b & 0xff):b;
		   }
		 } catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.exit(1);
		  }
        return -1;
	   }
	 
	
	public void writeBit(int bit) {
    	int actualBit=bit &1;
    	this.bitBuf=this.bitBuf<<1 | actualBit;
    	this.widx +=1;
    	if(this.widx==8) {
    		this.writeByte(this.bitBuf);
    		this.bitBuf=0;
    		this.widx=0;
    	}
    }
    
    /**
     * Flushes unwritten bits
     */
    public void flushBits() {
    	int toBe=8-this.widx;
    	for(int i=0;i<toBe;i++) {
    		this.writeBit(0);
    	 }
    }
    
    public int readBit() {
    	if(this.ridx==0) {
    		this.bitBuf=this.read();
    		this.ridx=8;
    	}
    	int bit=(this.bitBuf >> (this.ridx-1)) & 1;
    	ridx -=1;
    	return bit;
    }
    
	public int size() {
		return wPos;
	}
	
	public byte[] getActualBytes() {
		return Arrays.copyOf(bytes, wPos-1);
	}
}
