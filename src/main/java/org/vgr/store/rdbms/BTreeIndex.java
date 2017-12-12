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
			 insertNotFull(root,-1 ,key,pageid);
		  }
	}
	
	/**
	 * Key is inserted in node or page which is not full.
	 * 
	 * @param node
	 * @param key
	 * @return
	 */
	  private BtreeNode insertNotFull(BtreeNode node, int posInParent, int key,int nodeid) {
		try {
			if (node.isLeaf() == true) {
				int maxKey=node.insert(key, nodeid);
				LOG.info("After Insert key="+key+"-> "+node.keys());
				 if((posInParent>-1 || posInParent < 2*degree ) && key>maxKey ) {
					  BtreeNode parent=store.readIdxNode(node.getParentId());
					  parent.setKey(posInParent, key);
				   }
				return node;
			 } else {
					int i =node.getKeySize() - 1;
					while (i >=0 && key < node.getKey(i))
						i--;
					BtreeNode requiredChild = this.getChild(node, i+1);
					if(requiredChild==null) {
						LOG.info("Adding last child to : "+node.getId() +" "+node.keys());
					 }else {
						 LOG.info("Insert (key,index)  : ("+key +"," +(i+1)+") --:  "+requiredChild.keys());
					 }
					if(requiredChild==null) {
						BtreeNode lastChild=newNode(true);
						lastChild.insert(key, nodeid);
						lastChild.setParentId(node.getId());
						node.setHasLastChild(true);
						node.setChild(i+1, lastChild.getId());
						return node;
					  }else {
						if (requiredChild.isFull()) {
							splitChild(node, requiredChild, i+1,key,nodeid);
							return node;
						 }else {
							return insertNotFull(requiredChild,i+1, key,nodeid);
						 }
					  }
				 }
			} catch (Exception e) {
				LOG.info("Exception in inserting (key, in page)=("+key+"," + node.getId()+") - Keys"+node.keys());
				e.printStackTrace();
			}
			
		return node;
	}

	public BtreeNode splitChild(BtreeNode parent, BtreeNode child, int index,int key,int pageid) {
		//LOG.info(child.getKeys());
		 BtreeNode childNew = newNode(child.isLeaf()); 
		 int k=0;
		 int firstMax=child.getKey(degree-1);
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
		 child.setParentId(parent.getId());
		 childNew.setParentId(parent.getId());
		 
		 if(index==2*degree) {
			 BtreeNode createdChild=null;
			  if(parent.getParentId()==-1) {
				  BtreeNode newParent = newNode(false); 
				  createdChild=splitChild(newParent, parent, 0,child.maxKey(),child.getId());
				  newParent.setChild(2, childNew.getId());
	              this.root=newParent;
			  }else {
				  BtreeNode pp=this.getParent(parent);
				  createdChild=splitChild(pp, parent, index, child.maxKey(), child.getId());
				  insertNotFull(pp, -1, childNew.maxKey(), childNew.getId());
					 
			  }
			  
		 }else {
			 parent.insert(index, child.maxKey(),child.getId());
			 if(!parent.isFull()) {
				 parent.insert(childNew.maxKey(), childNew.getId());
				 if(childNew.isHasLastChild()) {
					 parent.setChild(index+2, childNew.deletChild(2));	 
					 childNew.setHasLastChild(false);
				  }
			 }else {
				 if(parent.getParentId()==-1)
				  {
					  BtreeNode newParent = newNode(false); 
					  splitChild(newParent, parent, 0,childNew.maxKey(),childNew.getId());
		              this.root=newParent;
		              LOG.info("New root : "+ root.getId()+ " -- "+root.keys());
				  }else {
					  BtreeNode parentParent=this.getParent(parent); 
					  splitChild(parentParent, parent, 0,key,childNew.getId());
				  }
			 }
		 }

	
		 return childNew;
	}
	
	public void traverse() {
		LOG.info("Traversing tree ");
		this.traverse(root);
	}
	
	private void traverse(BtreeNode page) {
		if (page != null) {
			//LOG.info(page.keys()+ "Childs   : "+page.childs());
			//if(!page.isLeaf())
			System.out.println(page.keys()+ "  "+page.childs());
			int i;
			try {
				if (page.isLeaf() == false) {
					for (i = 0; i < page.getKeySize(); i++) {
							BtreeNode child=getChild(page, i);
							traverse(child);
						 }
						BtreeNode lastChild=getChild(page, i);
						if(lastChild!=null)
						traverse(lastChild);
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
			while (i < page.getKeySize() && key > page.getKey(i)) {
				i++;
			}
			position = page.getKey(i);
			if (page.getKey(i) == key)
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
