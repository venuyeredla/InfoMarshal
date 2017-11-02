package org.vgr.store.io;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.List;

public class DataWriter implements Closeable {
 OutputStream os=null;
 RamStorage ramStorage=null;
 boolean isRamStorage=false;
 private int bytesWritten=0;
 
	public DataWriter(OutputStream os) {
		this.os=new BufferedOutputStream(os);
	}
	public DataWriter(RamStorage ramStorage) {
		isRamStorage=true;
		this.ramStorage=ramStorage;
	}
	
	/**
	 * Writes one byte data
	 * @param b
	 * @return
	 */
	public void writeByte(int b) {
		try {
			if(isRamStorage) {
				ramStorage.write(b);
			}else {
				os.write(b);
			}
			bytesWritten++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeInt(int i) {
		writeByte((int)i >> 24 );
		writeByte((int)i >> 16 );
		writeByte((int)i >> 8);
		writeByte((int)i);
	}

	public void writeLong(long pointer) {
		writeByte((int)(pointer >> 56) );
		writeByte((int)pointer >> 48 );
		writeByte((int)pointer >> 40 );
		writeByte((int)pointer >> 32 );
		writeByte((int)pointer >> 24 );
		writeByte((int)pointer >> 16 );
		writeByte((int)pointer >> 8 );
		writeByte((int)pointer);
		
	}

	public void writeShort(int s) {
		writeByte((int)s >> 8 );
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
	
	public void writeMap(LinkedHashMap<String,String> map) {
		writeVint(map.keySet().size());
		map.forEach((key,value)-> {
			writeString(key);
			writeString(value);
		});
	}


	public void writeList(List<String> strings) {
		writeInt(strings.size());
		strings.forEach(str -> writeString(str));
	}
	
	public int getFilePointer() {
		return bytesWritten;
	}
	
	
	String basePath="C:\\Work\\testdata\\io\\";
	String db=basePath+"db\\db.db";
	public void update(byte[] bytes,int offset) {
		 try {
		   Path path=FileSystems.getDefault().getPath(db);
				SeekableByteChannel sbc=Files.newByteChannel(path, StandardOpenOption.WRITE);
				sbc.position(offset);
				ByteBuffer byteBuffer=ByteBuffer.wrap(bytes);
				sbc.write(byteBuffer);
                sbc.close();				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
	}
	
	@Override
	public void close(){
		try {
			if(!isRamStorage) {
				os.flush();
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
