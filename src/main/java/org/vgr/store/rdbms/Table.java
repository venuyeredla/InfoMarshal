package org.vgr.store.rdbms;

import java.util.List;

import org.vgr.store.io.Block;

public class Table {
	private String name;
	private int num;
	private String primary;
	private List<Column> columns;

	public Table(String name, int num) {
		this.name = name;
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




	public int getNum() {
		return num;
	}




	public void setNum(int num) {
		this.num = num;
	}




	public String getPrimary() {
		return primary;
	}




	public void setPrimary(String primary) {
		this.primary = primary;
	}




	public List<Column> getColumns() {
		return columns;
	}




	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}




	public void wirteTable() {
		Block block=new Block();
		block.write(this.name);
		
	}
	
	
}
