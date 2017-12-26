package org.vgr.compress;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.vgr.store.io.Bytes;

public class HuffManCoding {
	
	private HuffManNode[] nodes;
	private int capacity;
	private int heapSize;
	private LinkedHashMap<String, String> huffmanCodes=new LinkedHashMap<>();
	public HuffManCoding() { }
	public void compress(String txt) {
		   this.buldHuffmannCodes(txt);
		   Bytes bytes=new Bytes();
		   StringBuilder compressed=new StringBuilder();
		   bytes.write(huffmanCodes);
		   for(int i=0;i<txt.length();i++) {
			   char c= txt.charAt(i);
			   String code=huffmanCodes.get(new Character(c).toString());
			   compressed.append(code);
		     }
		   byte[] compressedBytes=this.convertToBytes(new String(compressed));
		   bytes.write(compressedBytes);
		   System.out.println("Actual size : "+txt.getBytes().length+ "  , Compressed Size : "+compressedBytes.length);
		   System.out.println("Size with including huffmann codes :  "+bytes.size());
		  // byte[] byteArr=bytes.getActualBytes();
	   }
	
	public void decompress(String txt) {
		
	}
	
	public void buldHuffmannCodes(String txt) {
		   Map<Character, Integer> dictonary=new HashMap<>();
		   for(int i=0;i<txt.length();i++) {
			  char c= txt.charAt(i);
			  Character charcter=new Character(c);
			  if(dictonary.containsKey(charcter)) {
				  int count=dictonary.get(charcter);
				  dictonary.put(charcter, count+1);
			  }else {
				  dictonary.put(charcter, 1);
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
		   huffmanCodes.forEach((key,val)->{
			   System.out.print(key+ "-"+val+" , ");
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
				huffmanCodes.put(new Character(node.c).toString(), str);
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
			System.out.print(node.c+"--"+node.freq+" , ");
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
	
    public byte[] convertToBytes(String bitString) {
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
}
class HuffManNode{
	protected int freq;
	protected char c;
	protected HuffManNode left, right;
	public HuffManNode(int freq) {
		this.freq = freq;
	}
	public HuffManNode(char c,int freq) {
		this.freq = freq;
		this.c = c;
	}
	@Override
	public String toString() {
		return "HuffManNode [freq=" + freq + ", c=" + c + "]";
	}
}