package org.vgr.store.rdbms;

import java.io.File;

import org.vgr.store.io.Block;
import org.vgr.store.io.DataReader;
import org.vgr.store.io.DataWriter;
import org.vgr.store.io.FileUtil;

public class FileStore {
	private String dbFile=FileUtil.getPath("data.db");
	private DataWriter writer;
	private DataReader reader;
	private boolean existed;
	
	
	public FileStore() {
		boolean exists = new File(dbFile).exists();
		this.existed=exists;
		this.writer = new DataWriter(dbFile, exists);
		this.reader = new DataReader(dbFile);
	}

	public FileStore(String dbFile) {
		this.dbFile=dbFile;
		boolean exists = new File(dbFile).exists();
		this.existed=exists;
		this.writer = new DataWriter(dbFile, exists);
		this.reader = new DataReader(dbFile);
	}

	public void writeBlock(int blockNum, Block block) {
		int offset=blockNum*Block.BLOCK_SIZE;
		writer.writeBlock(offset, block);
		writer.flush();
	 }
	
	public Block readBlock(int blockNum) {
		int offset=blockNum*Block.BLOCK_SIZE;
		Block block=reader.readBlock(offset);
		return block;
	}
	
	public void writeIdxNode(IndexNode node) {
		Block block = new Block();
		int pageType = node.isLeaf() ? 2 : 1;
		block.write(node.getId());// Page Number
		block.write((byte) pageType);
		if (node.getParent() != null) {
			block.write(node.getParent().getId());// Parent Page Number
		} else {
			block.write(-1);// Parent Page Number
		}
		block.write(node.getKeySize());//
		for (int i = 0; i < node.getKeySize(); i++) {
			block.write(node.getKey(i));
		}
		block.write(node.getChildSize());// ChildPages
		for (int i = 0; i < node.getChildSize(); i++) {
			block.write(node.getChildId(i));
		}
		int offset = getNodeOffset(node.getId());
		//System.out.println("Writing page " + page.getId() + " at :" + offset);
		writer.writeBlock(offset, block);
		writer.flush();
	}
	
	public IndexNode readIdxNode(int nodeId) {
		int offset = nodeId * Block.BLOCK_SIZE;
		Block block = reader.readBlock(offset);
		int pageNum = block.readInt();
		byte b = block.readByte();
		boolean isLeaf = b == 2 ? true : false;
		IndexNode node = new IndexNode(pageNum, isLeaf);
		node.setId(pageNum);
		node.setParentId(block.readInt());
		int keySize = block.readInt();
		for (int i = 0; i < keySize; i++) {
			node.addKey(block.readInt());
		}
		int childSize = block.readInt();
		for (int i = 0; i < childSize; i++) {
			node.setChildId(i, block.readInt());
		}
		return node;
	}
	
	public int getNodeOffset(int pageNum) {
		int offset = pageNum * Block.BLOCK_SIZE;
		return offset == -1 ? 0 : offset;
	}

	public boolean isExisted() {
		return existed;
	}

	public void setExisted(boolean existed) {
		this.existed = existed;
	}
}
