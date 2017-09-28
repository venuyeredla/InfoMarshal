package org.vgr.store.rdbms;

import java.util.ArrayList;
import java.util.List;

import org.vgr.store.io.DataReader;
import org.vgr.store.io.DataWriter;

public class DBSchema {
	DataWriter dataWriter=null;
	DataReader dataReader=null;
	
	public DBSchema(DataWriter dw,DataReader dr) {
          this.dataWriter=dw;
          this.dataReader=dr;
	}
	
	public boolean createTable(TableOrView table) {
	     dataWriter.writeString(table.getTableName());
	     dataWriter.writeMap(table.getColumns());
		 dataWriter.close();
		 return true;
	}
	
	public TableOrView getTable() {
		TableOrView table=new TableOrView();
		table.setTableName(dataReader.readString());
		table.setColumns(dataReader.readMap());
		return table;
	}
	
	
	public List<TableOrView> getTables() {
		List<TableOrView> tables=new ArrayList<>();
		
		
		return tables;
		
	}

}
