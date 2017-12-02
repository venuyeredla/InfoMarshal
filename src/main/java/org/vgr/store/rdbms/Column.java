package org.vgr.store.rdbms;

public class Column {
	private String name;
	private String id;
	private byte pos;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public byte getPos() {
		return pos;
	}
	public void setPos(byte pos) {
		this.pos = pos;
	}
 }
