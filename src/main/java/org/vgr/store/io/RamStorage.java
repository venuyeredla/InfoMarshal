package org.vgr.store.io;

public class RamStorage{
	 private int initSize=1000;
	 private int maxSize=100000;
	 private byte[] store;
	 private int outPointer=0;
	 private int inPointer=0;
	 
	 public RamStorage() {
		  store=new byte[initSize];
	  }
	  public RamStorage(int newSize) {
		  store=new byte[newSize];
	  }
	  public void write(int b){
		  store[outPointer++]=(byte)b;
		}
	  
	  public int read() {
		  byte b=store[inPointer++];
		     if((b&0x80)==0x80) {
		    	 int i= b & 0xff;
		    	 return i;
		     }
		  return b;
		}
}
