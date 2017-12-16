package org.vgr.store.rdbms;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.store.io.Bytes;
import org.vgr.store.io.FileUtil;

public class SqlEngine implements Closeable {
	private static final Logger LOG = LoggerFactory.getLogger(SqlEngine.class);
	private static String DB_SUFFIX=".db";
	private FileStore fileStore=null;
	private SchemaInfo schemaInfo=null;
	private static int SCHEMA_PAGE_ID=0;
	
	public SqlEngine(String schema, String userName, String passWord) {
		  LOG.info("DB engine started ");
		  LOG.info("OS block size is : "+Bytes.BLOCK_SIZE);
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
			 schemaInfo.addTable(table.getName(), pageNum);
			 fileStore.writeTable(table, pageNum);
			 LOG.info("Table created : "+table.getName() +" Page number : "+pageNum);
		 }else {
			 LOG.info("Table already existed : "+table.getName());
		 }
	 }

	public Table getTable(String tableName) {
		int pageNum=schemaInfo.tablePage(tableName);
		if(pageNum!=-1) {
			Table table=fileStore.getTable(pageNum);
			LOG.info("Table info loaded : "+table.getName());
			LOG.info("Table : "+table);
			return table;
		}else {
			LOG.info("Table doesn't exist : "+tableName);
			return null;
		}
	}
	
	public void insert(String tableName,TableRow row) {
		Table table=this.getTable(tableName);
		int pageNum=schemaInfo.tablePage(tableName);
		Bytes bytes=new Bytes();
		if(table!=null) {
			table.getColumns().forEach(column->{
			   String name= column.getName();
			   Types type=column.getType();
	           row.wirteColumn(bytes, name, type);
			});
			LOG.info("New row added to the table :"+table.getName());
		}
		int pageid=this.nextPage();
		fileStore.writeBlock(pageid, bytes);
		BTreeIndex bTreeIndex=new BTreeIndex(fileStore, table.getIndexRoot());
		int key=row.getId();
		bTreeIndex.insert(key, pageid);
		fileStore.writeTable(table, pageNum);
	}
	
	public TableRow select(String tableName,int id) {
		Table table=this.getTable(tableName);
		int pageid=id;
		Bytes bytes=fileStore.readBlock(pageid);
		TableRow tableRow=new TableRow();
		table.getColumns().forEach(column->{
			   String name= column.getName();
			   Types type=column.getType();
			   tableRow.addColumn(bytes, name, type);
		});
		 LOG.info("Selected a row :"+table.getName());
		return tableRow;
	}
	
	
	@Override
	public void close() throws IOException {
		fileStore.writeSchemaInfo(this.schemaInfo, SCHEMA_PAGE_ID);
		fileStore.close();
	}
	
	
	private int nextPage() {
	 return schemaInfo.nextPage();
		
	}
	
}
