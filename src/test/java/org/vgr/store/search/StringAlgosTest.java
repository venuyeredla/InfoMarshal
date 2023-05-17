package org.vgr.store.search;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StringAlgosTest {
	

	StringAlgos algos=new StringAlgos();
	
	@Test
	public void testReverse() {
		String[] inputs=new String[]{null,"","v", "ve","venugopal"};
		String[] outputs=new String[]{null,"","v","ev","lapogunev"};
		for(int i=0;i<inputs.length;i++) {
			String reverse = algos.reverse(inputs[i]);
			Assert.assertEquals(outputs[i], reverse);
			if(null!=inputs[i]) {
				reverse =algos.reverse(inputs[i],0,inputs[i].length()-1);
				Assert.assertEquals(outputs[i], reverse);
			}
			
		}
	}
	
	/*@Test
	public void tetsCommonPrefix() {
	//	String[] inputWords=new String[] {"geeksforgeeks","geeks","geek","geezer"};
		String[] args=new String[]{"flower","flow","flight","fight",};
	    String actual=algos.longestCommonPrefix(args,0);
		Assert.assertEquals("f", actual);
	}
	*/
	
	@Test
	public void tetsHasUnique() {
		String input="venugopal";
	    boolean actual=algos.hasUniqueChars(input,2);
		Assert.assertEquals(true, actual);
	}
	
	@Test
	public void testPalidrome() {
		String in="venunev";
		boolean isPalindrome=algos.isPalidrome(in,0,in.length()-1);
		Assert.assertEquals(true, isPalindrome);
	}
	
	
	@Test
	public void testStrToNum() {
		String[] inputs=new String[]{"123","-123"};
		int[] expected=new int[] {123,-123};
		for(int i=0;i<inputs.length;i++) {
			  int result=algos.strToNum(inputs[i]);
				Assert.assertEquals(expected[i], result);
		}
	}
	
	@Test
	public void testNumToStr() {
		int[] inputs=new int[] {0,1,123,-123};
		String[] expected=new String[]{"0","1","123","-123"};
		for(int i=0;i<inputs.length;i++) {
			  String result=algos.numToStr(inputs[i]);
			  Assert.assertEquals(expected[i], result);
		}
	}
	
	/*
	
	@Test
	public void testStringPerm() {
		String str="venu";
		List<String> collector=new ArrayList<>();
		algos.permutations(str,0,str.length(),collector);
		System.out.println("Total length ="+collector.size());
		collector.forEach(System.out::println);
	}
	
	@Test
	public void testReverseSent4ence() {
		algos.reverseWords("i.like.this.program.very.much");
		algos.reverseWords("pq.mno");
    }
	 */

	@Test
	public void testRoman() {
	 String[] inputs=new String[]{"IV","VI","VIII","XXVIII","XLVIII","XCIX"};
	 int[] expected=new int[] {4,6,8,28,48,99};
		for(int i=0;i<inputs.length;i++) {
			  int integer=	algos.romanToDecimal(inputs[i]);
			  System.out.println(inputs[i] +" -> "+ integer);
			  Assert.assertEquals(expected[i], integer);
		}
	  
	}
	
	@Test
	public void testIsValidIp() {
		String[] ipAddresses=new String[]{null,"","192.168.1.1","192.168.1.1.123","192.168.1.256",};
		for(String ip:ipAddresses) {
		  boolean isValid=algos.validIp(ip);
		  if(!isValid) {
			  System.out.println("Inavlid :"+ip);
		  }
		}
    }
	
	@Test
	public void testMakeIp() {
		String[] ipAddresses=new String[]{null,"","192","19216811","19216811123",};
		for(String ip:ipAddresses) {
		  String isValid=algos.makeIP(ip);
			  System.out.println("Inavlid :"+isValid);
		}
    }
	
}
