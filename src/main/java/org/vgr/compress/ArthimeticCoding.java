package org.vgr.compress;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArthimeticCoding implements Compressor{
	private byte bits=8;
	private int total=0;
	private int underflow=0;
	BitStream bitStream=new BitStream();
	@Override
	public byte[] compress(String txt) {
		byte[] bytes=txt.getBytes();
		Map<Byte, FreqTable> freqTableMap=this.charFreqs(txt);
		int  low=0,high=(1<<8)-1;
		int TOP=1<<bits-1, SECOND=1<<bits-2;
		for(int i=0;i<bytes.length;i++) {
			FreqTable sym=freqTableMap.get(bytes[i]);
			int symLow=sym.low, symHigh=sym.high;
			int range=high-low;
			int newLow=low+(symLow*range)/total;
			int newHigh=low+(symHigh*range)/total;
			low=newLow;high=newHigh;
			while(((newLow ^ newHigh) & TOP)==0) {
		         newLow=(newLow<<1) & TOP;
		         newHigh=((newHigh<<1) & TOP) | 1;
			}
			// While the second highest bit of low is 1 and the second highest bit of high is 0
			while ((low & ~high & SECOND) != 0) {
				underflow();
				/*low = (low << 1) & (MASK >>> 1);
				high = ((high << 1) & (MASK >>> 1)) | TOP_MASK | 1;*/
			}
			
		}
		
		return null;
	}

	
	public void shift(int num) {
		int bit=num>>>(bits-1);
		bitStream.write(bit);
		for (; underflow > 0; underflow--)
			bitStream.write(bit ^ 1);
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
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	public Map<Byte, FreqTable> charFreqs(String txt) {
		return this.charFreqs(txt.getBytes());
	}
    /**
     * Enumerates all bytes and calculates frequencies and does scaling on probability line.
     * @param bytes
     * @return
     */
	public Map<Byte, FreqTable> charFreqs(byte[] bytes) {
		List<Byte> byteList = new ArrayList<>();
		for (int i = 0; i < bytes.length; i++) {
			byteList.add(bytes[i]);
			total++;
		}
			
		Map<Byte, Long> charFreq = byteList.stream().collect(Collectors.groupingBy(b -> b, Collectors.counting()));
		Map<Byte, FreqTable> freqTableMap=new LinkedHashMap<>();
		int scale=1<<bits-3;
		int cumFreq=0;
		for(Map.Entry<Byte, Long>  entry :charFreq.entrySet()) {
			  byte symbol=entry.getKey();
			  int freq = Math.toIntExact(entry.getValue());
			  int low=cumFreq;
			  int high=cumFreq+freq;
			  cumFreq=high;
			  freqTableMap.put(symbol, new FreqTable(symbol, freq, low, high));
		}
		return freqTableMap;
	}
}

class FreqTable{
	 protected byte symbol;
	 protected int freq;
	 protected int low;
	 protected int high;
	 public FreqTable(byte symbol,int freq,int low ,int high) {
        this.symbol=symbol;
        this.freq=freq;
        this.low=low;
        this.high=high;
	 }
	@Override
	public String toString() {
		return "FreqTable [symbol=" + symbol + ", freq=" + freq + ", low=" + low + ", high=" + high + "]";
	}
}


class BitStream{
	short[] compressed=new short[50];
	int currentByte=0; 
	int filled=0;
	int idx=0;
	public void write(int bit) {
		    currentByte=(currentByte<<1) |bit;
	        filled++;
	        if(filled==8) {
	       	 compressed[idx++]=(short)currentByte;
	       	 currentByte=0;
	       	 filled=0;
	      }
	}
	
}

