package org.vgr.store.rdbms;

import java.util.ArrayList;

public class RdbmsTest {
	public void createTable() {
		Table table=new Table("Student", 1);
		ArrayList<Column> columns=new ArrayList<>();
		columns.add(new Column("id", 0, Types.INT));
		columns.add(new Column("name", 1, Types.STRING));
		columns.add(new Column("age", 2, Types.INT));
		columns.add(new Column("phone", 3, Types.STRING));
		columns.add(new Column("email", 4, Types.STRING));
		columns.add(new Column("job", 5, Types.STRING));
		columns.add(new Column("city", 6, Types.STRING));
		}
	
	public void insert() {
		String table="create table english";
	}

	public void update() {
		String table="create table english";
	}

	public void drop() {
		String table="create table english";
	}
	
}
