package org.vgr.store.rdbms;

public abstract class Node {
	protected int id;
	protected int parentId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	
	
	
}
