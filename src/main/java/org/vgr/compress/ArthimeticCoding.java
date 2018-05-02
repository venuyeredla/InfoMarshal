package org.vgr.compress;

import org.vgr.store.io.ByteBuf;

public class ArthimeticCoding implements Compressor{
	private byte bits=8;
	private int underflow=0;
	ByteBuf byteBuf=new ByteBuf();
	private int bitCount=0;
	
	@Override
	public byte[] compress(String txt) {
		byte[] bytes=txt.getBytes();
		int  low=0,high=(1<<8)-1;
		int MASK=(1<<bits)-1 ,TOP_MASK=1<<bits-1, SECOND_MASK=1<<bits-2;
		int MAX_ALLOWED=SECOND_MASK-1;
		System.out.print("MASK:"+MASK+"-"+NumSysUtil.decToBin(MASK));
		System.out.print(" TOP_MASK:"+TOP_MASK+"-"+NumSysUtil.decToBin(TOP_MASK));
		System.out.println(" SECOND_MASK:"+SECOND_MASK+"-"+NumSysUtil.decToBin(SECOND_MASK));
		FreqTable freqTable=new FreqTable(MAX_ALLOWED,256);
		freqTable.buildRanges(bytes);
		int total=freqTable.getTotal();
		for(int i=0;i<bytes.length;i++) {
			int symLow=freqTable.getLow(bytes[i]), symHigh=freqTable.getHigh(bytes[i]);
			int range=high-low+1;
			int newLow=low+(symLow*range)/total;
			int newHigh=low+(symHigh*range)/total-1;
			low=newLow;high=newHigh;
			System.out.print("low-high:"+low+"-"+high+" "+NumSysUtil.decToBin(low)+"-"+NumSysUtil.decToBin(high));
			while(((low ^ high) & TOP_MASK)==0) {
				 shift(low);
		         low=(low<<1) & MASK;
		         high=((high<<1) & MASK) | 1;
		         System.out.print("low-high:"+low+"-"+high+" "+NumSysUtil.decToBin(low)+"-"+NumSysUtil.decToBin(high));
			}
			// While the second highest bit of low is 1 and the second highest bit of high is 0
			while ((low & ~high & SECOND_MASK) != 0) {
				underflow();
				low = (low << 1) & (MASK >>> 1);
				high = ((high << 1) & (MASK >>> 1)) | TOP_MASK | 1;
			}
			
		}
		System.out.println();
		System.out.println("written bit: "+bitCount);
		return byteBuf.getActualBytes();
	}
	public void shift(int num) {
		int bit=num>>(bits-1)&1;
		System.out.print("-- written "+bit+"\n");
		byteBuf.writeBit(bit);
		bitCount++;
		for (; underflow > 0; underflow--) {
			//System.out.println("");
			byteBuf.writeBit(bit ^ 1);
			bitCount++;
		}
	}
	
	public void underflow() {
		underflow++;
	 }
	
	@Override
	public byte[] compress(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] decompress(String txt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] decompress(byte[] bytes) {

		
		return null;
	}
	
	
	@Override
	public String decompressToTxt(byte[] compressed) {
		// TODO Auto-generated method stub
		return null;
	}
}

class FreqTable{
	private int[] frequencies;
	private int[] lows;
	private int[] highs;
	private int total;
	private int MAX_PROBABLITY;
	
	public FreqTable(int max,int size) {
		frequencies=new int[size];
		highs=new int[size];
		lows=new int[size];
		this.MAX_PROBABLITY=max;
	}
	
	public void buildRanges(byte[] data) {
		for(int i=0;i<data.length;i++) {
			frequencies[data[i]]++;
		}
		this.total=data.length;
		//if the total frequncies > bits-2 we should rescale it.
		if(this.total>=MAX_PROBABLITY)
		{
		  int rescaleValue=(this.total/MAX_PROBABLITY)+1;
		  for(int j=0;j<frequencies.length;j++) {
			  if(frequencies[j]>rescaleValue) {
				  frequencies[j]=frequencies[j]/rescaleValue;
			  }else if(frequencies[j] !=0){
				  frequencies[j]=1;
			  }
		  }
		}
		int cum=0;
		lows[0]=0;
		highs[0]=frequencies[0];
		for(int j=1;j<frequencies.length;j++) {
			if(frequencies[j]!=0) {
				lows[j]=cum;
				cum=cum+frequencies[j];
				highs[j]=cum;
			}
		}
        this.total=cum;
		
		System.out.println("(Character, frequency, low, high)");
		for(int k=0;k<frequencies.length;k++) {
			if(frequencies[k]!=0) {
			  System.out.print("("+(char)k+","+frequencies[k]+","+lows[k]+","+highs[k]+"),");
			}
		}
		System.out.println("");
		
	}
	
	public int getLow(int symbol) {
		if(symbol==0){
			return 0;
		}
		return lows[symbol];	
	}
	
	public int getHigh(int symbol) {
		return highs[symbol];
	}
	public int getTotal() {
		return this.total; 
	}
}


class NumSysUtil{
	public static String decToBin(int num) {
		String binString="";
		while(num>0) {
			int bit=num%2;
			binString=bit+binString;
			num=num/2;
		}
		return binString;
	}
	
	
}
