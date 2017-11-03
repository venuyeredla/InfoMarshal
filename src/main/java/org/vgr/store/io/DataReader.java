package org.vgr.store.io;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DataReader implements Closeable{
	InputStream is=null;
	String fileName;
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
	
	public int readByte() {
		try {
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
	
	public Block readBlock() {
		try {
			byte[] bytes=new byte[512];
			int count=this.is.read(bytes);
			System.out.println("No of bytes read :"+count);
			return new Block(bytes);
		 } catch (IOException e) {
			e.printStackTrace();
		 }
		return null;
	}
	

	
	
/*	public byte[] readBlock(int blockNum ) {
		try {
			byte[] bytes=new byte[512];
			this.is.read(bytes);
		 } catch (IOException e) {
			e.printStackTrace();
		 }
		return null;
	}*/
	
	public void seek(int offset) {
		try {
			is.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
}
