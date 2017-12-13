package org.vgr.store.rdbms;

import java.io.File;

import org.vgr.store.io.Bytes;
import org.vgr.store.io.DataReader;
import org.vgr.store.io.DataWriter;

public class FileStore  implements Store{
	private String dbFile=null;
	private DataWriter writer;
	private DataReader reader;
	private boolean existed;

	/*public FileStore() {
		boolean exists = new File(dbFile).exists();
		this.existed=exists;
		this.writer = new DataWriter(dbFile, exists);
		this.reader = new DataReader(dbFile);
	}*/

	public FileStore(String dbFile) {
		this.dbFile=dbFile;
		this.existed = new File(dbFile).exists();
		this.writer = new DataWriter(dbFile, this.existed);
		this.reader = new DataReader(dbFile);
	}

	public void writeBlock(int blockNum, Bytes block) {
		int offset=blockNum*Bytes.BLOCK_SIZE;
		writer.writeBlock(offset, block);
		writer.flush();
	 }
	
	public Bytes readBlock(int blockNum) {
		int offset=blockNum*Bytes.BLOCK_SIZE;
		Bytes block=reader.readBlock(offset);
		return block;
	}
	
	public void writeIdxNode(BtreeNode node) {
		Bytes block = new Bytes();
		int pageType = node.isLeaf() ? 2 : 1;
		block.write(node.getId());// Page Number
		block.write((byte) pageType);
		block.write(node.getParentId());
		block.write(node.getKeySize());//
		for (int i = 0; i < node.getKeySize(); i++) {
			block.write(node.keyAt(i));
			block.write(node.getChildId(i));
		}
		int offset = getNodeOffset(node.getId());
		//System.out.println("Writing page " + page.getId() + " at :" + offset);
		writer.writeBlock(offset, block);
		writer.flush();
	}
	
	public BtreeNode readIdxNode(int nodeId) {
		int offset = nodeId * Bytes.BLOCK_SIZE;
		Bytes block = reader.readBlock(offset);
		int pageNum = block.readInt();
		byte b = block.readByte();
		boolean isLeaf = b == 2 ? true : false;
		BtreeNode node = new BtreeNode(pageNum, isLeaf);
		node.setId(pageNum);
		node.setParentId(block.readInt());
		int keySize = block.readInt();
		for (int i = 0; i < keySize; i++) {
			node.add(block.readInt(),block.readInt());
		}
		return node;
	}
	
	public int getNodeOffset(int pageNum) {
		int offset = pageNum * Bytes.BLOCK_SIZE;
		return offset == -1 ? 0 : offset;
	}

	public boolean isExisted() {
		return existed;
	}

	public void setExisted(boolean existed) {
		this.existed = existed;
	}

	@Override
	public void addToBuffer(int key, BtreeNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPageList() {
		// TODO Auto-generated method stub
		return null;
	}
}
