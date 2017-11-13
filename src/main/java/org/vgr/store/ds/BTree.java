package org.vgr.store.ds;

import java.io.Closeable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.vgr.store.io.Block;
import org.vgr.store.io.DataReader;
import org.vgr.store.io.DataWriter;
import org.vgr.store.rdbms.ScheamaInfo;

     /**
	 * B-Tree implementation 
	 * @author vyeredla
	 *
	 */
 public class BTree implements Closeable {
	public Page rootPage;
	private int degree = Page.degree;
	private DataWriter writer;
	private DataReader reader;
	private ScheamaInfo schemaInfo=null;
	private List<Page> unsavedPages=null;
	public BTree(String dbFile) {
		boolean exists=new File(dbFile).exists();
		this.writer=new DataWriter(dbFile,exists);
		this.reader=new DataReader(dbFile);
		this.init(exists);
		unsavedPages=new ArrayList<>();
	}

	public void insert(int key, long data) {
		rootPage=getPage(schemaInfo.getRootPage());
		rootPage = insert(rootPage, key, data);
		schemaInfo.setRootPage(rootPage.getId());
	}
	public Page insert(Page page, int key, long data) {
		if (page == null) {
			page = new Page(nextPageId(),true,key);
			unsavedPages.add(page);
			return page;
		} else {
			if (page.isFull()) { // && page.isLeaf()
				Page newParent = new Page(nextPageId(),false);
				unsavedPages.add(newParent);
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
		Page newChild = new Page(nextPageId(),child.isLeaf());
		unsavedPages.add(newChild);
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
		Block block=new Block();
		int pageType= page.isLeaf()?2:1;
		block.write(page.getId());//Page Number
		block.write((byte)pageType);
		if(page.getParent()!=null) {
			block.write(page.getParent().getId());//Parent Page Number
		}else {
			block.write(-1);//Parent Page Number
		}
		block.write(page.getKeySize());//
		for(int i=0;i<page.getKeySize();i++) {
			block.write(page.getKey(i));
		}
		block.write(page.getKeySize()+1);//ChildPages
		for(int i=0;i<=page.getKeySize();i++) {
			block.write(page.getId());
		}
		int offset=getPageOffset(page.getId());
		System.out.println("Writing page "+page.getId()+" at :"+offset);
		writer.writeBlock(offset,block);
		writer.flush();
	}
	
	
	public Page getPage(int pageid) {
		if(pageid<=schemaInfo.getNoOfPages()) {
			return readPage(pageid);
		}else {
			throw new IllegalArgumentException();
		}
	}
	
	public Page readPage(int pageId) {
		 int offset=pageId*Block.BLOCK_SIZE;
		 Block block=reader.readBlock(offset);
		 int pageNum=block.readInt();
		 byte b=block.readByte();
		 boolean isLeaf=b==2?true:false;
		 Page page=new Page(pageNum,isLeaf);
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
	
	public int getPageOffset(int pageNum) {
		int offset=pageNum*Block.BLOCK_SIZE;
		return offset==-1?0:offset;
	}
	
	public int nextPageId() {
	   	return schemaInfo.nextPageId();
	}

	  public boolean init(boolean exists) {
		  try {
			  if(exists) {
				  readMeta(); 
			   }else {
				  initialize();
				  writeMeta();
			  }
			  return true;
		  }catch (Exception e) {
			 e.printStackTrace();
			  return false;
		   }
	  }
	
	 private void initialize() {
		schemaInfo=new ScheamaInfo("venudb", "venugopal", "venugopal");
		schemaInfo.setPageId(0);
	    schemaInfo.setNoOfPages(0);
	    schemaInfo.setHasIndex(false);
	    schemaInfo.setRootPage(-1);
	   }
	
	public void readMeta() {
		Block block=reader.readBlock(0);
		schemaInfo=new ScheamaInfo();
		schemaInfo.setPageId(block.readInt());
		schemaInfo.setSchemaName(block.readString());
		schemaInfo.setUserName(block.readString());
		schemaInfo.setPassWord(block.readString());
		schemaInfo.setNoOfPages(block.readInt());
	    int val=block.readByte();
	    boolean hasIndex=val==0?false:true;
	    schemaInfo.setHasIndex(hasIndex);
	    schemaInfo.setRootPage(block.readInt());
	}
	 
	public void writeMeta() {
		Block block=new Block();
	    block.write(schemaInfo.getPageId());
	    block.write(schemaInfo.getSchemaName());
	    block.write(schemaInfo.getUserName());
	    block.write(schemaInfo.getPassWord());
	    System.out.println("Total numuber of pages  : "+schemaInfo.getNoOfPages());
	    block.write(schemaInfo.getNoOfPages());
	    int b=schemaInfo.isHasIndex()?1:0;
	    block.write((byte)b);
	    block.write(schemaInfo.getRootPage());
	    writer.writeBlock(0,block);
		writer.flush();
	}

	public void closeWriter() {
		this.writer.close();
	}
	public void closeReader() {
		this.reader.close();
	}
	@Override
	public void close(){
		unsavedPages.forEach(page->{
            System.out.println("Writing page : "+page.getId());
			this.writePage(page);
		});
		this.writeMeta();
		this.writer.close();
		this.reader.close();
	  }
}
