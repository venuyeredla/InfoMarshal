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
	 private int id;
	 private int parentId;
	 int[] childIds;
	 
	 int noOfBytesinPage;
	 int[] keys;  // An array of keys
	 int[] values; // Used in case of B-Tree not used in case of B+ tree.
	 private int keySize;   // No of keys stored.
	 Page parent;
	 Page[] childPages; // An array of child pointers
	  // Current number of keys
	 private boolean leaf; 

	 public Page(boolean leaf) {
		 keys=new int[2*degree-1];
		 childPages=new Page[2*degree];
		 childIds=new int[2*degree];
		 this.leaf=leaf;
		 noofPages++;
		 this.id=noofPages;
	 }
   

	public Page(boolean leaf,int key) {
		 keys=new int[2*degree-1];
		 childPages=new Page[2*degree];
		 childIds=new int[2*degree];
		 this.leaf=leaf;
		 keys[0]=key;
		 keySize++;
		 noofPages++;
		 this.id=noofPages;
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
	 
	 public void setChildId(int pos,int childId) {
		 this.childIds[pos]=childId;
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
	 
	 public String getKeys() {
			StringBuilder keyString=new StringBuilder("");
			for(int i=0;i<keySize;i++) 
				keyString.append(keys[i]+", ");
	        return new String(keyString);		 
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
	public void increseKeySize() {
		keySize++;
	}
	public boolean isFull() {
		return keySize== (2 * degree - 1)? true:false;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Page getParent() {
		return parent;
	}

	public void setParent(Page parent) {
		this.parent = parent;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	 @Override
		public String toString() {
			return "Page [id=" + id + ", parentId=" + parentId + ", keySize=" + keySize + ", leaf=" + leaf + "]";
		}	
	
	
}
