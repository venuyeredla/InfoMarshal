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
			  BtreeNode newRoot=newNode(false);
			  //newRoot.insert(root.maxKey(), root.getId());
			  splitChild(newRoot,0, root,key,pageid);
              this.root=newRoot;
		 }else {
			 insertNotFull(null,root,key,pageid);
		  }
	  }
	  private BtreeNode insertNotFull(BtreeNode parent,BtreeNode node, int key,int nodeid) {
		try {
			if (node.isLeaf() == true) {
				int maxKey=node.insert(key, nodeid);
				if(parent!=null && key>=maxKey) {
				   int childPos=parent.getChildPos(node.getId());
				   if(childPos!=-1) {
					   parent.setKey(childPos, key);
				   }
				}
				return node;
			 } else {
				    int i=node.getKeySize()-1;
				    while(i>=0 && key<node.keyAt(i)) 
				    	i--;
				    if(++i==node.getKeySize())  i--;
					BtreeNode requiredChild = this.getChild(node, i);
					LOG.info("Insert (key,index)  : ("+key +"," +(i)+") --:  "+requiredChild.keys());
					if(requiredChild.isLeaf() && requiredChild.isFull()) {
						splitChild(node,i,requiredChild,key,nodeid);
					}else {
						return insertNotFull(node,requiredChild,key,nodeid);
					}
			   }
			} catch (Exception e) {
				LOG.info("Exception in inserting (key, in page)=("+key+"," + node.getId()+") - Keys"+node.keys());
				e.printStackTrace();
			}
		return node;
	}

	  /**
	   * Splits the child and insert element in right child.and calls update parent method.
	   * @param parent
	   * @param index
	   * @param child
	   * @param key
	   * @param pageid
	   */
	public void splitChild(BtreeNode parent, int index, BtreeNode child,int key,int pageid) {
		BtreeNode childNew=this.splitInsert(child,key,pageid);
		if(parent.isNotFull()) {
			parent.insert(index, child.maxKey(),child.getId());
			parent.insert(childNew.maxKey(), childNew.getId());
			child.setParentId(parent.getId());
			childNew.setParentId(parent.getId());
			this.updateParents(parent);
		}else {
			parent.insert(index, child.maxKey(),child.getId());
			child.setParentId(parent.getId());
			this.updateParents(parent);
			splitParent(parent, childNew);
		}
	 }
	
	
	/**
	 * Splits the parent and inserts the child in righ parent.
	 * @param parent
	 * @param child
	 * @return
	 */
	public void splitParent(BtreeNode parent,BtreeNode child) {
		  BtreeNode parentNew=this.splitInsert(parent,child.maxKey(),child.getId());
		  if(child.maxKey()<parent.maxKey()) {
			  child.setParentId(parent.getId());
		  }else {
			  child.setParentId(parentNew.getId());
		  }
		  
		  BtreeNode parentParent=this.getParent(parent);
		  if(parentParent==null) {
			  parentParent=newNode(false);
		      this.root=parentParent;
		  }
		  int index=parentParent.getChildPos(parent.getId());
		  index=index==-1?0:index;  //-1 means new root
		  if(parentParent.isNotFull()) {
			  parentParent.insert(index, parent.maxKey(),parent.getId());
			  parentParent.insert(parentNew.maxKey(), parentNew.getId());
			  parent.setParentId(parentParent.getId());
			  parentNew.setParentId(parentParent.getId());
			  this.updateParents(parentParent);
			}else {
				parentNew.insert(index, child.maxKey(),child.getId());
				child.setParentId(parentParent.getId());
				this.updateParents(parent);
				splitParent(parentParent,parentNew);
			}
     	}
	
	
	  public BtreeNode splitInsert(BtreeNode node,int key,int value) {
		  BtreeNode nodeNew = newNode(node.isLeaf()); 
			 int firstMax=node.keyAt(degree-1);
			 for (int k = 0; k <degree; k++) { //Copying keys to second child
				 nodeNew.insert(node.deleteKey(k + degree),node.deleteChild(k + degree));
			  }
			 if(key<firstMax) {
				 node.insert(key, value);
			 }else {
				 nodeNew.insert(key, value);
			 }
		  return nodeNew;
	  }
	  
	  public void updateParents(BtreeNode node) {
		       BtreeNode parent=this.getParent(node);
		       if(parent!=null) {
		    	      int index= parent.getChildPos(node.getId());
		    	       parent.setKey(index, node.maxKey());
		    	       this.updateParents(parent);
		       }
	  }
	

	

	
	public void traverse() {
		LOG.info("Traversing tree ");
		this.traverse(root);
	}
	
	private void traverse(BtreeNode node) {
		if (node != null) {
			//LOG.info(page.keys()+ "Childs   : "+page.childs());
			//if(!node.isLeaf())
			System.out.println(node.keys()+ "  "+node.childs());
			int i;
			try {
				if (node.isLeaf() == false) {
					for (i = 0; i < node.getKeySize(); i++) {
							BtreeNode child=getChild(node, i);
							traverse(child);
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
	     int parentId=node.getParentId();
	     if(parentId==-1) return null;
	     else return store.readIdxNode(parentId);
      }
	
   private BtreeNode getChild(BtreeNode node,int pos) {
	    int childPage=node.getChildId(pos);
	   return store.readIdxNode(childPage);
   }
	public void delete() {

	}

	public BtreeNode newNode(boolean isLeaf ) {
		int nodeid=++pageCount;
		BtreeNode node = new BtreeNode(nodeid, isLeaf);
		store.addToBuffer(node.getId(), node);
		return node;
	}
	
	  public int nextPageId() {
		return schemaInfo.nextPage();
	  }

}
