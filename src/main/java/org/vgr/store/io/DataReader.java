package org.vgr.store.io;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DataReader implements Closeable{
	InputStream is=null;
	private String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	RamStorage ramStorage=null;
	boolean isRamStorage=false;
	
    public DataReader(String fileName) {
			try {
				this.fileName=fileName;
				this.is=new BufferedInputStream(new FileInputStream(fileName));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	  }
	public DataReader(InputStream in) {
		this.is=new BufferedInputStream(in);
		this.is.mark(0);
	  }
	public DataReader(RamStorage ramStorage) {
		isRamStorage=true;
		this.ramStorage=ramStorage; 
     }
	public Block readBytes(int size) {
		try {
			byte[] bytes=new byte[size];
			int count=this.is.read(bytes);
			System.out.println("No of bytes read :"+count);
			return new Block(bytes);
		 } catch (IOException e) {
			e.printStackTrace();
		 }
		return null;
	}
	
	public Block readBlockOld(int offset) {
		try {
			byte[] bytes=new byte[Block.BLOCK_SIZE];
			int count=this.is.read(bytes);
			System.out.println("No of bytes read :"+count);
			return new Block(bytes);
		 } catch (IOException e) {
			e.printStackTrace();
		 }
		return null;
	}
	
	
	public Block readBlock(int offset) {
		try {
		    Path path=FileSystems.getDefault().getPath(fileName);
			SeekableByteChannel sbc=Files.newByteChannel(path, StandardOpenOption.READ);
			sbc.position(offset);
		    ByteBuffer byteBuffer=ByteBuffer.allocate(Block.BLOCK_SIZE);
		    int  noOfbytes=sbc.read(byteBuffer);
			byte[] bytes= byteBuffer.array();
			System.out.println("Read BufferSize : "+noOfbytes);
		    sbc.close();	
			return new Block(bytes);
		 } catch (IOException e) {
			e.printStackTrace();
		 }
		return null;
	}
	
	public void seek(int offset) {
		try {
			is.reset();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	public InputStream getIs() {
		return is;
	}
	public void setIs(InputStream is) {
		this.is = is;
	}
	
	
}
