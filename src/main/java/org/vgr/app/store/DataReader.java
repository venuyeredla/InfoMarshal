package org.vgr.app.store;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DataReader implements Closeable{
	
	BufferedInputStream bufferedInputStream=null;
	private long pointerPosition;

	 public DataReader(InputStream in) {
			bufferedInputStream=new BufferedInputStream(in); 
	  }
	public byte readByte() {
		try {
			pointerPosition++;
			return (byte)bufferedInputStream.read();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int readInt() {
		return (readByte() << 24) | (readByte() << 16) |(readByte() << 8)|(readByte());
	 }
	
	public long readLong() {
		 return (readByte() << 56) | (readByte() << 48) |(readByte() << 40)|(readByte() << 32)|
				(readByte() << 24) | (readByte() << 16) |(readByte() << 8)|(readByte());
	}
	
	public short readShort() {
		return (short) ((readByte() << 8)|(readByte()));
	}
	
	public String readString() {
		 int len=readVInt();
		 byte[] bytes=new byte[len];
		 readBytes(bytes, len);
		 String str=new String(bytes);
		 return str;
	}
	
	public int readVInt() {
		byte b=readByte();
		if(b>0) return b;  // it means like it is less than < 127 and it is one bit integer. i.e last bit is used for sign in byte
		int i=b & 0x7F;
		b=readByte();
		i=i | (b & 0x7F)<<7;
	    if(b>0) return i;
	    b=readByte();
		i=i | (b & 0x7F)<<14;
		if(b>0) return i;
		 b=readByte();
		i=i | (b & 0x7F)<<21;  // 28 bits were read. 4 bits need to be read to yet
		if(b>0) return i;
				
		b=readByte();
		 i=i | (b & 0x0F)<<28;
		if(b>0) return i;
		if ((b & 0xF0) == 0) return i;
		        		
		return 0;
	}
	
	public long readVLong() {
		
		return 0;
	}
	
	public void readBytes(byte[] bytes,int len) {
		for (int i = 0; i < len; i++) {
			bytes[i]=readByte();
		}
	}
	public void readBytes(byte[] bytes,int offset, int len) {
		for (int i = offset; i < len; i++) {
			bytes[i]=readByte();
		}
	}
	public Map<String,String> readMap(){
		Map<String,String> map=new HashMap<>();
		int size=readVInt();
		for (int i = 0; i < size; i++) {
		map.put(readString(), readString());
		}
		return map;
	}

	public long getFilePointer() {
		return pointerPosition;
	}
	
	public void seek(int offset) {
		pointerPosition=offset;
		byte[] bytes=new byte[offset];
		this.readBytes(bytes, offset);
		bytes=null;
	}
	@Override
	public void close() throws IOException {
		bufferedInputStream.close();
	}
}
