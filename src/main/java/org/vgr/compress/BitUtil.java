package org.vgr.compress;

public class BitUtil {
	
	/**
	 * Convert bit string to bytes
	 * @param bitString
	 * @return
	 */
    public static byte[] bitStringTobytes(String bitString) {
    	 int len=bitString.length();
    	 int bytesRequired=(int) Math.ceil(len/8.0);
    	 byte[] bytes=new byte[bytesRequired];
    	 int i=-1;
    	 while(bitString.length()>7) {
    		  String byteStr=bitString.substring(0, 8);
    		  int num=Integer.parseInt(byteStr, 2);
    		  bytes[++i]=(byte)num;
    		  bitString=bitString.substring(8);
    	  }
    	 int currentLength=bitString.length();
    	 for(int j=0;j<8-currentLength;j++) {
    		 bitString=bitString+"0";
    	  }
    	 int num=Integer.parseInt(bitString, 2);
		 bytes[++i]=(byte)num;;
    	 return bytes;
    }

    /**
	 * Convert bit string to bytes
	 * @param bitString
	 * @return
	 */
    public static String bytesToBitString(byte[] bytes) {
    	 StringBuffer strBuffer=new StringBuffer();
    	 for(int i=0;i<bytes.length;i++) {
    		 byte b=bytes[i];
    		 String str = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    		 strBuffer.append(str);
    	  }
    	return new String(strBuffer);
     }
}
