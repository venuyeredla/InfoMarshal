package org.vgr.store.rdbms;

import org.vgr.store.io.Bytes;

public interface Store {
	
	public void writeBlock(int blockNum, Bytes block);
	public Bytes readBlock(int blockNum);
	public void writeIdxNode(IndexNode node);
	public IndexNode readIdxNode(int nodeId);
	public void addToBuffer(int key,IndexNode node);

}
