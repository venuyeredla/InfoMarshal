package org.vgr.store.ds;

import org.vgr.store.io.DataReader;
import org.vgr.store.io.DataWriter;

public class BST {
	private Node root;
	private boolean balanced=false;
	private static int totalNodes=0;
	public BST() {
		this.balanced=false;
	}
	public BST(boolean balance) {
		this.balanced=balance;
	}
	public void insert(int key,long pointer) {
		if(root==null) { root=creatNode(key,pointer);  }
		else {
			if(!balanced) {
				insert(root, key,pointer);
			}else if(balanced) {
				insertAndBalance(root,key,pointer);
			}
		}
	  }
	private boolean insert(Node node,int key,long pointer) {
		if(node!=null){
			if(node.key==key) return false;
			else if(key<node.key) {	
				 if(node.left==null) {
					 node.left=creatNode(key,pointer); return true;
				 }else return insert(node.left, key,pointer);
				}
			else if(key>node.key) {
				   if(node.right==null) {node.right=creatNode(key,pointer); return true;}
				   else return insert(node.right, key,pointer);
				}
			}
		return false;
	}
	
	/**
	 * Tree is balanced usingn AVL Tree.
	 * @param node
	 * @param key
	 * @return
	 */
	private boolean insertAndBalance(Node node,int key,long pointer) {
		if(node!=null){
			if(node.key==key) return false;
			else if(key<node.key) {	
				 if(node.left==null) {
					 node.left=creatNode(key,pointer); return true;
				 }else return insert(node.left, key,pointer);
				}
			else if(key>node.key) {
				   if(node.right==null) {node.right=creatNode(key,pointer); return true;}
				   else return insert(node.right, key,pointer);
				}
			}
		return false;
	}
	
	private Node leftRotate() {
		
		return null;
	}
	
	
	private Node rightRotate() {
		
		return null;
	}
	
	public long search(int key) {
		Node node=search(root, key);
		return root==null?-1:node.dp;
	}
	
	private Node search(Node node,int key) {
		return node==null?null:node.key==key?node:key<node.key?this.search(node.left, key):this.search(node.right, key);
	}

	public boolean delete(int key) {
		delete(root, key);
		return true;
	  }
	
	private Node delete(Node node, int key) {
		  if(key<node.key) {
			  node.left=delete(node.left, key);
		  }else if(key>node.key) {
			  node.right=delete(node.right, key);
		  }else {
			  if(node.left==null) {
				  return node.right;
			  }else if(node.right==null) {
				  return node.left;
			  }
			  node.key=minKey(node.right);
			  node.right=delete(node.right, node.key);
		  }
		return node;
	}
	
	/**
	 * Tree is balanced using AVL Tree.
	 * @param node
	 * @param key
	 * @return
	 */
	private Node deleteAndBalance(Node node, int key) {
		  if(key<node.key) {
			  node.left=delete(node.left, key);
		  }else if(key>node.key) {
			  node.right=delete(node.right, key);
		  }else {
			  if(node.left==null) {
				  return node.right;
			  }else if(node.right==null) {
				  return node.left;
			  }
			  node.key=minKey(node.right);
			  node.right=delete(node.right, node.key);
		  }
		return node;
	}
	
	
	private int minKey(Node node) {
		 int minKey=node.key;
	     while (node.left !=null)
	      {
	         node = node.left;
	         minKey=node.key;
	      }
	     return minKey;
	     
	}
	
	private static Node creatNode(int key,long pointer) {
		totalNodes++;
		return new Node(key,pointer);
	}
	
		
	private int height(Node node) {
		return node!=null?node.height:0;
	}
	
	public void traverse(Traversal mode) {
		switch (mode) {
		case PRE:
			preOrder(root,null);
			break;
		case POST:
			postOrder(root);
			break;
		case IN:
			inOrder(root);
			break;
		default:
			break;
		}
	}
	
	public void inOrder(Node node) {
		if(node!=null) {
			if(node.left!=null) inOrder(node.left);
			System.out.print(node.key+",");
			if(node.right!=null) inOrder(node.right); 
		 }
	}
	public void preOrder(Node node,DataWriter dw) {
		if(node!=null) {
			dw.writeInt(node.key);dw.writeInt((int) node.dp);
			if(node.left!=null) preOrder(node.left,dw);
			if(node.right!=null) preOrder(node.right,dw); 
		 }
	}
	public void postOrder(Node node) {
		if(node!=null) {
			if(node.left!=null) postOrder(node.left);
			if(node.right!=null) postOrder(node.right); 
			System.out.print(node.key+",");
		 }
	}
	
	public void writeToFile(DataWriter dataWriter) {
		dataWriter.writeString("BST#pre");
		dataWriter.writeInt(totalNodes);
		preOrder(root,dataWriter);
		dataWriter.close();
	}
	
	public void readFromFile(DataReader dataReader) {
		String codec=dataReader.readString();
		int totalNodes=dataReader.readInt();
		for(int i=0;i<totalNodes;i++) {
			 int key=dataReader.readInt();
			 int pointer= dataReader.readInt();
			insert(key,pointer);
		}
		dataReader.close();
	}
	
	static class Node{
		private int key, height;
		private long dp; //dataponter;
		private Node left,right;
		public Node(int key) {
			this.key = key;
			this.height=1;
		}
		public Node(int key,long pointer) {
			this.key = key;
			this.height=1;
			this.dp=pointer;
		}
	}

}

	

