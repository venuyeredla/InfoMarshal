package org.vgr.store.rdbms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.vgr.app.util.RandomUtil;

public class SqlEngineTest {
	
	static SqlEngine sqlEngine;
	
	@BeforeClass
	public static void init() {
		sqlEngine=new SqlEngine("test", "tst", "tst");
	}
	
	@AfterClass
	public static void close() {
		try {
			sqlEngine.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void createTable() {
		Table table=new Table("Student");
		table.addColumn(new TableColumn("id", 0, Types.INT));
        table.addColumn(new TableColumn("name", 1, Types.STRING));
		table.addColumn(new TableColumn("age", 2, Types.INT));
		table.addColumn(new TableColumn("phone", 3, Types.STRING));
		table.addColumn(new TableColumn("email", 4, Types.STRING));
		table.addColumn(new TableColumn("job", 5, Types.STRING));
		table.addColumn(new TableColumn("city", 6, Types.STRING));
		table.setPrimary("id");
		sqlEngine.createTable(table);
	 }
	
	@Test
	@Ignore
	public void getTable() {
		sqlEngine.getTable("Student");
	 }
	
	@Test
	@Ignore
	public void insert() {
		for(int i=35;i<20000;i++) {
			TableRow row=new TableRow();
			row.addColumn("id",i);
			row.addColumn("name", "Venugopal"+i);
			row.addColumn("age", i+31);
			row.addColumn("phone", "9533277");
			row.addColumn("email", "venu@venu.org"+i);
			row.addColumn("job", "Programmer"+i);
			row.addColumn("city", "Hyderabad"+i);
			sqlEngine.insert("Student", row);
		}
	 }
	
	@Test
	public void select() {
		
		 HashSet<Integer> keySet=RandomUtil.randomNumsSet(5, 19999);
		 List<Integer> keyList=new ArrayList<>(keySet);
		 for (Integer integer : keyList) {
			 TableRow tableRow=sqlEngine.select("Student",integer);
			  System.out.println(tableRow);
		}
	}
	
	public void update() {
		String table="create table english";
	}

	public void drop() {
		String table="create table english";
	}
	
}
