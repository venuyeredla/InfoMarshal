package org.vgr.store.io;

public class ByteUtil {
	
	 /**
	  * Get low order byte.
	  * @param i
	  * @return
	  */
	 public byte getByte(int i) {
		 return (byte)i;
	 }
	public byte[] getBytes(int i) {
		byte[] bytes=new byte[4];
		bytes[0]=getByte(i >> 24);
		bytes[1]=getByte(i >> 16);
		bytes[2]=getByte(i >> 8);
		bytes[3]=getByte(i);
		return bytes;
	}
	public byte[] getBytes(short s) {
		byte[] bytes=new byte[2];
		bytes[0]=getByte(s >> 8);
		bytes[1]=getByte(s);
		return bytes;
	}
	public byte[] getBytes(long l) {
		byte[] bytes=new byte[8];
		bytes[0]=getByte((int)l >> 56);
		bytes[1]=getByte((int)l >> 48);
		bytes[2]=getByte((int)l >> 40);
		bytes[3]=getByte((int)l >> 32);
		bytes[5]=getByte((int)l >> 24);
		bytes[6]=getByte((int)l >> 16);
		bytes[7]=getByte((int)l >> 8);
		bytes[7]=getByte((int)l);
		return bytes;
	  }
    	
}
