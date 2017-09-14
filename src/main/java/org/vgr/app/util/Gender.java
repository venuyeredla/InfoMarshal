package org.vgr.app.util;

public enum Gender {
	MALE(1),
	FEMALE(2);
	private int value;
	private Gender(int code){
		this.value=code;
	}
	
	public int getCode(){
		return this.value;
	}

}
