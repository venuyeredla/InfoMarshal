package org.vgr.store.rdbms;

import java.util.Arrays;

public class IndexNode extends Node {
	 public static int degree=DBConstatnts.DEGREE;      // Minimum degree (defines the range for number of keys) 
	 private int[] keys;  // An array of keys
	 private int keySize;   // No of keys stored.
	 private int[] childs;
	 private boolean leaf; 
	 private boolean hasLastChild=false;

	 public IndexNode(int pageid,boolean leaf) {
		 keys=new int[2*degree];
		 childs=new int[2*degree+1];	
		 this.leaf=leaf;
		 this.id=pageid;
		 parentId=-1;
	 }
	public IndexNode(int pageid,boolean leaf,int key) {
		 keys=new int[2*degree];
		 childs=new int[2*degree+1];
		 this.leaf=leaf;
		 keys[0]=key;
		 keySize++;
		 this.id=pageid;
	 }
	
	/**
	 * Adds key and child and returns maxKey in Node.
	 * @param key
	 * @param child
	 */
	 public int insert(int key,int childid) {
		 int j = this.keySize - 1;
		 int maxKey=key,nextPos=-1;
		 if(j==-1) {
			 nextPos=j+1;
		 }else {
			 while(j>=0 && keys[j]>key) {
				 keys[j+1]=keys[j];
				 childs[j+1]=childs[j];
				 if(maxKey<keys[j]) {
					 maxKey=keys[j];
				 }
				 j--;
			 }
			 nextPos=j+1;
		 }
		 keys[nextPos]=key;
		 childs[nextPos]=childid;
		 keySize++;
         return maxKey;
	 }
	 
	 public void insert(int pos,int key,int childid) {
		 if(pos>=keySize)  keySize++;
		 keys[pos]=key;
		 childs[pos]=childid;
		 
	 }
	 public void setChild(int pos,int childid) {
		 childs[pos]=childid;
	 }
	 public int deletChild(int pos) {
		 int temp=childs[pos];
		 childs[pos]=0;
		 return temp;
	 }
	 
	 
	 
	 public void add(int key,int childid) {
		 keys[keySize]=key;
		 childs[keySize]=childid;
		 keySize++;
	 }
	 
	 public int deleteKey(int pos) {
		 int temp=keys[pos];
		 keys[pos]=0;
		 keySize--;
		 return temp;
	 }
	 
	 public int deleteChild(int pos) {
		 int temp=childs[pos];
		 childs[pos]=0;
		 return temp;
	 }
	 
	 
	 public void setKey(int pos,int key) {
		 keys[pos]=key;
	 }
	 public int getKey(int pos) {
		 return keys[pos];
	 }
	 public void insertKey(int key) {
		 int j = this.keySize - 1;
		 while(j>=0 && keys[j]>key) {
			 keys[j+1]=keys[j];
			 j--;
		 }
         keys[j+1]=key;		 
         this.increaseKeySize();
	 }
	 
	 public int getChildId(int pos) {
		 return this.childs[pos];
	 }
	 
	 public String keys() {
			StringBuilder keyString=new StringBuilder("(Page,Leaf)->("+this.id+","+ this.isLeaf()+") --Keys: " );
			for(int i=0;i<keySize;i++) 
				keyString.append(keys[i]+",");
	        return new String(keyString);		 
	  }
	 
	 public String childs() {
			StringBuilder childsString=new StringBuilder();
			for(int i=0;i<keySize+1;i++) 
				childsString.append(childs[i]+",");
	        return new String(childsString);		 
	  }
	 
	 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKeySize() {
		return keySize;
	}

	public void setKeySize(int keySize) {
		this.keySize = keySize;
	}
	public void increaseKeySize() {
		keySize++;
	}
	public boolean isFull() {
		return keySize== (2 * degree)? true:false;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
	public boolean isHasLastChild() {
		return hasLastChild;
	}
	public void setHasLastChild(boolean hasLastChild) {
		this.hasLastChild = hasLastChild;
	}
	@Override
	public String toString() {
		return "IndexNode [keys=" + Arrays.toString(keys) + ", keySize=" + keySize + ", childs="
				+ Arrays.toString(childs) + ", leaf=" + leaf + ", hasLastChild=" + hasLastChild + "]";
	}

}
