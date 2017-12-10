package org.vgr.store.rdbms;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.store.io.Bytes;

public class SchemaInfo {
	private static final Logger LOG = LoggerFactory.getLogger(Table.class);
	private static int SCHEMA_PAGE_ID=0;
	private int pageId=0;
	private String name;
	private String user;
	private String pass;
	private int pages;
	private boolean hasTables;
	private short tablesSize;
	private Map<String,Integer> tables;
	private boolean existed;
	/**
	 * Creates new schema and saves to the disk
	 * @param schemaName
	 * @param userName
	 * @param passWord
	 */
	public SchemaInfo(String name, String userName, String passWord) {
		this.name = name;
		this.user = userName;
		this.pass = passWord;
		this.pageId=0;
		this.pages=1;
		this.hasTables=false;
		this.tablesSize=0;
		tables=new HashMap<>();
	  }
    /**
     * Constructs the SchemaInfo by from the block of memory.
     * @param block
     */
	public SchemaInfo(FileStore fileStore) {
		Bytes block=fileStore.readBlock(SCHEMA_PAGE_ID);
		this.pageId=block.readInt();
		this.setSchemaName(block.readString());
		this.setUserName(block.readString());
		this.setPassWord(block.readString());
		this.pages=block.readInt();
		this.setHasTables(block.readBoolean());
		this.tablesSize=block.readShort();
		tables=new HashMap<>();
		if(hasTables) {
			byte tablesSize=block.readByte();
			for(int i=0;i<tablesSize;i++) {
				tables.put(block.readString(), block.readInt());	
			}
		}
	}
       /**
       * Returns the schema as Block of memory
       * @return
       */
	public boolean write(FileStore fileStore,int blockNum) {
		Bytes block = new Bytes();
		block.write(this.pageId)
		     .write(this.getSchemaName())
		     .write(this.getUserName())
		     .write(this.getPassWord())
		     .write(this.pages)
		     .write(this.hasTables)
		     .write(this.tablesSize);
		if(this.hasTables) {
			block.writeByte((byte)tables.size());
			this.tables.forEach((k,v)->{ block.write(k); block.write(v);});
		   }
		  fileStore.writeBlock(blockNum, block);
		  System.out.println("Total numuber of pages  : " + (this.pages));  
		 return true;
	   }
	  
	public int nextPage() {
		  return pages++;
	  }
	public int nextTableId() {
		  return tablesSize++;
	  }
	
	
	public boolean contains(String name) {
		return tables.keySet().contains(name);
	}
	
	public void addTable(String name, int pageid) {
			tables.put(name, pageid);
			tablesSize++;
			this.hasTables=true;
	}
	public int tablePage(String tableName) {
		if(tables.keySet().contains(tableName)) {
			return tables.get(tableName);
		}
		return -1;
	}
	
	
	public String getSchemaName() {
		return name;
	}
	public void setSchemaName(String schemaName) {
		this.name = schemaName;
	}
	public String getUserName() {
		return user;
	}
	public void setUserName(String userName) {
		this.user = userName;
	}
	public String getPassWord() {
		return pass;
	}
	public void setPassWord(String passWord) {
		this.pass = passWord;
	}
	public boolean isHasTables() {
		return hasTables;
	}
	public void setHasTables(boolean hasTables) {
		this.hasTables = hasTables;
	}
	public Map<String, Integer> getTables() {
		return tables;
	}
	public void setTables(Map<String, Integer> tables) {
		this.tables = tables;
	}
	public boolean isExisted() {
		return existed;
	}

	public void setExisted(boolean existed) {
		this.existed = existed;
	}
	
	public int getPages() {
		return pages;
	}
	@Override
	public String toString() {
		return "SchemaInfo [pageId=" + pageId + ", name=" + name + ", user=" + user + ", pass=" + pass + ", pages="
				+ pages + ", hasTables=" + hasTables + "]";
	}
}
