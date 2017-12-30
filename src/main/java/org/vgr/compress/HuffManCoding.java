package org.vgr.compress;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.vgr.store.io.Bytes;

public class HuffManCoding {
	
	private HuffManNode[] nodes;
	private int capacity;
	private int heapSize;
	private LinkedHashMap<Byte, String> compressCodes=new LinkedHashMap<>();
	private LinkedHashMap<String, Byte> decompressCodes=new LinkedHashMap<>();
	public HuffManCoding() { }
	public byte[] compress(String txt) {
		   this.buldHuffmannCodes(txt);
		   Bytes bytes=new Bytes();
		   StringBuilder compressed=new StringBuilder();
		   bytes.writeByte(compressCodes.keySet().size());
		   compressCodes.forEach((key,val)->{
			   bytes.writeByte(key);
			   bytes.write(val);
		   });

		   for(int i=0;i<txt.length();i++) {
			   byte asciNum= (byte)txt.charAt(i);
			   String code=compressCodes.get(asciNum);
			   compressed.append(code);
		     }
		   int compressedStrLength=compressed.length();
		   int noOfChars=txt.length();
		   byte[] compressedBytes=this.bitStringTobytes(new String(compressed));
		   String reversProcess=this.bytesToBitString(compressedBytes);
		   reversProcess=reversProcess.substring(0, compressedStrLength);
		  // System.out.println("Compressed : "+compressed);
		  // System.out.println("To String  : "+reversProcess);
		   this.buildHuffmann(bytes);
		   System.out.println("Compressed   : "+txt);
		   System.out.println("Decompressed : "+toText(reversProcess));
		   
		   bytes.write(compressedBytes);
		   System.out.println("Actual: "+txt.getBytes().length+ ", Compressed Size : "+compressedBytes.length);
		   System.out.println("Including huffmann codes :  "+bytes.size());
		   return bytes.getActualBytes();
	   }
	
	
	  public String toText(String compressed) {
		  StringBuffer buffer=new StringBuffer();
		  String code="";
		  for(int i=0;i<compressed.length();i++) {
			  code=code+compressed.charAt(i);
			  if(decompressCodes.containsKey(code)) {
				  char c= (char)(int)decompressCodes.get(code);
				  buffer.append(c);
				  code="";
			  }
		  }
		  return new String(buffer);
	  }
	
	
	public void decompress(byte[] compressedBytes) {
		Bytes bytes=new Bytes();
		bytes.write(compressedBytes);
		this.buildHuffmann(bytes);
		int noOfChars=bytes.readInt();
		for(int i=0;i<noOfChars;i++) {
		}
	}
	
	  public void buildHuffmann(Bytes bytes) {
		  byte codeSize=bytes.readByte();
		for(byte i=0;i<codeSize;i++) {
			byte val=bytes.readByte();
			String key=bytes.readString();
			decompressCodes.put(key,val );
		  }
	}
	
	public void buldHuffmannCodes(String txt) {
		   Map<Byte, Integer> dictonary=new HashMap<>();
		   for(int i=0;i<txt.length();i++) {
			  byte asciNum= (byte)txt.charAt(i);
			  if(dictonary.containsKey(asciNum)) {
				  int count=dictonary.get(asciNum);
				  dictonary.put(asciNum, count+1);
			  }else {
				  dictonary.put(asciNum, 1);
			  }
		    }
			/* dictonary.forEach((key,val)->{
					System.out.print(key+ "--"+val +"  , ");
			 });*/
			 this.capacity=dictonary.keySet().size();
			 this.nodes=new HuffManNode[capacity];
			 this.heapSize=0;
			 dictonary.forEach((key,val)->{
				HuffManNode node=new HuffManNode(key,val);
				this.add(node);
			 });
			 
		 /*  this.printHeap();*/
		   this.buildHuffManTree();
		   this.printCodes(this.nodes[0], "");
		   System.out.println("Huffmann codes : ");
		   compressCodes.forEach((key,val)->{
			   char ch=(char)(int)key;
			   System.out.print(key+ "("+ch+")-"+val+" , ");
		   });
		   System.out.println("");
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
				compressCodes.put(node.key, str);
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
	/**
	 * Convert bit string to bytes
	 * @param bitString
	 * @return
	 */
    public byte[] bitStringTobytes(String bitString) {
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
    public String bytesToBitString(byte[] bytes) {
    	 StringBuffer strBuffer=new StringBuffer();
    	 for(int i=0;i<bytes.length;i++) {
    		 byte b=bytes[i];
    		 String str = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    		 strBuffer.append(str);
    	  }
    	return new String(strBuffer);
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