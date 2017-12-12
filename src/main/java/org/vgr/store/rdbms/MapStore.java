package org.vgr.store.rdbms;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.store.io.Bytes;

public class MapStore implements Store{
	private static final Logger LOG = LoggerFactory.getLogger(MapStore.class);
	 Map<Integer,Bytes> mapStore=null;
	 Map<Integer,BtreeNode> bufferPages=null;
	 
	   public MapStore() {
	    mapStore=new HashMap<>();
	    bufferPages=new HashMap<>();
	   }
	 
	  public void writeBlock(int blockNum, Bytes block) {
		  mapStore.put(blockNum, block);
	  }
		
	   public Bytes readBlock(int blockNum) {
			Bytes block=mapStore.get(blockNum);
			return block;
	   }
	   
	   
	   public void addToBuffer(int key,BtreeNode node) {
		   bufferPages.put(key, node);
	   }
		
		public void writeIdxNode(BtreeNode node) {
			Bytes block = new Bytes();
			int pageType = node.isLeaf() ? 2 : 1;
			block.write(node.getId());// Page Number
			block.write((byte) pageType);
			block.write(node.getParentId());
			block.write(node.getKeySize());//
			for (int i = 0; i < node.getKeySize(); i++) {
				block.write(node.getKey(i));
				block.write(node.getChildId(i));
			}
			//System.out.println("Writing page " + page.getId() + " at :" + offset);
			mapStore.put(node.getId(), block);
		}
		
		public BtreeNode readIdxNode(int nodeId) {
			 try {
				 if(bufferPages.containsKey(nodeId)) {
					 return bufferPages.get(nodeId);
				 }else {
					    Bytes block = mapStore.get(nodeId);
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
			 }catch (Exception e) {
				LOG.info("Node doesn't exist : " + nodeId);
			}
			return null;
		}
		
		public String getPageList() {
			StringBuffer stringBuffer=new StringBuffer();
			bufferPages.keySet().forEach(key->{
				stringBuffer.append(key+" , ");
			});
			return new String(stringBuffer);
		}
		
		public int getNodeOffset(int pageNum) {
			int offset = pageNum * Bytes.BLOCK_SIZE;
			return offset == -1 ? 0 : offset;
		}


	 
	

}
