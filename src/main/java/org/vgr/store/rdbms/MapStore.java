package org.vgr.store.rdbms;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.store.io.ByteBuf;

public class MapStore implements Store {
	private static final Logger LOG = LoggerFactory.getLogger(MapStore.class);
	Map<Integer, ByteBuf> mapStore = null;
	Map<Integer, BtreeNode> bufferPages = null;

	public MapStore() {
		mapStore = new HashMap<>();
		bufferPages = new HashMap<>();
	}

	public void writeBlock(int blockNum, ByteBuf block) {
		mapStore.put(blockNum, block);
	}

	public ByteBuf readBlock(int blockNum) {
		ByteBuf block = mapStore.get(blockNum);
		return block;
	}

	public void addToBuffer(int key, BtreeNode node) {
		bufferPages.put(key, node);
	}

	public void writeIdxNode(BtreeNode node) {
		ByteBuf block = new ByteBuf();
		int pageType = node.isLeaf() ? 2 : 1;
		block.write(node.getId());// Page Number
		block.write((byte) pageType);
		block.write(node.getParentId());
		block.write(node.getKeySize());//
		for (int i = 0; i < node.getKeySize(); i++) {
			block.write(node.keyAt(i));
			block.write(node.getChildId(i));
		}
		// System.out.println("Writing page " + page.getId() + " at :" + offset);
		mapStore.put(node.getId(), block);
	}

	public BtreeNode readIdxNode(int nodeId) {
		try {
			if (bufferPages.containsKey(nodeId))
				return bufferPages.get(nodeId);
			else
				return null;
		} catch (Exception e) {
			LOG.info("Node doesn't exist : " + nodeId);
		}
		return null;
	}

	public String getPageList() {
		StringBuffer stringBuffer = new StringBuffer();
		bufferPages.keySet().forEach(key -> {
			stringBuffer.append(key + " , ");
		});
		return new String(stringBuffer);
	}

	public int getNodeOffset(int pageNum) {
		int offset = pageNum * ByteBuf.BLOCK_SIZE;
		return offset == -1 ? 0 : offset;
	}

	@Override
	public boolean isExisted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean writeTable(Table table,int pageNum) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public boolean writeSchemaInfo(SchemaInfo schemaInfo,int pageNum) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public SchemaInfo getSchemaInfo(int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeTableRow() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Table getTable(int pageid) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
