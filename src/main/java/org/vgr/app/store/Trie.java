package org.vgr.app.store;

public class Trie {
     TrieNode root=null;
     public Trie() {
    	 root=new TrieNode();
     	}
	
	public void insert(String key) {
		int keyLen=key.length();
        TrieNode current=root;
		for(int l=0;l<keyLen;l++) {
		  int alphabetNum=key.charAt(l)-'a';
		  if(current.childs[alphabetNum]==null) {
			  current=current.childs[alphabetNum]=new TrieNode();
		    }else {
		    	current=current.childs[alphabetNum];
		    }
		}
		current.isLeaf=true;
	}
	
	public boolean search(String key) {
		int keyLen=key.length();
		TrieNode current=root;
		for(int level=0;level<keyLen;level++) {
			int alphabetNum=key.charAt(level)-'a';
			if(current.childs[alphabetNum]==null) return false;
			current=current.childs[alphabetNum];
		}
		return current.isLeaf==true?true:false;
	}
	
	
	public boolean delete(String key) {
		int keyLen=key.length();
		TrieNode current=root;
		for(int level=0;level<keyLen;level++) {
			int alphabetNum=key.charAt(level)-'a';
			if(current.childs[alphabetNum]==null) return false;
			current=current.childs[alphabetNum];
		}
		if(current.isLeaf==true) {
			current.isLeaf=false;
			return true;
		}
		return false;
	}
	
	static class TrieNode{
		private static final int size=26;
		public TrieNode[] childs;
		private boolean isLeaf;
		public TrieNode() {
			isLeaf=false;
			childs=new TrieNode[size];
			for(int i=0;i<size;i++) {
				childs[i]=null;
			}
		}
		public TrieNode[] getChilds() {
			return childs;
		}
		public void setChilds(TrieNode[] childs) {
			this.childs = childs;
		}
		public boolean isLeaf() {
			return isLeaf;
		}
		public void setLeaf(boolean isLeaf) {
			this.isLeaf = isLeaf;
		}
		
		
	}

}


