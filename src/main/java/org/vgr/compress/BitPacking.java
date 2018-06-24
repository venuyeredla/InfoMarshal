package org.vgr.compress;

import java.util.Arrays;

import org.vgr.store.io.ByteBuf;
import org.vgr.store.io.IntBuf;

/**
 * Supports only max 5 bit width numbers.
 * @param ints
 */
public class BitPacking {
	 ByteBuf byteBuf=null;
	 IntBuf intBuf=new IntBuf();
     /**
      * Supports only max 5 bit width numbers.
      * @param ints
      */
	 public byte[] compress(int[] ints) {
		 int bitWidth=getBitWidth(ints);
		 if(bitWidth>5) {
			 System.out.println("This implementation can't pack the number with bitwidth >5");
		 }
		 //int maxNum=(1<<bitWidth)-1;
		 System.out.println("Packing numbers with bit width :"+bitWidth);
		 byteBuf=new ByteBuf();
		 byteBuf.writeByte(bitWidth);
		 int size=ints.length;
		 byteBuf.writeVInt(size);//Writes the actual size of ints
		 switch (bitWidth) {
			case 2:
				 int rem2=size % 4;
				 if(rem2!=0) {
					 ints=Arrays.copyOf(ints, size+(4-rem2));
				 }
				 for(int i=0;i<ints.length;i=i+4) {
						 pack2(Arrays.copyOfRange(ints, i, i+4));
				   }
				break;
			case 3:
				int rem3=size % 5;
				 if(rem3!=0) {
					 ints=Arrays.copyOf(ints, size+(5-rem3));
				 }
				for(int i=0;i<ints.length;i=i+5) {
					 pack3(Arrays.copyOfRange(ints, i, i+5));
				 }
				break;
			case 4:
				int rem4=size % 2;
				if (rem4!=0) ints=Arrays.copyOf(ints, size+(2-rem4));
				for(int i=0;i<ints.length;i=i+2) {
					 pack4(Arrays.copyOfRange(ints, i, i+2));
				 }
				break;
			case 5:
				for(int i=0;i<ints.length;i=i+2) {
					 pack2(Arrays.copyOfRange(ints, i, i+1));
				 }
				break;
	
		default:
			break;
		}
		 
		 return byteBuf.getActualBytes();
		}
	 
	 public int[] decompress(byte[] bytes,ByteBuf bBuf) {
		 if(bBuf!=null) {
			 byteBuf=bBuf;
		 }else {
			 byteBuf=new ByteBuf(bytes);
		 }
		 
		 intBuf=new IntBuf();
		 int bitWidth=byteBuf.readByte();
		// int maxNum=(1<<bitWidth)-1;
		 System.out.println("Unpacking numbers with bit width :"+bitWidth);
		 int count=byteBuf.readVInt();
		 switch (bitWidth) {
				 case 2:
					  while(intBuf.size()<count) {
						  unpack2(byteBuf.readByte());
					  }
						break;
					case 3:
						while(intBuf.size()<count) {
							  unpack3(byteBuf.readShort());
						  }
						break;
					case 4:
						while(intBuf.size()<count) {
							  unpack4(byteBuf.readByte());
						  }
		
						break;
					case 5:
						break;
		
				default:
					break;
		 }
		 return Arrays.copyOf(intBuf.getInts(), count);
		 
		}
	
	/**
	 * Packs 4 numbers of width into single byte.
	 * @param nums
	 * @return
	 */
	public byte pack2(int[] nums) {
		byte b=0;
		for(int i=0;i<4;i++) {
			 byte num=(byte) (nums[i] & 3);
			 b=(byte) (b<<2 | num);
		}
		byteBuf.writeByte(b);
		return b;
	 }
	
	/**
	 * unpacks the single byte into 4 numbers of bid width2;
	 */
	public int[] unpack2(int num) {
		int[] nums=new int[4];
		intBuf.write(num >> 6 & 3);
		intBuf.write(num >> 4 & 3);
		intBuf.write(num >> 2 & 3);
		intBuf.write(num & 3);
		return nums;
	}
	
	/**
	 * Packs 2 numbers of  bit width 4 into single byte.
	 * @param nums
	 * @return
	 */
	public int pack3(int[] nums) {
		int n=0;
		for(int i=0;i<5;i++) {
			 int num=nums[i] & 7;
			 n=n<<3 | num;
		}
		byteBuf.write((short)n);
		return n;
	 }
	
	/**
	 * unpacks the single byte into 2 numbers of bid width 4;
	 */
	public int[] unpack3(int num) {
		int[] nums=new int[5];
		intBuf.write(num >> 12 & 7);
		intBuf.write(num >> 9 & 7);
		intBuf.write(num >> 6 & 7);
		intBuf.write(num >> 3 & 7);
		intBuf.write(num & 7);
	    return nums;
	}
	
	
	/**
	 * Packs 2 numbers of  bit width 4 into single byte.
	 * @param nums
	 * @return
	 */
	public byte pack4(int[] nums) {
		byte b=0;
		for(int i=0;i<2;i++) {
			 byte num=(byte) (nums[i] & 15);
			 b=(byte) (b<<4 | num);
		}
		byteBuf.writeByte(b);
		return b;
	 }
	
	/**
	 * unpacks the single byte into 2 numbers of bid width 4;
	 */
	public int[] unpack4(int num) {
		int[] nums=new int[2];
		intBuf.write(num >> 4 & 15);
		intBuf.write(num & 15);
		return nums;
	}
	
	
	
	/**
	 * Packs 2 numbers of  bit width 4 into single byte.
	 * @param nums
	 * @return
	 */
	public int pack5(int[] nums) {
		int n=0;
		for(int i=0;i<3;i++) {
			 int num=nums[i] & 31;
			 n=n<<5 | num;
		}
		return n;
	 }
	
	/**
	 * unpacks the single byte into 2 numbers of bid width 4;
	 */
	public int[] unpack5(int num) {
		int[] nums=new int[3];
		nums[0]=num >> 10 & 31;
		nums[1]=num >> 5 & 31;
		nums[2]=num & 31;
	    return nums;
	}
	
	
	/**
	 * Returns the required number of bits hold the information of max number.
	 * @param ints
	 * @return
	 */
	private int getBitWidth(int[] ints) {
		 int max=0;
		 for(int i=0;i<ints.length;i++) {
			 if(max<ints[i]) {
				 max=ints[i];
			 }
		 }
		for(int j=2;j<31;j++) {
			int num=(1<<j) -1;
			if(max<=num) {
				return j;
			}
		}
		return 0;
	}

}
