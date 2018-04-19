package org.vgr.compress;

public interface Compressor {
	public byte[] compress(String txt);
	public byte[] compress(byte[] bytes);
	public byte[] decompress(String txt);
	public byte[] decompress(byte[] bytes);
	String decompressToTxt(byte[] compressed);
}
