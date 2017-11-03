package org.vgr.store.io;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Wraps byte array and default size is 512 bytes equivalent to HDD block size.
 */
public class Block {
	private byte[] bytes;
	private int wPos;
	private int rPos;
	public Block() {
		bytes=new byte[512];
		wPos=0;//points to the next position to be inserted.
		rPos=-1;
	}
	public Block(int size) {
		bytes=new byte[size];
		rPos=-1;
	}
	public Block(byte[] b) {
		bytes=new byte[b.length];
		for(int i=0;i<b.length;i++) {
			bytes[wPos++]=b[i];
		}
		wPos=b.length;
		rPos=-1;
	  }
	
	public void write(byte b) {
		bytes[wPos++]=b;
	}
	public byte readByte() {
		 try {
		   if(++rPos>=wPos) throw new OutOfRangException("There are no bytes at postion:"+rPos);
		   return bytes[rPos];
		} catch (OutOfRangException e) {
			e.printStackTrace();
			System.exit(1);
		  }
        return -1;
	   }
	
	/**
	 * writes lower order 8-bits(1 byte).Used internal purpose only
	 * @param i
	 */
	private void writeInt(int i) {
		bytes[wPos++]=(byte)i;
	}
	
	public void write(short s) {
		writeInt(s >> 8); write(s);
	}
	public short readShort() {
		return (short) ((readByte() << 8)|(readByte()));
	 }
	
	public void write(int i) {
		writeInt(i >> 24);	writeInt(i >> 16); writeInt(i >> 8); writeInt(i);
	 }
	public int readInt() {
		return (readByte() << 24) | (readByte() << 16) |(readByte() << 8)|(readByte());
	 }
	public void writeVInt(int i) {
		while((i & ~0x7F) !=0 ) {
			writeInt((i&0x7F) | 0x80);
			i>>=7;
		}
		write((byte)i);
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
	
	public void writeLong(long l) {
		writeInt((int)l >> 56);	writeInt((int)l >> 48);	writeInt((int)l >> 40); writeInt((int)l >> 32);
		writeInt((int)l >> 24);	writeInt((int)l >> 16);	writeInt((int)l >> 8); writeInt((int)l);
	  }
	public long readLong() {
		 return (readByte() << 56) | (readByte() << 48) |(readByte() << 40)|(readByte() << 32)|
				(readByte() << 24) | (readByte() << 16) |(readByte() << 8)|(readByte());
	}
	
	public void write(byte[] b) {
		for(int i=0;i<b.length;i++) {
			bytes[wPos++]=b[i];
		}
	  }
	
	public byte[] readBytes(int len) {
		 byte[] b=new byte[len];
		for (int i = 0; i < len; i++) {
			b[i]=readByte();
		}
		return b;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	/**
	 * Adds the string bytes length as Vint and string bytes
	 * @param str
	 */
	public void write(String str) {
		writeVInt(str.getBytes().length);
		this.write(str.getBytes());
	}
	
	public String readString() {
		 return new String(readBytes(readVInt()));
	}
	
	public void write(LinkedHashMap<String,String> map) {
		writeVInt(map.keySet().size());
		map.forEach((key,value)-> {
			write(key);
			write(value);
		});
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
	
	public void writeList(List<String> strings) {
		writeInt(strings.size());
		strings.forEach(str -> write(str));
	}
}

class OutOfRangException extends Exception{
	private static final long serialVersionUID = 1L;
	public OutOfRangException(String message) {
		super(message);
	}
}


