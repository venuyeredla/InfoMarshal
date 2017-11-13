package org.vgr.store.rdbms;

import java.util.LinkedHashMap;

import org.junit.Test;
import org.vgr.store.io.DataReader;
import org.vgr.store.io.DataWriter;
import org.vgr.store.io.FileUtil;

public class TableTest {
	String dbFile=FileUtil.getPath("stud.table");
	@Test
	public void createTable() {
		TableOrView table=new TableOrView();
		table.setTableName("student");
		LinkedHashMap<String,String> map=new LinkedHashMap<String,String>();
		map.put("id", "Integer");
		map.put("name", "String");
		map.put("sub1", "Integer");
		map.put("sub2", "Integer");
		map.put("sub3", "Integer");
		table.setColumns(map);
		DataWriter writer=new DataWriter(dbFile,false);
		DataReader reader=new DataReader(dbFile);		
		DBSchema schema=new DBSchema(writer,reader);
		schema.createTable(table);
		TableOrView t=schema.getTable();
		System.out.println("Table Name: "+t.getTableName());
		t.getColumns().forEach((key,value)->{
			System.out.println(key +" -- "+value);
		});
		
	}
	
	public void getTableInfo() {
		
	}

	
	
}
