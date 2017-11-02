package org.vgr.store.ds;

/**
 * Page is a BTree node is equivalent to block in Secondary storage.
 * The page size should be equivalent to storage block size.
 * Current tradeoff between IO is 512 bytes
 * This can be used for both B-Tree and B+Tree 
 * @author vyeredla
 *
 */
  public class Page {
	 public static int degree=50;      // Minimum degree (defines the range for number of keys) 
	 private static int noofPages=0;
	 
	 int pageNum;
	 int noOfBytesinPage;
	 int[] keys;  // An array of keys
	 int[] values; // Used in case of B-Tree not used in case of B+ tree.
	 private int keySize;   // No of keys stored.
	 Page parent;
	 Page[] childPages; // An array of child pointers
	 int[] childOffsets;
	  // Current number of keys
	 private boolean leaf; 

	 public Page(boolean leaf) {
		 keys=new int[2*degree-1];
		 childPages=new Page[2*degree];
		 childOffsets=new int[2*degree];
		 this.leaf=leaf;
		 noofPages++;
		 pageNum=noofPages;
	 }
   
	 public Page(boolean leaf,int key) {
		 keys=new int[2*degree-1];
		 childPages=new Page[2*degree];
		 childOffsets=new int[2*degree];
		 this.leaf=leaf;
		 keys[0]=key;
		 keySize++;
		 noofPages++;
		 pageNum=noofPages;
	 }
	 //Adds new key at end of the array
	 public void addKey(int key) {
		 keys[keySize++]=key;
	 }
	 public int deleteKey(int pos) {
		 int temp=keys[pos];
		 keys[pos]=0;
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
         this.increseKeySize();
	 }
	 
	 
	 public void setChild(int pos,Page childPage) {
		 childPages[pos]=childPage;
	 }
	 
	 public Page deleteChild(int pos) {
		 Page temp=childPages[pos];
		 childPages[pos]=null;
		 return temp;
	 }
	 /**
	  * Used when a child is split into two parts.
	  */
	 public void setHalfFill() {
		 this.keySize=degree-1;
	 }
	 
	 public Page getChild(int pos) {
		return childPages[pos];
	  }
	 public void updateKey(int pos,int key) {
		 
	 }
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNumber) {
		this.pageNum = pageNumber;
	}
	public int getKeySize() {
		return keySize;
	}
	public void setKeySize(int keySize) {
		this.keySize = keySize;
	}
	public void increseKeySize() {
		keySize++;
	}
	public boolean isFull() {
		return keySize== (2 * degree - 1)? true:false;
	}
	public String getKeys() {
		StringBuilder keyString=new StringBuilder("");
		for(int i=0;i<keySize;i++) 
			keyString.append(keys[i]+", ");
        return new String(keyString);		 
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
	
}
