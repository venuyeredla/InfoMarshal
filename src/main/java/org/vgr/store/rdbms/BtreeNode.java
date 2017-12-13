package org.vgr.store.rdbms;

import java.util.Arrays;

public class BtreeNode{
	 public static int degree=DBConstatnts.DEGREE;      // Minimum degree (defines the range for number of keys) 
	 private int id;
	 private int parentId;
	 private int[] keys; //Array of keys 
	 private int keySize;// No of keys stored.
	 private int[] childs;//Array of childs;
	 private boolean leaf; 
	 private boolean hasLastChild=false;
	 private boolean isRoot;

	 public BtreeNode(int pageid,boolean leaf) {
		 keys=new int[2*degree];
		 childs=new int[2*degree+1];	
		 this.leaf=leaf;
		 this.id=pageid;
		 this.parentId=-1;
	 }
	public BtreeNode(int pageid,boolean leaf,int key) {
		 keys=new int[2*degree];
		 childs=new int[2*degree+1];
		 this.leaf=leaf;
		 keys[keySize++]=key;
		 this.id=pageid;
	 }
	
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	/**
	 * Adds key and child and returns maxKey in Node.
	 * @param key
	 * @param child
	 */
	 public int insert(int key,int childid) {
		 int j = this.keySize - 1;
		 int nextPos=-1;
		 if(j==-1) {
			 nextPos=j+1;
		 }else {
			 childs[j+2]=childs[j+1];
			 while(j>=0 && keys[j]>key) {
				 keys[j+1]=keys[j];
				 childs[j+1]=childs[j];
				 j--;
			 }
			 nextPos=j+1;
		 }
		 keys[nextPos]=key;
		 childs[nextPos]=childid;
		 keySize++;
         return this.maxKey();
	 }
	 
	 public void insert(int pos,int key,int childid) {
		 keys[pos]=key;
		 childs[pos]=childid;
		 if(keySize==0) {
			 keySize++;
		 }
		 
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
	 public int keyAt(int pos) {
		 return keys[pos];
	 }
	 
	  public int maxKey() {
	 	  return keys[keySize-1];
	   }
	 
	 
	 public int getChildId(int pos) {
		 return this.childs[pos];
	 }
	 
	 public String keys() {
			StringBuilder keyString=new StringBuilder("(Node,Leaf)=("+this.id+","+this.isLeaf()+")  Keys=(" );
			for(int i=0;i<keySize;i++) {
			 try {
					keyString.append(keys[i]+",");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
				
			keyString.append(")");
	        return new String(keyString);		 
	  }
	 
	 public String childs() {
			StringBuilder childsString=new StringBuilder("Childs=(");
			for(int i=0;i<keySize+1;i++) 
				childsString.append(childs[i]+",");
			childsString.append(")");
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
	public boolean isRoot() {
		return isRoot;
	}
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	
	
}
