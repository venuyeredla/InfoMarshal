package org.vgr.store.rdbms;

import org.vgr.store.io.Bytes;

public interface Store {
	
	public void writeBlock(int blockNum, Bytes block);
	public Bytes readBlock(int blockNum);
	public void writeIdxNode(BtreeNode node);
	public BtreeNode readIdxNode(int nodeId);
	public void addToBuffer(int key,BtreeNode node);
	public String getPageList();

}
