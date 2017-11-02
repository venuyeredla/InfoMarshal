package org.vgr.store.io;

public class Bytes {
	private byte[] bytes;
	private int pointer;
	public Bytes() {
		bytes=new byte[512];
		pointer=0;//points to the position to be inserted.
	}
	public Bytes(int size) {
		bytes=new byte[size];
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void add(byte b) {
			bytes[pointer++]=b;
	}
	public void add(byte[] b) {
		for(int i=0;i<b.length;i++) {
			bytes[pointer++]=b[0];
		}
	}
}
