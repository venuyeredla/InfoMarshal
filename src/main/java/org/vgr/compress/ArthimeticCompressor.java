package org.vgr.compress;

import org.vgr.store.io.ByteBuf;

public class ArthimeticCompressor implements Compressor{
	
	private byte bits=8;
	int MASK=(1<<bits)-1;           //Max range number with all ones. 1111...111
	int TOP_MASK=1<<bits-1;         //Top bit mask. 10000...0000
	int SECOND_MASK=1<<bits-2;      //Second bit mask. 01000...000
	int MAX_ALLOWED=SECOND_MASK-1;  // 00111..111

	private int underflow=0;
	ByteBuf byteBuf=new ByteBuf();
	private int bitCount=0;
	private FreqTable freqTable;
	
	private int low,high;
	
	public ArthimeticCompressor() {
		// TODO Auto-generated constructor stub
	}

	public void init() {
		System.out.println("MASK:"+MASK+"-"+NumSysUtil.decToBin(MASK));
		System.out.println(" TOP_MASK:"+TOP_MASK+"-"+NumSysUtil.decToBin(TOP_MASK));
		System.out.println(" SECOND_MASK:"+SECOND_MASK+"-"+NumSysUtil.decToBin(SECOND_MASK));
	}
	public void initcompress() {
	}
	
	@Override
	public byte[] compress(byte[] bytes) {
		//this.init();
		System.out.println("Data size: "+bytes.length);
		this.low=0; this.high=(1<<8)-1;
		this.freqTable=new FreqTable(MAX_ALLOWED,256);
		this.freqTable.buildRanges(bytes);
		for(int i=0;i<bytes.length;i++) {
			System.out.print("\nEncoding:"+(char)bytes[i]+"\t");
			this.applySymbolRange(bytes[i]);
			while(((low ^ high) & TOP_MASK)==0) {
				 eshift(low);
		         low=(low<<1) & MASK;
		         high=((high<<1) & MASK) | 1;
		         //System.out.print(low+"-"+high+"->"+NumSysUtil.decToBin(low)+"-"+NumSysUtil.decToBin(high)+" ");
		         System.out.print(low+"-"+high);
			}
			// While the second highest bit of low is 1 and the second highest bit of high is 0
			while ((low & ~high & SECOND_MASK) != 0) {
				eunderflow();
				low = (low << 1) & (MASK >>> 1);
				high = ((high << 1) & (MASK >>> 1)) | TOP_MASK | 1;
				System.out.print(low+"-"+high);
			}
			
		}
		System.out.println("written bit: "+bitCount);
		return byteBuf.getActualBytes();
	}
	
	public void applySymbolRange(int symbol) {
		int symLow=this.freqTable.getLow(symbol), symHigh=this.freqTable.getHigh(symbol);
		int cumulative=this.freqTable.getTotal();
		int range=high-low+1;
		high=low+(symHigh*range)/cumulative-1;
		low=low+(symLow*range)/cumulative;
		//System.out.print(low+"-"+high+"->"+NumSysUtil.decToBin(low)+"-"+NumSysUtil.decToBin(high)+" ");
		System.out.print(low+"-"+high);
	}
	
	public void eshift(int num) {
		int bit=num>>(bits-1)&1;
		System.out.print("("+bit+")-->");
		byteBuf.writeBit(bit);
		bitCount++;
		for (; underflow > 0; underflow--) {
			byteBuf.writeBit(bit ^ 1);
			bitCount++;
		}
	}
	
	public void eunderflow() {
		underflow++;
	 }
	
	private int code=0;
	
	@Override
	public byte[] decompress(byte[] bytes) {
		ByteBuf byteBuf=new ByteBuf(bytes);
		this.low=0;this.high=MASK;
		int total=this.freqTable.getTotal();
		//initialize the code
		for(int i=0;i<bits;i++) {
			code=code<<1 | byteBuf.readBit();
		}
		// Decoding the code;
		for(int j=0;j<132;j++) {
			int range=high-low+1;
			int offset=code-low;
			int value=(offset * total-1)/range;
			int symb=freqTable.getSymbol(value);
			System.out.println((char)symb);
			this.applySymbolRange(symb);
			
			while (((low ^ high) & TOP_MASK) == 0) {
				dshift();
				low = (low << 1) & MASK;
				high = ((high << 1) & MASK) | 1;
				System.out.print(low+"-"+high);
			}
			
			// While the second highest bit of low is 1 and the second highest bit of high is 0
			while ((low & ~high & SECOND_MASK) != 0) {
				dunderflow();
				low = (low << 1) & (MASK >>> 1);
				high = ((high << 1) & (MASK >>> 1)) | TOP_MASK | 1;
				System.out.print(low+"-"+high);
			}
		}
		return null;
	}
	
	public void dshift() {
		int nextBit=byteBuf.readBit();
		code = ((code << 1) & MASK) |nextBit;
	}
	
	public void dunderflow() {
		int nextBit=byteBuf.readBit();
		code = (code & TOP_MASK) | ((code << 1) & (MASK >>> 1)) | nextBit;
	}
	
	public void readEncodedBits(int code) {
		
	}
	
	
}

class FreqTable{
	private int[] frequencies;
	private int[] cumulatives;
	private int total;
	private int MAX_PROBABLITY;
	public FreqTable(int max,int size) {
		frequencies=new int[size];
		cumulatives=new int[size];
		this.MAX_PROBABLITY=max;
	 }
	
	 public void buildRanges(byte[] data) {
		for(int i=0;i<data.length;i++) {
			frequencies[data[i]]++;
		}
		this.total=data.length;
		//if the cumulative frequncies > bits-2 we should rescale it.
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
		cumulatives[0]=frequencies[0];
		int cumulative=0;
		for(int j=1;j<frequencies.length;j++) {
				cumulatives[j]=cumulative+frequencies[j];
				cumulative=cumulatives[j];	
		}
        this.total=cumulative;
        System.out.println("Symbol ranges with upper bounds");
		for(int k=1;k<frequencies.length;k++) {
			if(frequencies[k]!=0) {
			  System.out.print((char)k+"=["+cumulatives[k-1]+","+cumulatives[k]+"],");
			}
		}
		System.out.println("");
		
	}
	
	public int getLow(int symbol) {
		if(symbol==0){
			return 0;
		}
		return cumulatives[symbol-1];	
	}
	public int getHigh(int symbol) {
		return cumulatives[symbol];
	}
	
	
	public int getSymbol(int probability) {
		int temp=0;
         for(int i=0;i<256;i++) {
        	 if(probability<this.cumulatives[i]) {
        		 temp=i;
        		 break;
        	 }
         }
		return temp;
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
