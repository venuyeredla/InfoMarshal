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

	public void insert(int key,int pageid) {
		if(root.isLeaf() && root.isFull()) {
			  IndexNode newParent = newNode(false); 
			  LOG.info("Root is full : "+ root.getId());
			  splitChild(newParent, root, 0,key,pageid);
              this.root=newParent;
		  }else {
			 insertNotFull(root,-1 ,key,pageid);
		  }
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
				LOG.info("Inserting (key,child,pageNum): ("+key+","+pageid+","+page.getId()+")");
				if(nodePosition!=-1 &&key>=maxKey) {
					IndexNode parent=store.readIdxNode(page.getParentId());
					if(nodePosition<parent.getKeySize()) {
						  parent.setKey(nodePosition, maxKey);
						  LOG.info("Updating parent (key,child,pageNum): ("+key+","+pageid+","+page.getId()+")");
					}
				
				}
				return page;
			} else {
				int i =page.getKeySize() - 1;
				if(key>page.getKey(i) && !page.isHasLastChild()){
					IndexNode lastChild=newNode(true);
					lastChild.insert(key, pageid);
					lastChild.setParentId(page.getId());
					page.setHasLastChild(true);
					page.setChild(i+1, lastChild.getId());
					return page;
				}else {
					while (i >=0 && key < page.getKey(i))
						i--;
					int childPageId = page.getChildId(i+1);
					LOG.info("Key to be inserted(key,index,page) : ("+key +"," +(i+1)+","+childPageId+") --:  "+page.keys());
					IndexNode requiredChild =store.readIdxNode(childPageId);
					if (requiredChild.isFull()) {
						splitChild(page, requiredChild, i+1,key,pageid);
						return page;
					 }else {
						return insertNotFull(requiredChild,i+1, key,pageid);
					 }
				}
			}
		} catch (Exception e) {
			LOG.info(page.keys());
			LOG.info("Exception in inserting key : " + key + " Page Number : " + page.getId());
			e.printStackTrace();
		}
		
		return page;
	}

	public void splitChild(IndexNode parent, IndexNode child, int index,int key,int pageid) {
		//LOG.info(child.getKeys());
		IndexNode childNew = newNode(child.isLeaf()); 
		int firstMax=child.getKey(degree-1);
		int secondMax=child.getKey(2*degree-1);
		int secondMin=child.getKey(degree);
		int k=0;
		for (k = 0; k <degree; k++) { //Copying keys to second child
			 childNew.insert(child.deleteKey(k + degree),child.deleteChild(k + degree));
		 }
		 childNew.setChild(k, child.deletChild(k+degree));
		 if(key<secondMin) {
			 firstMax=child.insert(key, pageid);
		 }else {
			 secondMax=childNew.insert(key, pageid);
		 }
		 child.setParentId(parent.getId());
		 childNew.setParentId(parent.getId());
		 //LOG.info(parent.getKeys());
		 parent.insert(index, firstMax,child.getId());
		 // this.insertInParent(parent,index+1,secondMax, childNew.getId());
		 if(!parent.isFull()) {
			 parent.insert(secondMax, childNew.getId());
		 }else {
			 if(parent.getParentId()==-1)
			  {
				  IndexNode newParent = newNode(false); 
				  splitChild(newParent, parent, 0,secondMax,childNew.getId());
	              this.root=newParent;
	            //LOG.info("New root : "+ root.getId());
			  }else {
				  IndexNode parentParent=this.getParent(parent); 
				  splitChild(parentParent, parent, 0,key,childNew.getId());
			  }
		 }
	/*	 LOG.info("Splitting child : "+ child.getId() +" into new child : " + childNew.getId());
		 LOG.info(child.keys()+"Childs : "+child.childs());
		 LOG.info(childNew.keys()+"Childs : "+childNew.childs());
		 LOG.info(parent.keys()+"Childs : "+parent.childs());*/
	}
	
	
	public void insertInParent(IndexNode node,int index, int key,int childid) {
		try {
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
		}catch (Exception e) {
			LOG.info("Error in inserting key in  parent(Page,key) -- ("+node.id+","+key+")");
			LOG.info(node.keys());
			e.printStackTrace();
		}
		
		
	}
	
	public void traverse() {
		LOG.info("Traversing tree ");
		this.traverse(root);
	}
	
	private void traverse(IndexNode page) {
		if (page != null) {
			//if(!page.isLeaf())
			LOG.info("Keys : "+page.keys()+ "Childs  : "+page.childs());
			int i;
			try {
				if (page.isLeaf() == false) {
					for (i = 0; i < page.getKeySize(); i++) {
							IndexNode child=getChild(page, i);
							traverse(child);
						 }
					if(page.isHasLastChild()) {
						IndexNode lastChild=getChild(page, i);
						traverse(lastChild);
					}
					
					}
			}catch (Exception e) {
				e.getSuppressed();
			}
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
		int pageNum=++pageCount;
		IndexNode node = new IndexNode(pageNum, isLeaf);
		store.addToBuffer(node.getId(), node);
		return node;
	}
	
	  public int nextPageId() {
		return schemaInfo.nextPage();
	  }

}
