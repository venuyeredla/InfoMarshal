package org.vgr.store.io;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Wraps byte array and default size is 512 bytes equivalent to HDD block size.
 * 
 */
public class Block {
	public static int BLOCK_SIZE=512;
	private byte[] bytes;
	private int wPos;
	private int rPos;
	public Block() {
		bytes=new byte[BLOCK_SIZE];
		wPos=0;//points to the next position to be inserted.
		rPos=-1;
	}
	public Block(int size) {
		BLOCK_SIZE=size;
		bytes=new byte[BLOCK_SIZE];
		rPos=-1;
	}
	public Block(byte[] b) {
		bytes=new byte[b.length];
		for(int i=0;i<b.length;i++) 
			bytes[wPos++]=b[i];
		wPos=b.length;
		rPos=-1;
	  }
	
	public void write(byte b) {
		bytes[wPos++]=b;
	}
	private void writeByte(byte b) {
		if(wPos+1>=BLOCK_SIZE) {
			System.out.println("Block is full: Block size:"+bytes.length+ " Position to write : "+wPos+1);
		}
		bytes[wPos++]=b;
	}
	
	 /**
	 * returns signed byte.
	 * @return
	 */
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
	 * Reads one byte and returns as short. other wise last bit considered as negative number.
	 * @return
	 */
	private short read() {
		 try {
		   if(++rPos>=wPos) throw new OutOfRangException("There are no bytes at postion:"+rPos);{
			   byte b=bytes[rPos];
			   return (b&0x80)==0x80?(short)(b & 0xff):b;
		   }
		 } catch (OutOfRangException e) {
			e.printStackTrace();
			System.exit(1);
		  }
        return -1;
	   }
	
	public void write(short s) {
		writeByte((byte)(s >> 8)); 
		writeByte((byte)s);
	}
	public short readShort() {
		return  (short)((read() << 8)|(read()));
	 }
	
	public void write(int i) {
		writeByte((byte)(i >> 24));	
		writeByte((byte)(i >> 16));
		writeByte((byte)(i >> 8));
		writeByte((byte)(i));
	 }
	public int readInt() {
		return (read() << 24) | (read() << 16) |(read() << 8)|(read());
	 }
	public void writeVInt(int i) {
		while((i & ~0x7F) !=0 ) {
			writeByte((byte)((i&0x7F) | 0x80));
			i>>=7;
		}
		writeByte((byte)i);
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
		writeByte((byte)(l >> 56));	writeByte((byte)(l >> 48));	writeByte((byte)(l >> 40)); writeByte((byte)(l >> 32));
		writeByte((byte)(l >> 24));	writeByte((byte)(l >> 16));	writeByte((byte)(l >> 8)); writeByte((byte)(l));
	  }
	public long readLong() {
		 return (read() << 56) | (read() << 48) |(read() << 40)|(read() << 32)|
				(read() << 24) | (read() << 16) |(read() << 8)|(read());
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
		write(strings.size());
		strings.forEach(str -> write(str));
	}
	
	public static int getBlockSize() {
		return BLOCK_SIZE;
	}
	
}

class OutOfRangException extends Exception{
	private static final long serialVersionUID = 1L;
	public OutOfRangException(String message) {
		super(message);
	}
}


