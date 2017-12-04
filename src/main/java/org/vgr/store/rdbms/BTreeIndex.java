package org.vgr.store.rdbms;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.store.ds.BTree;
import org.vgr.store.ds.Page;

 /**
 * B+ tree index, internal nodes contain index data & and leaves will hold actual data. 
 * @author vyeredla
 *
 */
public class BTreeIndex {
	private static final Logger LOG = LoggerFactory.getLogger(BTree.class);
	private FileStore fileStore=null;
	public IndexNode root;
	private int degree = Page.degree;
	private SchemaInfo schemaInfo = null;
	private List<IndexNode> unsavedPages = null;

	public BTreeIndex() {
		this.schemaInfo=schemaInfo.getSchemaInfo();
		unsavedPages = new ArrayList<>();
		root=fileStore.readIdxNode(schemaInfo.getRootPage());  // getPage(schemaInfo.getRootPage());
	 }

	public void insert(int key, long data) {
		root = insert(root, key, data);
		schemaInfo.setRootPage(root.getId());
	}

    public IndexNode insert(IndexNode node, int key, long data) {
		if(node == null) {
			return newNode(true,key); 
		}else {
			if(node.isLeaf() && !node.isFull()) {
				  insertNotFull(node,key);
				  return node;
			}else if(node.isLeaf() && node.isFull()) {
				  IndexNode newParent = newNode(false); // new Page(nextPageId(),false);
				  newParent.setChild(0, node);
				  splitChild(newParent, node, 0);
				  int i = 0;
				  if (newParent.getKey(0) < key)
						i++;
				  insertNotFull(newParent.getChild(i), key);
				  return newParent; // page = newParent;
			 }
		}
		return node;
	}

	/**
	 * Key is inserted in node or page which is not full.
	 * 
	 * @param page
	 * @param key
	 * @return
	 */
	private IndexNode insertNotFull(IndexNode page, int key) {
		try {
			int i = page.getKeySize() - 1;
			if (page.isLeaf() == true) {
				page.insertKey(key);
				return page;
			} else {// if this is not a leaf node.
				while (i >= 0 && page.getKey(i) > key)
					i--;
				int childPageId = page.getChildId(i + 1);
				IndexNode requiredChild = getPage(childPageId);
				if (requiredChild.isFull()) {
					splitChild(page, requiredChild, i + 1);
					if (page.getKey(i + 1) < key)
						i++;
				 }
				IndexNode newRequired = getPage(page.getChildId(i + 1));
				return insertNotFull(newRequired, key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("Exception in inserting key : " + key + " Page Number : " + page.getId());
		}
		return page;

	}

	public void splitChild(IndexNode parent, IndexNode child, int index) {
		parent.setLeaf(false);
		IndexNode newChild = newNode(child.isLeaf()); // new Page(nextPageId(),child.isLeaf());
		for (int k = 0; k < degree - 1; k++) { // copying second half of keys and childs to new child.
			newChild.addKey(child.deleteKey(k + degree));
		   }
		
		for(int k=0;k<degree;k++) {    //copying second half childs to new child.
			IndexNode popChild=child.deleteChild(k + degree);
			if(popChild!=null) {
				newChild.setChild(k, popChild);
			}
		  }
		parent.insertKey(child.deleteKey(degree - 1));
		for (int i = parent.getKeySize() - 1; i >= index + 1; i--) {
			parent.moveChild(i, i + 1);
		}
		parent.setChild(index + 1, newChild);
		newChild.setHalfFill();
		child.setHalfFill();
	}

	public void traverse(IndexNode page) {
		if (page != null) {
			System.out.println("Page:" + page.getId() + "&key size:" + page.getKeySize() + "  --" + page.getKeys());
			int i;
			for (i = 0; i < page.getKeySize(); i++) {
				if (page.isLeaf() == false) {
					IndexNode child=getPageTravarse(page.getChildId(i));
					traverse(child);
				 }
					
				// System.out.print(page.keys[i] + ",");
			}
			if (page.isLeaf() == false)
				traverse(page.getChild(i));
		}
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
			IndexNode childePage=getPageTravarse(page.getChildId(i));
			return search(childePage, key);

		} catch (Exception e) {
			System.out.println("Errop Page:" + page.getId() + " index" + position);
		}
		return null;
	}

	public void delete() {

	}

	public IndexNode newNode(boolean isLeaf ) {
		IndexNode node = new IndexNode(nextPageId(), isLeaf);
		unsavedPages.add(node);
		return node;
	}
	
	public IndexNode newNode(boolean isLeaf, int key ) {
		IndexNode node = new IndexNode(nextPageId(), isLeaf);
		node.addKey(key);
		unsavedPages.add(node);
		return node;
	}
	
	
	public IndexNode getPageTravarse(int pageid) {
		if (pageid == -1) {
			return null;
		} else if (pageid <= schemaInfo.getNoOfPages()) {
			IndexNode rpage=fileStore.readIdxNode(pageid);
			unsavedPages.add(rpage);
			return rpage;
     	}
		return null;
	}
	
	public IndexNode getPage(int pageid) {
		for (IndexNode page : unsavedPages) {
			if (page.getId() == pageid) {
				return page;
			}
		}
		if (pageid == -1) {
			return null;
		} else if (pageid <= schemaInfo.getNoOfPages()) {
			IndexNode rpage=fileStore.readIdxNode(pageid);
			unsavedPages.add(rpage);
			return rpage;
		} else {
			throw new IllegalArgumentException();
		}
	}

	  public int nextPageId() {
		return schemaInfo.nextPageId();
	  }

	 public void close() {
		    unsavedPages.forEach(page -> {
			fileStore.writeIdxNode(page);
	  });
		schemaInfo.persist();
	 }
}
