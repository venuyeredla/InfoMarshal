package org.vgr.store.rdbms;

public class IndexNode extends Node {
	 public static int degree=DBConstatnts.DEGREE;      // Minimum degree (defines the range for number of keys) 
	 private int[] keys;  // An array of keys
	 private int keySize;   // No of keys stored.
	 private int[] childs;
	 private int childSize;
	 private boolean leaf; 

	 public IndexNode(int pageid,boolean leaf) {
		 keys=new int[2*degree];
		 childs=new int[2*degree+1];	
		 this.leaf=leaf;
		 this.id=pageid;
		 childSize=0;
		 parentId=-1;
	 }
	public IndexNode(int pageid,boolean leaf,int key) {
		 keys=new int[2*degree];
		 childs=new int[2*degree+1];
		 this.leaf=leaf;
		 keys[0]=key;
		 keySize++;
		 this.id=pageid;
		 childSize=0;
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
	 
	 public void setChildId(int pos,int childId) {
		 this.childs[pos]=childId;
		 childSize++;
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
	 
	 public String getKeys() {
			StringBuilder keyString=new StringBuilder("Leaf  : "+this.isLeaf()+" "+this.id+" : ");
			for(int i=0;i<keySize;i++) 
				keyString.append("("+keys[i]+", "+childs[i]+"),");
	        return new String(keyString);		 
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
	
	public int getChildSize() {
		return childSize;
	}

	public void setChildSize(int childSize) {
		this.childSize = childSize;
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
	
	
	@Override
	public String toString() {
		return "DbPage [id=" + id + ", parentId=" + parentId + ", keySize=" + keySize + ", childSize=" + childSize
				+ ", leaf=" + leaf + "]";
	}

}
