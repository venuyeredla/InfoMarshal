package org.vgr.crypt;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Ignore;
import org.junit.Test;
import org.vgr.security.AESUtils;
import org.vgr.security.HashUtils;
import org.vgr.security.HashUtils.HashAlgorithm;
import org.vgr.security.HashUtils.HashEncoding;

public class AESCryptTest {

/*	private static AESUtils aesUtils=null;
	
	@BeforeClass
	public static void init() {
		aesUtils=new AESUtils();
	}*/
	
	@Test
	@Ignore
	public void testEncryption() {
	  String cipherText=AESUtils.encrypt("Venugopal Reddy Working in qualcomm");
	  System.out.println("Plain text: "+"Venugopal Reddy Working in qualcomm");
	  System.out.println("Cipher text: "+cipherText);
	  System.out.println("Decrypted text: "+AESUtils.decrypt(cipherText));
	}
	
	@Test
	@Ignore
	public void testAESKeyGen() {
		String hash=HashUtils.generateHash(HashAlgorithm.SHA1, "Venugopal Reddy Working in qualcomm", HashEncoding.BASE64);
		System.out.println("Hash : " +hash);
	 }
	
	@Test
	public void testKeyPair() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
      //String[] keyPair= KeyUtils.generateKeyPair(KeyPairAlgorithm.RSA, 512);
      //System.out.println("RSA-Public key: "+keyPair[0]);
		String input="Venugopal Reddy Working in qualcomm";
	 KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
     keyGen.initialize(512);
     KeyPair keypair = keyGen.genKeyPair();
     PrivateKey privateKey = keypair.getPrivate();
     PublicKey publicKey = keypair.getPublic();
     Cipher enCipher=Cipher.getInstance("RSA/ECB/PKCS1Padding");  //
     enCipher.init(Cipher.ENCRYPT_MODE, publicKey);
     byte[] cipher=enCipher.doFinal(input.getBytes());
     String cipherText= Base64.getEncoder().encodeToString(cipher) ;
     System.out.println("Cipher text:"+cipherText);
     enCipher.init(Cipher.DECRYPT_MODE, privateKey);
     byte[] actual=enCipher.doFinal(cipher);
     System.out.println("ABCE : "+new String(actual));
      
	}
	
	
}	
