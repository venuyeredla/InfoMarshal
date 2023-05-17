package org.vgr.util;

import java.security.SecureRandom;
import java.util.Random;

import org.jasypt.util.text.BasicTextEncryptor;

public class SecureTest {
	 private static final Random RANDOM = new SecureRandom();
	    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	    /**
	     * This method can be used to generate Random Key
	     * @param length
	     * @return
	     */
	    public static String getSalt(int length) {
	        StringBuilder returnValue = new StringBuilder(length);
	        for (int i = 0; i < length; i++) {
	            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
	        }
	        return new String(returnValue);
	    }

	    /**
	     * Encrypts the given password with given key.
	     * @param encryptionKey
	     * @param password
	     * @return
	     */
	    public String encryptPasswordtWithKey(String encryptionKey, String password){
	        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
	        textEncryptor.setPassword(encryptionKey);
	        String myEncryptedText = textEncryptor.encrypt(password);
	        return myEncryptedText;
	    }

	    /**
	     * Decrypts the given encrypted password with given key
	     * @param encryptionKey
	     * @param encryptedPassword
	     * @return
	     */
	    public String decryptPasswordWithKey(String encryptionKey, String encryptedPassword){
	        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
	        textEncryptor.setPassword(encryptionKey);
	        String acutalPassord = textEncryptor.decrypt(encryptedPassword);
	        return acutalPassord;
	    }
	    
	    
	  /*   @Test
	    public void testEncryptDecrypt(){
	        PasswordEncryptDecrypt passwordEncryptDecrypt=new PasswordEncryptDecrypt();
	        String encryptionKey = "axyzj";
	        String actualPassword="venugopal";
	        String encryptedPassword = passwordEncryptDecrypt.encryptPasswordtWithKey(encryptionKey, actualPassword);
	        System.out.println("Encrypted = "+encryptedPassword);
	        String decryptedPassword = passwordEncryptDecrypt.decryptPasswordWithKey(encryptionKey, encryptedPassword);
	        System.out.println("Decrypted = "+decryptedPassword);
	        Assert.assertEquals(actualPassword,decryptedPassword);
	    } */

}
