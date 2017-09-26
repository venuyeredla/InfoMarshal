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
	
	public boolean createTable(Table table) {
	     dataWriter.writeString(table.getTableName());
	     dataWriter.writeMap(table.getColumns());
		 dataWriter.close();
		 return true;
	}
	
	public Table getTable() {
		Table table=new Table();
		table.setTableName(dataReader.readString());
		table.setColumns(dataReader.readMap());
		return table;
	}
	
	
	public List<Table> getTables() {
		List<Table> tables=new ArrayList<>();
		
		
		return tables;
		
	}

}
