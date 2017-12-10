package org.vgr.store.rdbms;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class RdbmsTest {
	
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
			// TODO Auto-generated catch block
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
	public void insert() {
		TableRow row=new TableRow();
		row.addColumn("id", 1);
		row.addColumn("name", "Venugopal");
		row.addColumn("age", 31);
		row.addColumn("phone", "9533277");
		row.addColumn("email", "venu@venu.org");
		row.addColumn("job", "Programmer");
		row.addColumn("city", "Hyderbade");
		sqlEngine.insert("Student", row);
	}
	
	@Test
	@Ignore
	public void select() {
		insert();
		sqlEngine.select("Student",1);
		
	}
	
	
	

	public void update() {
		String table="create table english";
	}

	public void drop() {
		String table="create table english";
	}
	
}
