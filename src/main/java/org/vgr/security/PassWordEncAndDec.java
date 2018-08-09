package org.vgr.security;

import org.jasypt.util.text.BasicTextEncryptor;

public class PassWordEncAndDec {
	
	public void decryptPassWord(String pass, String salt) {
		BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
		/*basicTextEncryptor.setPassword("bum4s0uk");
		String pwd = basicTextEncryptor.decrypt("YWDz3iDd0a6uQVwRgoy1hHlqxfyx1TYl");*/
		basicTextEncryptor.setPassword(pass);
		String pwd = basicTextEncryptor.decrypt(salt);
		System.out.println(pwd);;
	 }
	
	public void encryptPassWord(String pass, String salt) {
		BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
	/*	basicTextEncryptor.setPassword("bum4s0uk");
		String encpwd =basicTextEncryptor.encrypt("Blk68buety");*/
		basicTextEncryptor.setPassword(pass);
		String encpwd =basicTextEncryptor.encrypt("pass");
		System.out.println(encpwd);;
	 }

}
