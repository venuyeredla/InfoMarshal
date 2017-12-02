package org.vgr.store.rdbms;

public class Column {
	private String name;
	private int pos;
	private Types type;

	public Column(String name, int i, Types type) {
		this.name = name;
		this.pos = i;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(byte pos) {
		this.pos = pos;
	}
	public Types getType() {
		return type;
	}
	public void setType(Types type) {
		this.type = type;
	}
   
}
