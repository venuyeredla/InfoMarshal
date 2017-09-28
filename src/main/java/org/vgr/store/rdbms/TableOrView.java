package org.vgr.store.rdbms;

import java.util.LinkedHashMap;
public class TableOrView {
	private String tableName;
	private LinkedHashMap<String,String> columns;
	  
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public LinkedHashMap<String, String> getColumns() {
		return columns;
	}
	public void setColumns(LinkedHashMap<String, String> columns) {
		this.columns = columns;
	}
	  

}
