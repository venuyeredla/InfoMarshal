package org.vgr.compress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.vgr.store.io.ByteBuf;

public class HuffManCoding implements Compressor {
	private HuffManNode[] nodes;
	private int capacity;
	private int heapSize;
	private LinkedHashMap<Byte, String> hCodes=new LinkedHashMap<>();
	public HuffManCoding() { }
	
	@Override
	public byte[] compress(byte[] data) {
		Map<Byte, Long> charFreq = this.charFreqs(data);
	    this.buildHCodes(charFreq);
		ByteBuf byteBuf = new ByteBuf(4096);
		byteBuf.writeByte(hCodes.keySet().size());
		charFreq.forEach((key, val) -> {
			byteBuf.writeByte(key);
			byteBuf.writeVInt(Math.toIntExact(val));
		});
		byteBuf.write(data.length);
		for (int i = 0; i < data.length; i++) {
			String code = hCodes.get(data[i]);
			while(code.length()>0) {
				int bit=Integer.parseInt(code.substring(0, 1));
				byteBuf.writeBit(bit);
				code=code.substring(1);
			}
		 }
	    byteBuf.fillLast();
		System.out.println("\n Actual -> Compressed  :: "+ data.length+ " ->  "+byteBuf.getActualBytes().length);
		return byteBuf.getActualBytes();
	}
	
	@Override
	public byte[] decompress(byte[] compressed) {
		ByteBuf byteBuf=new ByteBuf(compressed);
		this.buildHCodes(this.readFreq(byteBuf));
		Map<String,Byte> dHCodes=new HashMap<>();
		hCodes.forEach((key,val)->{ dHCodes.put(val, key); });
		int datasize=byteBuf.readInt();
		byte[] data=new byte[datasize];
		String code="";
		for(int i=0;i<datasize;i++) {
			while(!dHCodes.containsKey(code)) {
				code=code+byteBuf.readBit();
			}
			data[i]=dHCodes.get(code);
			code="";
		}
		return data;
	}
	
	public Map<Byte, Long> readFreq(ByteBuf bytes) {
		byte codeSize = bytes.readByte();
		Map<Byte, Long> charFreq=new HashMap<>();
		for (byte i = 0; i < codeSize; i++) {
			byte key = bytes.readByte();
			long val = bytes.readVInt();
			charFreq.put(key, val);
		}
		return charFreq;
	}

	public Map<Byte, Long> charFreqs(String txt) {
		return this.charFreqs(txt.getBytes());
	}

	public Map<Byte, Long> charFreqs(byte[] bytes) {
		List<Byte> byteList = new ArrayList<>();
		for (int i = 0; i < bytes.length; i++)
			byteList.add(bytes[i]);
		Map<Byte, Long> charFreq = byteList.stream().collect(Collectors.groupingBy(b -> b, Collectors.counting()));
		return charFreq;
	}

	public void buildHCodes(Map<Byte, Long> dictonary) {
			 this.capacity=dictonary.keySet().size();
			 this.nodes=new HuffManNode[capacity];
			 this.heapSize=0;
			 dictonary.forEach((key,val)->{
				int v=Math.toIntExact(val);
				HuffManNode node=new HuffManNode(key,v);
				this.add(node);
			 });
		 /*  this.printHeap();*/
		   this.buildHuffManTree();
		   this.printCodes(this.nodes[0], "");
		   System.out.print("Huffmann codes: ");
		   hCodes.forEach((key,val)->{
			   char ch=(char)(int)key;
			   //System.out.print(key+ "("+ch+")-"+val+" , ");
			   System.out.print(ch+"-"+val+",");
		   });
	   }
	

	
	private void buildHuffManTree() {
		while(this.heapSize>1) {
			HuffManNode first=this.extractMin();
			HuffManNode second=this.extractMin();
			HuffManNode node=new HuffManNode(first.freq+second.freq);
			node.left=first;
			node.right=second;
			this.add(node);;
		}
	}
	
	private void printCodes(HuffManNode node, String str) {
		if(node!=null) {
			if(node.left!=null) {
				this.printCodes(node.left, str+"0");
			}
			if(node.right!=null) {
				this.printCodes(node.right, str+"1");
		    }
			if(node.left==null && node.right==null)
				hCodes.put(node.key, str);
		}
	}
	
	public void add(HuffManNode node) {
		int i=heapSize++;
		this.nodes[i]=node;
		while(i!=0 && validateHeapfy(parentIndex(i),i)) {
			this.swap(i, parentIndex(i));
			i=parentIndex(i);
		 }
	  }
	
	public void printHeap() {
		for(int i=0;i<heapSize;i++) {
			HuffManNode node=this.nodes[i];
			System.out.print(node.key+"--"+node.freq+" , ");
		}
		System.out.println();
	}
	
	public HuffManNode extractMin() {
		   HuffManNode temp=this.nodes[0];
		   this.nodes[0]=this.nodes[--heapSize];
		   this.nodes[heapSize]=null;
		   this.minHeafFy(0);
		   return temp;
	}
	
	private boolean validateHeapfy(int j ,int i) {
		HuffManNode parent=this.getNodeAt(j);
		HuffManNode child=this.getNodeAt(i);
		return parent.freq>child.freq;
	}
	// Heafies from top to bottom.
	private void minHeafFy(int i) {
		int l=2*i+1;
		int r=2*i+2;
		int smallest=i;
		if(l<heapSize && validateHeapfy(smallest,l)) smallest=l;
		if(r<heapSize && validateHeapfy(smallest,r)) smallest=r;
		if(smallest!=i) {
				swap(i, smallest);
				this.minHeafFy(smallest);
		 }
	 }
	
	public int getFreq(int i) {
		return this.getNodeAt(i).freq;
	}
	
	private void swap(int first ,int second) {
		HuffManNode temp=this.nodes[first];
		this.nodes[first]=this.nodes[second];
		this.nodes[second]=temp;
	}

	private int parentIndex(int i) {
		return (i-1)/2;
	}
	
	private HuffManNode getNodeAt(int i) {
		return this.nodes[i];
	}

  }

class HuffManNode{
	protected int freq;
	protected byte key;
	protected HuffManNode left, right;
	public HuffManNode(int freq) {
		this.freq = freq;
	}
	public HuffManNode(byte key,int freq) {
		this.freq = freq;
		this.key = key;
	}
	@Override
	public String toString() {
		return "HuffManNode [freq=" + freq + ", key=" + key + "]";
	}
}