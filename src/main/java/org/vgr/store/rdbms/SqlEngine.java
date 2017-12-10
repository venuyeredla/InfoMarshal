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
			 schemaInfo=new SchemaInfo(fileStore); // loads existing schema 
		 }else {
			 schemaInfo=new SchemaInfo(schema, userName, passWord);
			 LOG.info("New scheama created. Name : "+schema);
		 }
	 }
	
	public void createTable(Table table) {
		 if(!schemaInfo.contains(table.getName())) {
			 table.setNum(schemaInfo.nextTableId());
			 int pageid=schemaInfo.nextPage();
			 schemaInfo.addTable(table.getName(), pageid);
			 table.write(fileStore, pageid);
			 LOG.info("Table created : "+table.getName() +" Page number : "+pageid);
		 }else {
			 LOG.info("Table already existed : "+table.getName());
		 }
	 }
	
	
	 public void getPageNum() {
		 BTreeIndex bTreeIndex=new BTreeIndex(fileStore,1);
		 
	 }
	
	
	
	
	public Table getTable(String tableName) {
		int id=schemaInfo.tablePage(tableName);
		if(id!=-1) {
			Table table=new Table(fileStore, id);
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
		Bytes bytes=new Bytes();
		if(table!=null) {
			table.getColumns().forEach(column->{
			   String name= column.getName();
			   Types type=column.getType();
	           row.wirteColumn(bytes, name, type);
			});
			LOG.info("New row added to the table :"+table.getName());
		}
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
		 schemaInfo.write(fileStore, 0);
	}
	
}
