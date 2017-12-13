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
	public BtreeNode root;
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
	
 	public void insert(int key,int pageid) {
		if(root.isLeaf() && root.isFull()) {
			  BtreeNode newParent = newNode(false); 
			  splitChild(newParent, root, 0,key,pageid);
			  // LOG.info("New root : "+ root.getId());
              this.root=newParent;
		  }else {
			 insertNotFull(null, -1 , root,key,pageid);
		  }
	}
	
	/**
	 * Key is inserted in node or page which is not full.
	 * 
	 * @param node
	 * @param key
	 * @return
	 */
	  private BtreeNode insertNotFull(BtreeNode parent, int posInParent, BtreeNode node,  int key,int nodeid) {
		try {
			if (node.isLeaf() == true) {
				int maxKey=node.insert(key, nodeid);
				LOG.info("Inserted key="+key+"-> "+node.keys());
				if(parent!=null && posInParent>-1&& key>=maxKey ) {
					  parent.setKey(posInParent, key);
				 }
				return node;
			 } else {
				     int i=node.getKeySize()-1;
				     while(i>=0 && key<node.keyAt(i)) {
				    	   i--;
				     }
				     i=i+1;
				     int childPosInparent=i;
				     if(i==2*degree) {
				    	 if(!node.isLeaf()&&!node.isHasLastChild()) {
				    		 i--;
				    		 childPosInparent=i;
				    	 }else {
				    		 childPosInparent=-1;
				    	 }
				     }
					BtreeNode requiredChild = this.getChild(node, i);
					LOG.info("Insert (key,index)  : ("+key +"," +(i)+") --:  "+requiredChild.keys());
					if(requiredChild.isLeaf() && requiredChild.isFull()) {
						splitChild(node, requiredChild,childPosInparent,key,nodeid);
					}else {
						return insertNotFull(node,childPosInparent,requiredChild,key,nodeid);
					}
			   }
			} catch (Exception e) {
				LOG.info("Exception in inserting (key, in page)=("+key+"," + node.getId()+") - Keys"+node.keys());
				e.printStackTrace();
			}
			
		return node;
	}

	public void splitChild(BtreeNode parent, BtreeNode child, int index,int key,int pageid) {
		//LOG.info(child.getKeys());
		 BtreeNode childNew = newNode(child.isLeaf()); 
		 int k=0;
		 int firstMax=child.keyAt(degree-1);
		 for (k = 0; k <degree; k++) { //Copying keys to second child
			 childNew.insert(child.deleteKey(k + degree),child.deleteChild(k + degree));
		  }
		 if(child.isHasLastChild()) {
			 parent.setChild(parent.getKeySize(), child.deletChild(k+degree));
			 child.setHasLastChild(false);
		 }
		 if(key<firstMax) {
			 child.insert(key, pageid);
		 }else {
			 childNew.insert(key, pageid);
		 }
		 System.out.println("");
		 child.setParentId(parent.getId()); 
		 childNew.setParentId(parent.getId());
	 	 if(index==-1) {
	 		this.splitParent(parent, index, child.maxKey(), child.getId(), childNew.maxKey(), childNew.getId());
	 	 }else {
	 		parent.insert(child.maxKey(),child.getId());
	 		 if(!parent.isFull()) {
				 parent.insert(childNew.maxKey(), childNew.getId());
			   }else {
				   parent.setChild(index+1, childNew.getId());
				   parent.setHasLastChild(true);
			  }
	 	 }
	}
	
	
	public void splitParent(BtreeNode parent,int firstChildIndex,int firstKey,int firstID, int secondKey, int secondID) {
		    BtreeNode newParent = newNode(parent.isLeaf()); 
		    if(firstChildIndex==-1) {
		    for (int k = 1; k <degree; k++) { //Copying keys to second child
				   newParent.insert(parent.deleteKey(k + degree),parent.deleteChild(k + degree));
			    }
			 newParent.insert(firstKey, firstID);
			 newParent.insert(secondKey, secondID);
		   }else {
			   for (int k = 0; k <degree; k++) { //Copying keys to second child
				   newParent.insert(parent.deleteKey(k + degree),parent.deleteChild(k + degree));
			    }
			   newParent.insert(firstKey, firstID);
			   newParent.insert(secondKey, secondID);
		   }
		    parent.setHasLastChild(false);
		    parent.setChild(2*degree, 0);
		    
		 if(parent.getParentId()==-1) {
			  BtreeNode newRoot = newNode(false); 
			  newRoot.insert(parent.maxKey(), parent.getId());
			  newRoot.insert(newParent.maxKey(), newParent.getId());
              this.root=newRoot;
		 }else {
			 BtreeNode parentParent=getParent(parent);
			 parentParent.insert(parent.maxKey(), parent.getId());
			 parentParent.insert(newParent.maxKey(), newParent.getId());
		 }
	}
	
	
	
	public void traverse() {
		LOG.info("Traversing tree ");
		this.traverse(root);
	}
	
	private void traverse(BtreeNode node) {
		if (node != null) {
			//LOG.info(page.keys()+ "Childs   : "+page.childs());
			//if(!page.isLeaf())
			System.out.println(node.keys()+ "  "+node.childs());
			int i;
			try {
				if (node.isLeaf() == false) {
					for (i = 0; i < node.getKeySize(); i++) {
							BtreeNode child=getChild(node, i);
							traverse(child);
						 }
					
					  if(node.isHasLastChild()) {
						  BtreeNode lastChild=getChild(node, i);
						  traverse(lastChild);
					  }
					}
			}catch (Exception e) {
				e.getSuppressed();
			}
		}
	}
	

	public BtreeNode search(BtreeNode page, int key) {
		int position = 0;
		try {
			int i = 0;
			while (i < page.getKeySize() && key > page.keyAt(i)) {
				i++;
			}
			position = page.keyAt(i);
			if (page.keyAt(i) == key)
				return page;

			if (page.isLeaf() == true) {
				return null;
			}
			BtreeNode childePage=store.readIdxNode(page.getChildId(i));
			return search(childePage, key);

		} catch (Exception e) {
			System.out.println("Errop Page:" + page.getId() + " index" + position);
		}
		return null;
	}
	
	
   private BtreeNode getParent(BtreeNode node) {
	   return store.readIdxNode(node.getParentId());
   }
	
   private BtreeNode getChild(BtreeNode node,int pos) {
	    int childPage=node.getChildId(pos);
	   return store.readIdxNode(childPage);
   }
	
	public void delete() {

	}

	public BtreeNode newNode(boolean isLeaf ) {
		int pageNum=++pageCount;
		BtreeNode node = new BtreeNode(pageNum, isLeaf);
		store.addToBuffer(node.getId(), node);
		return node;
	}
	
	  public int nextPageId() {
		return schemaInfo.nextPage();
	  }

}
