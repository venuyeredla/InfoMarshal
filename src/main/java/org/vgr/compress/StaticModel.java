package org.vgr.compress;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Iterates all the file and counts symbol frequencies. 
 * @author vyeredla
 *
 */
public class StaticModel {
	  public StaticModel() {
		  
	   }
	   public void calculateFreq(byte[] bytes) {
	        List<Byte> byteList = new ArrayList<>();
			for(int i = 0; i < bytes.length; i++) byteList.add(bytes[i]);
		    Map<Byte, Long> charFreq = byteList.stream().collect(Collectors.groupingBy(b -> b, Collectors.counting()));
	  }
}

/**
 * Stores the frequencies in sorted order
 * @author vyeredla
 *
 */
class KeyVal{
	int key;
	int val;
}