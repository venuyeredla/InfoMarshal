package org.vgr.store.rdbms;

public enum Types {
	BYTE(0),SHORT(1),INT(2),LONG(3),STRING(4);
	Types(int num) {
		this.val=num;
	}
	private int val;
	public int getValue() {
		return this.val;
	}
 }
