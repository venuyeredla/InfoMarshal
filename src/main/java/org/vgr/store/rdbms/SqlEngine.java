package org.vgr.store.rdbms;
import static org.vgr.store.io.IOConstants.*;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.store.io.ByteBuf;
import org.vgr.store.io.FileUtil;

public class SqlEngine implements Closeable {
	private static final Logger LOG = LoggerFactory.getLogger(SqlEngine.class);
	private static String DB_SUFFIX=".db";
	private FileStore fileStore=null;
	private SchemaInfo schemaInfo=null;
	private static int SCHEMA_PAGE_ID=0;
	private Map<String, BTreeIndex> tableIndexers=new HashMap<>();
	private Map<String, Table> tables=new HashMap<>();
	
	public SqlEngine(String schema, String userName, String passWord) {
		  LOG.info("DB engine started ");
		  LOG.info("OS block size is : "+BLOCK_SIZE);
		  schema=schema.toLowerCase();
		  String dbFile=FileUtil.getPath(schema+DB_SUFFIX);
		  fileStore=new FileStore(dbFile);	
		  initSchema(schema, userName, passWord);
	}
	
	private void initSchema(String schema, String userName, String passWord) {
		 boolean existed=fileStore.isExisted();
		 if(existed) {
			 LOG.info("Schema already existed and will be loaded...");
			 schemaInfo=fileStore.getSchemaInfo(SCHEMA_PAGE_ID);// loads existing schema 
		 }else {
			 schemaInfo=new SchemaInfo(schema, userName, passWord);
			 LOG.info("New scheama created. Name : "+schema);
		 }
	 }
	
	public void createTable(Table table) {
		 if(!schemaInfo.contains(table.getName())) {
			 table.setNum(schemaInfo.nextTableId());
			 int pageNum=schemaInfo.nextPage();
			 fileStore.writeTable(table, pageNum);
			 schemaInfo.addTable(table.getName(), pageNum);
			 LOG.info("Table created : "+table.getName() +" Page number : "+pageNum);
		 }else {
			 LOG.info("Table already existed : "+table.getName());
		 }
	 }

	public Table getTable(String tableName) {
		int pageNum=schemaInfo.tablePage(tableName);
		if(pageNum!=-1) {
			if (tables.containsKey(tableName)) {
				return tables.get(tableName);
			}else {
				Table table=fileStore.getTable(pageNum);
				table.setPageid(pageNum);
				LOG.info("Table loaded : "+table);
				tables.put(tableName, table);
				return table;
			}
			
		}else {
			LOG.info("Table doesn't exist : "+tableName);
			return null;
		}
	}
	
	public BTreeIndex getBtreeIndex(Table table) {
		BTreeIndex bTreeIndex=tableIndexers.get(table.getName());
		if(bTreeIndex!=null) {
			return bTreeIndex;
		}else {
			bTreeIndex=new BTreeIndex(this.schemaInfo, table,fileStore);;
			tableIndexers.put(table.getName(), bTreeIndex);
		}
		return bTreeIndex;
	}
	
	public void insert(String tableName,TableRow row) {
		Table table=this.getTable(tableName);
		BTreeIndex bTreeIndex=getBtreeIndex(table);
		ByteBuf bytes=new ByteBuf();
		if(table!=null) {
			table.getColumns().forEach(column->{
			   String name= column.getName();
			   Types type=column.getType();
	           row.wirteColumn(bytes, name, type);
			});
			int pageid=this.nextPage();
			fileStore.writeBlock(pageid, bytes);
			int key=row.getId();
			bTreeIndex.insert(key, pageid);
			table.inrcreRowSize();
			LOG.info("New row added to the table :"+table.getName());
		}
	}
	
	public TableRow select(String tableName,int id) {
		Table table=this.getTable(tableName);
		BTreeIndex bTreeIndex=new BTreeIndex(this.schemaInfo,table,fileStore);
		int pageid=bTreeIndex.search(id);
		if(pageid!=-1) {
			ByteBuf bytes=fileStore.readBlock(pageid);
			TableRow tableRow=new TableRow();
			table.getColumns().forEach(column->{
				   String name= column.getName();
				   Types type=column.getType();
				   tableRow.addColumn(bytes, name, type);
			});
			 LOG.info("Selected a row :"+table.getName());
			 try {
				bTreeIndex.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 return tableRow;
		}
		return null;
	}
	
	
	
	@Override
	public void close() throws IOException {
		tableIndexers.forEach((key,tableIndexer)->{
			try {
				tableIndexer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		tables.forEach((key,val)->{	fileStore.writeTable(val, val.getPageid());	});
		fileStore.writeSchemaInfo(this.schemaInfo, SCHEMA_PAGE_ID);
		fileStore.close();
	}
	
	
	private int nextPage() {
	   return schemaInfo.nextPage();
	 }
	
}
