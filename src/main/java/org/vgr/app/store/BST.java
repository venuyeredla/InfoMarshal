package org.vgr.app.store;

public class BST {
	private Node root;
	private boolean balanced=false;
	
	public BST() {
		this.balanced=false;
	}
	public BST(boolean balance) {
		this.balanced=balance;
	}
	public void insert(int key) {
		if(root==null) { root=creatNode(key);  }
		else {
			if(!balanced) {
				insert(root, key);
			}else if(balanced) {
				insertAndBalance(root,key);
			}
		}
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
	
	/**
	 * Tree is balanced usingn AVL Tree.
	 * @param node
	 * @param key
	 * @return
	 */
	private boolean insertAndBalance(Node node,int key) {
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
	
	private Node leftRotate() {
		
		return null;
	}
	
	
	private Node rightRotate() {
		
		return null;
	}
	
	public boolean search(int key) {
		return root==null?false:search(root, key);
	}
	
	private boolean search(Node node,int key) {
		return node==null?false:node.key==key?true:key<node.key?this.search(node.left, key):this.search(node.right, key);
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
	
	private static Node creatNode(int key) {
		return new Node(key);
	}
	
		
	private int height(Node node) {
		return node!=null?node.height:0;
	}
	
	
	
	
	
	
	
	public void traverse(Traversal mode) {
		switch (mode) {
		case PRE:
			preOrder(root);
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
	public void preOrder(Node node) {
		if(node!=null) {
			System.out.print(node.key+",");
			if(node.left!=null) preOrder(node.left);
			if(node.right!=null) preOrder(node.right); 
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
		dataWriter.writeString("BST#inorder");
	}
	
	public void readFrom(DataReader dataReader) {
				
	}
	
	static class Node{
		private int key, height;
		private Node left,right;
		public Node(int key) {
			this.key = key;
			this.height=1;
		}
	}

}
