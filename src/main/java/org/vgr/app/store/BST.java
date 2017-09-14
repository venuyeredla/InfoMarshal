package org.vgr.app.store;

public class BST {
	private Node root; 

	public void insert(int key) {
		if(root==null) root=creatNode(key);
		else insert(root, key);
	  }

	private boolean insert(Node node,int key) {
		if(node!=null){
			if(node.key==key) return false;
			else if(key<node.key) {	
				 if(node.left==null) {
					 node.left=creatNode(key); return true;
				 }else return insert(node.left, key);
				}
			else if(key>node.key) {
				   if(node.right==null) {node.right=creatNode(key); return true;}
				   else return insert(node.right, key);
		   }
			}
		return false;
	}
	
	public boolean search(int key) {
		return root==null?false:search(root, key);
	}
	
	private boolean search(Node node,int key) {
		return node==null?false:node.key==key?true:key<node.key?this.search(node.left, key):this.search(node.right, key);
	}
	
	private static Node creatNode(int key) {
		return new Node(key);
	}
	
	public void delete() {
		
	}
	
	public void preOrder(Node node,DataWriter dataWriter) {
		if(node!=null) {
			dataWriter.writeInt(node.key);
			if(node.left!=null) preOrder(node.left,dataWriter);
			if(node.right!=null) preOrder(node.right, dataWriter); 
		 }
	}
	
	public void writeToFile(DataWriter dataWriter) {
		dataWriter.writeString("BST#inorder");
		
	}
	
	public void readFrom(DataReader dataReader) {
		
		
	}
	
	
	
	
	static class Node{
		private int key;
		private Node left;
		private Node right;
		public Node(int key) {
			this.key = key;
		}
	}

}
