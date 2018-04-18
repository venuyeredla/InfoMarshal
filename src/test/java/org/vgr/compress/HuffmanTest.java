package org.vgr.compress;

import org.junit.Ignore;
import org.junit.Test;

public class HuffmanTest {
    @Test
	public void testCompress() {
		HuffManCoding huffManCoding=new HuffManCoding();
		String actual="aaaabbbccccddddddddddeeeeeeeffffffiiiiiiiiiiiiiiiiiasdlfkasdfsadfsadfasfdsafdaaasdfasfsafsadfsafdsafdffaaaabbbccccddddddddddeeeeeeeffffffiiiiiiiiiiiiiiiiiasdlfkasdfsadfsadfasfdsafdaaasdfasfsafsadfsafdsafdffaaaabbbccccddddddddddeeeeeeeffffffiiiiiiiiiiiiiiiiiasdlfkasdfsadfsadfasfdsafdaaasdfasfsafsadfsafdsafdff"
				+ "aaaabbbccccddddddddddeeeeeeeffffffiiiiiiiiiiiiiiiiiasdlfkasdfsadfsadfasfdsafdaaasdfasfsafsadfsafdsafdffaaaabbbccccddddddddddeeeeeeeffffffiiiiiiiiiiiiiiiiiasdlfkasdfsadfsadfasfdsafdaaasdfasfsafsadfsafdsafdffaaaabbbccccddddddddddeeeeeeeffffffiiiiiiiiiiiiiiiiiasdlfkasdfsadfsadfasfdsafdaaasdfasfsafsadfsafdsafdff";
		
		byte[] bytes=huffManCoding.compress(actual);
		String decompressed=huffManCoding.decompressToTxt(bytes);
		System.out.println("\nActual       ="+actual);
		System.out.println("Decompressed ="+decompressed);
	 }

	@Test
	@Ignore
	public void testDeCompress() {
		HuffManCoding huffManCoding=new HuffManCoding();
		String str="aaaabbbccccdeeeeeeeffffffiiiiiiii";
		huffManCoding.compress(str);
	}
	@Test
	@Ignore
	public void testbitStrToBytes() {
		//String bitString="1011101110111011101010101010010010010010000000000000000000001001001001001001001000110110110110110111111111111111111111111111111111111";
		String bitString="000001000001";
		BitUtil.bitStringTobytes(bitString);
	}
	
	
	
}
