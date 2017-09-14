package org.vgr.app.domain;

import java.util.Arrays;

public class UserInfo {
	private String name;
	private int[] values;
	private int key;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getValues() {
		return values;
	}
	public void setValues(int[] values) {
		this.values = values;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "UserInfo [name=" + name + ", values=" + Arrays.toString(values) + "]";
	}
}
