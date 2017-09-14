package org.vgr.app.store;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

public class DataWriter implements Closeable {
	
 BufferedOutputStream bufferedOutputStream=null;
 private int bytesWritten=0;
 
	public DataWriter(OutputStream out) {
			bufferedOutputStream=new BufferedOutputStream(out);
	}
	/**
	 * Writes one byte data
	 * @param b
	 * @return
	 */
	public void writeByte(int b) {
		try {
			bufferedOutputStream.write(b);
			bytesWritten++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeInt(int i) {
		writeByte((byte)i >> 24 );
		writeByte((byte)i >> 16 );
		writeByte((byte)i >> 8 );
		writeByte(i);
	}

	public void writeLong(int l) {
		writeByte((byte)l >> 56 );
		writeByte((byte)l >> 48 );
		writeByte((byte)l >> 40 );
		writeByte((byte)l >> 32 );
		writeByte((byte)l >> 24 );
		writeByte((byte)l >> 16 );
		writeByte((byte)l >> 8 );
		writeByte(l);
		
	}

	public void writeShort(int s) {
		writeByte((byte)s >> 8 );
		writeByte(s);
	}

	public void writeString(String str) {
		byte[] bytes= str.getBytes();
		int length=bytes.length;
		writeVint(length);
		writeBytes(bytes, length);
		
	}
	
	public void writeVint(int i) {
		while((i & ~0x7F) !=0 ) {
			writeByte((i&0x7F) | 0x80);
			i>>=7;
		}
		writeByte((byte)i);
	}
	
	public  void writeVLong(int s) {
		writeByte((byte)s >> 8 );
		writeByte(s);
	}
	
	public void writeBytes(byte[] bytes, int len) {
		for (int i = 0; i < len; i++) {
			writeByte(bytes[i]);
		}
	}
	
	public void writeBytes(byte[] bytes, int offset, int len) {
		for (int i = offset; i < len; i++) {
			writeByte(bytes[i]);
		}
	}
	
	public void writeMap(Map<String,String> map) {
		writeVint(map.keySet().size());
		map.forEach((key,value)-> {
			writeString(key);
			writeString(value);
		});
	}

	public void writeSet(Set<String> strings) {
		strings.forEach(str -> writeString(str));
	}
	
	public int getFilePointer() {
		return bytesWritten;
	}
	@Override
	public void close() throws IOException {
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
	}
	
}
