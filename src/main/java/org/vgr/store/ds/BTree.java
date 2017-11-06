package org.vgr.store.ds;

import org.vgr.store.io.Block;
import org.vgr.store.io.DataReader;
import org.vgr.store.io.DataWriter;
import org.vgr.store.rdbms.ScheamaInfo;

/**
	 * B-Tree implementation 
	 * @author vyeredla
	 *
	 */
public class BTree {
	public Page rootPage;
	private int degree = Page.degree;
	private DataWriter writer;
	private DataReader reader;
	private int blockSize=512; //for ubuntu
	private ScheamaInfo scheamaInfo=null;
	
	public BTree(DataWriter writer,DataReader reader) {
		this.writer=writer;
		this.reader=reader;
	}
	public BTree(DataWriter writer) {
		this.writer=writer;
	}
	public BTree(DataReader reader) {
		this.reader=reader;
	}

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
			System.out.println("Exception in inserting key : "+key +" Page Number : "+page.getId());
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
			System.out.println("Page:"+page.getId()+"&key size:"+page.getKeySize()  + "  --"+page.getKeys());
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
			System.out.println("Errop Page:"+page.getId()  +" index"+position);
		}
	return null;
	}

	public void delete() {

	}

	public void writePage(Page page) {
		Block block=new Block(blockSize);
		int pageType= page.isLeaf()?2:1;
		block.write(page.getId());//Page Number
		block.write((byte)pageType);
		block.write(page.getParent().getId());//Parent Page Number
		block.write(page.getKeySize());//
		for(int i=0;i<page.getKeySize();i++) {
			block.write(page.getKey(i));
		}
		block.write(page.getKeySize()+1);//ChildPages
		for(int i=0;i<=page.getKeySize();i++) {
			block.write(page.getId());
		}
		writer.writeBlock(0,block);
		writer.flush();
	}
	
	public Page readPage(int pageId) {
		 int offset=pageId*blockSize+1;
		 Block block=reader.readBlock(offset, blockSize);
		 int pageNum=block.readInt();
		 byte b=block.readByte();
		 boolean isLeaf=b==2?true:false;
		 Page page=new Page(isLeaf);
		 page.setId(pageNum);
		 page.setParentId(block.readInt());
		 int keySize=block.readInt();
		 for(int i=0;i<keySize;i++) {
			 page.addKey(block.readInt());
		 }
		 int childSize=block.readInt();
		 for(int i=0;i<childSize;i++) {
			 page.setChildId(i,block.readInt());
		 }
		return page;
	}
	
	 public void metaData() {
			scheamaInfo=new ScheamaInfo();
			scheamaInfo.setPageId(1);
			scheamaInfo.setSchemaName("venudb");
	        scheamaInfo.setUserName("venugopal");
	        scheamaInfo.setPassWord("venugopal");
		    scheamaInfo.setTotalPages(1);
		    scheamaInfo.setHasIndex(false);
		    scheamaInfo.setRootPage(0);
	   }
	
	public void writeMeta() {
		Block block=new Block(blockSize);
	    block.write(scheamaInfo.getPageId());
	    block.write(scheamaInfo.getSchemaName());
	    block.write(scheamaInfo.getUserName());
	    block.write(scheamaInfo.getPassWord());
	    block.write(scheamaInfo.getTotalPages());
	    int b=scheamaInfo.isHasIndex()?1:0;
	    block.write((byte)b);
	    block.write(scheamaInfo.getRootPage());
	    writer.writeBlock(0,block);
		writer.flush();
	}
	public void readMeta() {
		Block block=reader.readBlock(0, this.blockSize);
		ScheamaInfo scheamaInfo=new ScheamaInfo();
		scheamaInfo.setPageId(block.readInt());
		scheamaInfo.setSchemaName(block.readString());
        scheamaInfo.setUserName(block.readString());
        scheamaInfo.setPassWord(block.readString());
	    scheamaInfo.setTotalPages(block.readInt());
	    int val=block.readByte();
	    boolean hasIndex=val==0?false:true;
	    scheamaInfo.setHasIndex(hasIndex);
	    scheamaInfo.setRootPage(block.readInt());
	}
	
	public void closeWriter() {
		this.writer.close();
		
	}
	public void closeReader() {
		this.reader.close();
		
	}
	
}
