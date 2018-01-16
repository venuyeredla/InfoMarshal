package org.vgr.store.io;

public class ByteList {
	private ByteNode head;
	private int size;
	public ByteList() {
	}
	public void add(byte b) {
       if(head==null) {
    	   head=new ByteNode(b, null); 
    	   size++;
       }else {
    	   ByteNode current=head;
    	   while(current.getNext()!=null) {
    		   current=current.getNext();
    	   }
    	   ByteNode newNode=new ByteNode(b, null);   
    	   size++;
    	   current.setNext(newNode);
       }
	}
	
	public byte[] getBytes() {
		byte[] bytes=new byte[size];
		 ByteNode current=head;
		 int i=-1;
		 while(current!=null) {
		   bytes[++i]=current.getData();
   		   current=current.getNext();
   	    }
	   return bytes;
	}
}

class ByteNode{
	private byte data;
	private ByteNode next;
	public ByteNode(byte b,ByteNode byteNode) {
		this.data=b;
		this.next=byteNode;
	}
	public byte getData() {
		return data;
	}
	public void setData(byte data) {
		this.data = data;
	}
	public ByteNode getNext() {
		return next;
	}
	public void setNext(ByteNode next) {
		this.next = next;
	}
}