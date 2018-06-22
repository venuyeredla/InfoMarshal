package org.vgr.compress;

import org.vgr.store.io.ByteBuf;

/**
 * Wrapper over ByteBuf
 * @author vyeredla
 *
 */
public class BitStream {
	
	private ByteBuf byteBuf=null;
	private int bitBuf;
	private short wbitCount;
	private short rbitCount;

	public BitStream() {
		byteBuf=new ByteBuf();
	}
	
	public void writeBit(int bit) {
    	int actualBit=bit &1;
    	this.bitBuf=this.bitBuf<<1 | actualBit;
    	this.wbitCount +=1;
    	if(this.wbitCount==8) {
    		byteBuf.writeByte(this.bitBuf);
    		this.bitBuf=0;
    		this.wbitCount=0;
    	}
    }
    
    /**
     * Flushes unwritten bits
     */
    public void flushBits() {
    	int toBe=8-this.wbitCount;
    	for(int i=0;i<toBe;i++) {
    		this.writeBit(0);
    	 }
    }
    
    public int readBit() {
    	if(this.rbitCount==0) {
    		this.bitBuf=byteBuf.readByte();
    		this.rbitCount=8;
    	}
    	int bit=(this.bitBuf >> (this.rbitCount-1)) & 1;
    	rbitCount -=1;
    	return bit;
    }


}
