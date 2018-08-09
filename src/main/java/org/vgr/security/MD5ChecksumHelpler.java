package org.vgr.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5ChecksumHelpler {
	
	public static void main(String...args) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] dataBytes = new byte[1024];
		String url="http://stackoverflow.com/questions/415953/how-can-i-generate-an-md5-hash";
		dataBytes=url.getBytes();
		byte[] hashBytes = md.digest(dataBytes);
		
		System.out.println("Hash is : "+hashBytes);
		
	}
	

}
