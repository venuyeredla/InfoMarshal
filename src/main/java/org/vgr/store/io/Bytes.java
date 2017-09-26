package org.vgr.store.io;

public class Bytes {
	private byte[] bytes;
	private int pointer;
	
	public Bytes() {
		bytes=new byte[25];
	}
	public Bytes(int size) {
		bytes=new byte[size];
	}
	public byte[] getBytes() {
		return bytes;
	}

}
