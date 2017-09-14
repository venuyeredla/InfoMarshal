package org.vgr.app.exceptions;

public class CustomDirectoryExcption extends Exception{

	private static final long serialVersionUID = 1L;

	public CustomDirectoryExcption(){	}
	
	public CustomDirectoryExcption(String message){
		super(message);
	}
}
