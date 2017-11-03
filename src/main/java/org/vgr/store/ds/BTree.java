package org.vgr.store.ds;

import org.vgr.store.io.Block;

/**
	 * B-Tree implementation 
	 * @author vyeredla
	 *
	 */
public class BTree {
	public Page rootPage;
	private int degree = Page.degree;
	/**
	 * Inserting key.
	 * @param key
	 * @param data
	 */
	public void insert(int key, long data) {
		rootPage = insert(rootPage, key, data);
	}

	public Page insert(Page page, int key, long data) {
		if (page == null) {
			page = new Page(true,key);
			return page;
		} else {
			if (page.isFull()) { // && page.isLeaf()
				Page newParent = new Page(false);
				newParent.setChild(0, page);
				splitChild(newParent, page, 0);
				int i = 0;
				if (newParent.getKey(0) < key) i++;
				insertNotFull(newParent.getChild(i), key);
				return newParent;  	//page = newParent;
			} else {
				insertNotFull(page, key);
				return page;
			}
		}
	}
	
	/**
	 * Key is inserted in node or page which is not full.
	 * @param page
	 * @param key
	 * @return
	 */
	private Page insertNotFull(Page page, int key) {
		try {
			int i = page.getKeySize() - 1;
			if (page.isLeaf() == true) {
				page.insertKey(key);
				return page;
			} else {// if this is not a leaf node.
				while (i >= 0 && page.getKey(i) > key)
					i--;
				if (page.getChild(i + 1).isFull()) {
					splitChild(page, page.getChild(i + 1), i + 1);
					if (page.getKey(i + 1) < key) i++;
				}
				return insertNotFull(page.getChild(i + 1), key);
			}
		}catch (Exception e) {
			System.out.println("Exception in inserting key : "+key +" Page Number : "+page.getPageNum());
		}
		return page;
	
	}
	public void splitChild(Page parent, Page child, int index) {
		parent.setLeaf(false);
		Page newChild = new Page(child.isLeaf());
		for (int k=0; k < degree - 1; k++) { // copying second half of keys and childs to new child.
			newChild.addKey(child.deleteKey(k+degree));
			newChild.setChild(k, child.deleteChild(k + degree));
			if(k==degree-2) { //since childs greater than keys
				k=k+1;
				newChild.setChild(k, child.deleteChild(k + degree));
			}
		 }
		parent.insertKey(child.deleteKey(degree-1));
		for(int i=parent.getKeySize()-1;i>=index+1;i--) {
			parent.setChild(i+1, parent.deleteChild(i));
		   }
		parent.setChild(index + 1, newChild);
		newChild.setHalfFill();
		child.setHalfFill();
	}
	
	public void traverse(Page page) {
		if (page != null) {
			System.out.println("Page:"+page.getPageNum()+"&key size:"+page.getKeySize()  + "  --"+page.getKeys());
			int i;
			for (i = 0; i < page.getKeySize(); i++) {
				if (page.isLeaf() == false)
					traverse(page.getChild(i));
				  //System.out.print(page.keys[i] + ",");
			}
			if (page.isLeaf() == false)
				traverse(page.getChild(i));
		 }
	}
	
	public Page search(Page page, int key) {
		 int position=0;
		try {
			int i = 0;
			while (i < page.getKeySize() && key > page.keys[i]) {
				i++;
			}
			position=page.keys[i];
			if (page.keys[i] == key)
				return page;

			if (page.isLeaf() == true) {
				return null;
			}
			return search(page.getChild(i), key);
			
		}catch (Exception e) {
			System.out.println("Errop Page:"+page.getPageNum()  +" index"+position);
		}
	return null;
	}

	public void delete() {

	}

	public void writePage(Page page) {
		Block bytes=new Block();
		bytes.write(page.getPageNum());//Page Number
		bytes.write(page.getParent().getPageNum());//Parent Page Number
		bytes.write(page.getKeySize());//
		for(int i=0;i<page.getKeySize();i++) {
			bytes.write(page.getKey(i));
		}
		bytes.write(page.getKeySize());//ChildPages
		for(int i=0;i<=page.getKeySize();i++) {
			bytes.write(page.getPageNum());
		}
	}
	
	public Page readPage() {
		Block bytes=null;
		return null;
	}
	
}
