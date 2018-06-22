package org.vgr.compress;

import org.junit.Ignore;
import org.junit.Test;

public class UniformABS {
	
	@Test
	public void testUAbs() {
		String bitStr= "110101";
		String rev=new StringBuilder(bitStr).reverse().toString();
		//encoding
		int encoded=rev.chars().map(charint -> charint-48).reduce(1,(x,y)->x<<1|y);
		System.out.println("Encoded:"+bitStr+" = "+encoded);
        // Decoding
		System.out.print("Decoded:");
        while(encoded>1) {
    	  int bit=encoded & 1;
    	  System.out.print(bit);
    	  encoded=encoded>>1;
         }
		/*double p1 = 0.66;
		double p2 = 0.33;
		System.out.println("\nP1 =" + p1 + " P2=" + p2);
		// ABS encoding
		double x2 = 1;
		for (int i = 0; i < bitStr.length(); i++) {
			int bit = bitStr.charAt(i) == one ? 1 : 0;
			System.out.print(bit);
			if (bit == 0) {
				x2 = Math.ceil((x2 + 1) / p2);
			} else if (bit == 1) {
				x2 = Math.floor(x2 / p1);
			}
			//
		}
		System.out.println(" Encoded =" + x2);
*/
		/*
		 * Decoding:
		 * 
		 * s = ceil((x+1)*p) - ceil(x*p) // 0 if fract(x*p) < 1-p, else 1 if s = 0 then
		 * new_x = x - ceil(x*p) // D(x) = (new_x, 0) if s = 1 then new_x = ceil(x*p) //
		 * D(x) = (new_x, 1)
		 * 
		 */
		/*while (x2 >= 1) {
			int bit = (int) Math.ceil((float) (x2 + 1) * p1) - (int) Math.ceil((float) x2 * p1);
			System.out.print(bit);
			double div = x2 * p1;
			if (bit == 1) {
				x2 = Math.ceil(div);
			} else if (bit == 0) {
				x2 = x2 - Math.ceil(div);
			}
			// System.out.println("->"+x2);
		}*/
	}
	
	@Test
	@Ignore
	public void mathTest() {
		double val=4/6.0;
		System.out.println(val);
	}
	

}


/*






Encoding:









if s = 0 then new_x = ceil((x+1)/(1-p)) - 1 // C(x,0) = new_x
if s = 1 then new_x = floor(x/p)  // C(x,1) = new_x

*/