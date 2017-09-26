package org.vgr.store.rdbms;

import java.util.LinkedHashMap;

import org.junit.Test;
import org.vgr.store.io.IoUtil;

public class TableTest {
	String fileName="/home/venugopal/Documents/Work/io/db/stud.table";

	@Test
	public void createTable() {
		Table table=new Table();
		table.setTableName("student");
		LinkedHashMap<String,String> map=new LinkedHashMap<String,String>();
		map.put("id", "Integer");
		map.put("name", "String");
		map.put("sub1", "Integer");
		map.put("sub2", "Integer");
		map.put("sub3", "Integer");
		table.setColumns(map);
		DBSchema schema=new DBSchema(IoUtil.getDw(fileName) , IoUtil.getDr(fileName));
		schema.createTable(table);
		Table t=schema.getTable();
		System.out.println("Table Name: "+t.getTableName());
		t.getColumns().forEach((key,value)->{
			System.out.println(key +" -- "+value);
		});
		
	}
	
	public void getTableInfo() {
		
	}

	
	
}
