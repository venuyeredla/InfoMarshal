package org.vgr.store.rdbms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 /**
 * B+ tree index, internal nodes contain index data & and leaves will hold actual data. 
 * @author vyeredla
 *
 */
public class BTreeIndex {
	private static final Logger LOG = LoggerFactory.getLogger(BTreeIndex.class);
	private Store store=null;
	public IndexNode root;
	private int degree=DBConstatnts.DEGREE;
	private SchemaInfo schemaInfo = null;
	private int pageCount=0;
	public BTreeIndex(Store store, int rootid) {
		   this.store=store;
	        /*if(this.fileStore.isExisted()) {
			 //root=fileStore.readIdxNode(rootid); 
		    }*/
		   root=newNode(true);
	   }

	public int insert(int key,int pageid) {
		if(root.isLeaf() && root.isFull()) {
			  IndexNode newParent = newNode(false); 
			  splitChild(newParent, root, 0,key,pageid);
              this.root=newParent;
              LOG.info("New root : "+ root.getId());
		  }else {
			 insertNotFull(root,-1 ,key,pageid);
		  }
		return 0;
	}
	
	/**
	 * Key is inserted in node or page which is not full.
	 * 
	 * @param page
	 * @param key
	 * @return
	 */
	private IndexNode insertNotFull(IndexNode page, int nodePosition, int key,int pageid) {
		try {
			if (page.isLeaf() == true) {
				int maxKey=page.insert(key, pageid);
				//LOG.info(page.getKeys());
			//	LOG.info("Inserting (key,child,pageNum): ("+key+","+pageid+","+page.getId()+")");
				if(nodePosition!=-1 && key>maxKey) {
				  IndexNode parent=store.readIdxNode(page.getParentId());
				  parent.setKey(nodePosition, maxKey);
				  LOG.info(parent.getKeys());
				}
				return page;
			} else {// if this is not a leaf node.
				
				int i = page.getKeySize() - 1;
				if(key>page.getKey(i)) {
					i--;
				}else {
					while (i >= 0 && key < page.getKey(i))
						i--;
				}
				int childPageId = page.getChildId(i+1);
				IndexNode requiredChild =store.readIdxNode(childPageId);
				if (requiredChild.isFull()) {
					splitChild(page, requiredChild, i+1,key,pageid);
					return page;
				 }else {
					return insertNotFull(requiredChild,i+1, key,pageid);
				 }
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("Exception in inserting key : " + key + " Page Number : " + page.getId());
		}
		
		return page;
	}

	public void splitChild(IndexNode parent, IndexNode child, int index,int key,int pageid) {
		LOG.info("Splitting child : "+ child.getId());
		LOG.info(child.getKeys());
		parent.setLeaf(false);
		IndexNode childNew = newNode(child.isLeaf()); 
		int firstMax=child.getKey(degree-1);
		int secondMax=child.getKey(2*degree-1);
		int secondMin=child.getKey(degree);
		for (int k = 0; k <degree; k++) { //Copying keys to second child
			 childNew.insert(child.deleteKey(k + degree),child.deleteChild(k + degree));
		 }
		 if(key<secondMin) {
			 firstMax=child.insert(key, pageid);
		 }else {
			 secondMax=childNew.insert(key, pageid);
		 }
		 child.setParentId(parent.getId());
		 childNew.setParentId(parent.getId());
		 LOG.info(child.getKeys());
		 LOG.info(childNew.getKeys());
		 parent.insert(index, firstMax,child.getId());
		 LOG.info(parent.getKeys());
		 //this.insertInParent(parent,index,firstMax, child.getId(),false);
		 this.insertInParent(parent,index+1,secondMax, childNew.getId(),true);
		 LOG.info(parent.getKeys());
	}
	
	
	public void insertInParent(IndexNode node,int index, int key,int childid,boolean move) {
		 if(!node.isFull())
		 {
			/*int  i=node.getKeySize()-1;
			 while(i>=index ) {
				 node
			 }*/
			 node.insert(key, childid);
		 }else {
			  if(node.getParentId()==-1)
			  {
				  IndexNode newParent = newNode(false); 
				  splitChild(newParent, root, 0,key,childid);
	              this.root=newParent;
	              LOG.info("New root : "+ root.getId());
			  }else {
				  IndexNode parent=this.getParent(node); 
				  splitChild(parent, node, 0,key,childid);
			  }
		 }
		
	}
	
	public void traverse() {
		this.traverse(root);
	}
	
	private void traverse(IndexNode page) {
		if (page != null) {
			System.out.println("Leaf :"+page.isLeaf() +"  (Page ,keys) - (" +page.getId() + "," + page.getKeySize() + " ) --" + page.getKeys());
			int i;
			
			if (page.isLeaf() == false) {
			for (i = 0; i < page.getKeySize(); i++) {
					IndexNode child=getChild(page, i);
					traverse(child);
				 }
			}
			/*if (page.isLeaf() == false)
			  traverse(page);*/
		}
	}
	
	
	
   private IndexNode getParent(IndexNode node) {
	   return store.readIdxNode(node.getParentId());
   }
	
   private IndexNode getChild(IndexNode node,int pos) {
	    int childPage=node.getChildId(pos);
	   return store.readIdxNode(childPage);
   }
	

	public IndexNode search(IndexNode page, int key) {
		int position = 0;
		try {
			int i = 0;
			while (i < page.getKeySize() && key > page.getKey(i)) {
				i++;
			}
			position = page.getKey(i);
			if (page.getKey(i) == key)
				return page;

			if (page.isLeaf() == true) {
				return null;
			}
			IndexNode childePage=store.readIdxNode(page.getChildId(i));
			return search(childePage, key);

		} catch (Exception e) {
			System.out.println("Errop Page:" + page.getId() + " index" + position);
		}
		return null;
	}

	public void delete() {

	}

	public IndexNode newNode(boolean isLeaf ) {
		IndexNode node = new IndexNode(++pageCount, isLeaf);
		store.addToBuffer(node.getId(), node);
		return node;
	}
	
	
/*	public IndexNode getPageTravarse(int pageid) {
		if (pageid == -1) {
			return null;
		} else if (pageid <= schemaInfo.getPages()) {
			IndexNode rpage=store.readIdxNode(pageid);
			//unsavedPages.add(rpage);
			return rpage;
     	}
		return null;
	}*/
	

	  public int nextPageId() {
		return schemaInfo.nextPage();
	  }

}
