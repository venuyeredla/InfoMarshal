package org.vgr.store.ds;

public class BTree {
	public BNode root;
	private int t=3;
	
     public void insert(int key,long data) {
    	  root=insert(root, key, data);	 
    	  System.out.print(" ");
     }
    public BNode insert(BNode node,int key,long data) {
    	if(node==null) {
    		node=new BNode(true);
    		node.keys[0]=key;
    		node.keySize=1;
    		return node;
    	}else {
    		 if(node.keySize==(2*t-1)) {
    			 BNode newParent=new BNode(false);
    			 newParent.childs[0]=node;
    			 splitChild(newParent, node,0);
    			 int i=0;
    			 if(newParent.keys[0]<key)
    				 i++;
    			 insertNotFull(newParent.childs[i], key);
                 node=newParent;
                 return node;
    		 }else {
    			insertNotFull(node, key);
    			return node;
    		 }
    	}
     }
     
     
     public void splitChild(BNode parent,BNode child, int index ) {
    	 BNode child2=new BNode(child.leaf);
    	 child2.keySize=t-1;
    	 for(int j=0;j<t-1;j++) { // copying second half of keys to new child.
    		 child2.keys[j]=child.keys[j+t];
    	 }
    	 for(int j=0;j<t;j++) {  //Copy childs
    		 child2.childs[j]=child.childs[j+t];  
    	 }
    	 child.keySize=t-1;
    	 for(int j=parent.keySize;j>index+1;j--) {  //need to analyze
    		 
    	 }
    	 parent.childs[index+1]=child2;
    	 for(int j=parent.keySize-1;j>=index;j--) {
    		 parent.keys[j+1]=parent.keys[j];
    	 }
    	 parent.keys[index]=child.keys[t-1];
    	 parent.keySize++;
    	 
    	 for(int i=t-1;i<2*t-1;i++) {
    		 child.keys[i]=0;
    	 }
    	 
     }

     private BNode insertNotFull(BNode node,int key) {
    	   int i=node.keySize-1;
    	   if(node.leaf==true) {
    		   while(i>=0&& node.keys[i]>key) {
    			   node.keys[i+1]=node.keys[i];
    			   i--;
    		   }
    		   node.keys[i+1]=key;
    		   node.keySize++;
    		   return node;
    	   }else {// if this is not a leaf node.
    		while(i>=0 && node.keys[i]>key)
    			  i--;
    		if(node.childs[i+1].keySize==2*t-1) {
    			splitChild(node, node.childs[i+1], i+1);
    			if(node.keys[i+1]<key)
    				i++;
    		}
    		return insertNotFull(node.childs[i+1], key);
    	   }
     }
     
     public void traverse(BNode node) {
    	 if(node!=null) {
        	 int i;
        	 for(i=0;i<node.keySize;i++) {
        		 if(node.leaf=false) 
        			traverse(node.childs[i]);
        		  System.out.print(node.keys[i]+",");
        	 }
        	 if(node.leaf==false)
        		 traverse(node.childs[i]); 
    	 }
     }
     
     public BNode search(BNode node,int key) {
    	 int i=0;
    	 while(i<node.keySize && key>node.keys[i]) {
    		 i++;
    	 }
    	 if(node.keys[i]==key)
    		 return node;
    	 
    	 if(node.leaf=true) {
    		 return null;
    	 }
    	 return search(node.childs[i], key);
     }
     
     public void delete() {
    	 
     }
     
     
}






class BNode{
	 int[] keys;  // An array of keys
	 int t=3;      // Minimum degree (defines the range for number of keys)
	 BNode[] childs; // An array of child pointers
	 int keySize;     // Current number of keys
	 boolean leaf; 
	 public BNode(boolean leaf) {
		 childs=new BNode[2*t];
		 keys=new int[2*t-1];
		 this.leaf=leaf;
	 }
	 
}