package org.vgr.store.io;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DataReader implements Closeable{
	InputStream is=null;
	RamStorage ramStorage=null;
	boolean isRamStorage=false;
	private long offSet;
	public DataReader(InputStream in) {
		this.is=new BufferedInputStream(in);
		this.is.mark(0);
	  }
	public DataReader(RamStorage ramStorage) {
		isRamStorage=true;
		this.ramStorage=ramStorage; 
     }
	
	public int readByte() {
		try {
		   offSet++;
		   if(isRamStorage) {
			return ramStorage.read();
			}
			int b=is.read();
			return b;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
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
		int b=readByte();
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
			bytes[i]=(byte)readByte();
		}
	}
	public void readBytes(byte[] bytes,int offset, int len) {
		for (int i = offset; i < len; i++) {
			bytes[i]=(byte) readByte();
		}
	}
	public LinkedHashMap<String,String> readMap(){
		LinkedHashMap<String,String> map=new LinkedHashMap<>();
		int size=readVInt();
		for (int i = 0; i < size; i++) {
		map.put(readString(), readString());
		}
		return map;
	}
	
	public List<String> readList() {
		List<String> list=new ArrayList<>();
		int size=readInt();
		for(int i=0;i<size;i++) {
			list.add(readString());
		}
		return list;
	}
	
	public long getPointer() {
		return offSet;
	}
	
	public void seek(int offset) {
		offSet=0;
		try {
			is.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] bytes=new byte[offset];
		this.readBytes(bytes, offset);
		bytes=null;
	}
	@Override
	public void close() {
		try {
			if(!isRamStorage) {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
