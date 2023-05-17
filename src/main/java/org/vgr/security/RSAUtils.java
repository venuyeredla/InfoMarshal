package org.vgr.security;

/**
 * Utility class to encrypt using RSA Public Key and decrypt using RSA private key
 */
public class RSAUtils {
    private static final String ALGORITHM = "RSA";

    /**
     *
     * @param publicKey
     * @param plainText
     * @return
     */
    public static String encrypt(String publicKey, String plainText) {
      /*  if (StringUtils.isBlank(publicKey) || StringUtils.isBlank(plainText)) {
            return StringUtils.EMPTY;
        } else {
            try {
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);
                PublicKey key = KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(keyBytes));
                cipher.init(Cipher.ENCRYPT_MODE, key);
                return new BASE64Encoder().encode(cipher.doFinal(plainText.getBytes()));
            } catch (GeneralSecurityException e) {
                throw new CryptoException(e);
            } catch (IOException e) {
                throw new CryptoException(e);
            }
        }
        */ return "";
    }

    /**
     *
     * @param privateKey
     * @param encryptedText
     * @return
     */
    public static String decrypt(String privateKey, String encryptedText) {
		/*
		 * if (StringUtils.isBlank(privateKey) || StringUtils.isBlank(encryptedText)) {
		 * return StringUtils.EMPTY; } else { try { Cipher cipher =
		 * Cipher.getInstance(ALGORITHM); byte[] keyBytes = new
		 * BASE64Decoder().decodeBuffer(privateKey); PrivateKey key =
		 * KeyFactory.getInstance(ALGORITHM).generatePrivate(new
		 * PKCS8EncodedKeySpec(keyBytes)); cipher.init(Cipher.DECRYPT_MODE, key); return
		 * new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(encryptedText)));
		 * } catch (GeneralSecurityException e) { throw new CryptoException(e); } catch
		 * (IOException e) { throw new CryptoException(e); } }
		 */    
    	 return "";}
}
