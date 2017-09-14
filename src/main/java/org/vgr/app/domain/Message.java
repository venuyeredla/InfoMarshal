package org.vgr.app.domain;

public class Message {
	private String senderImage;
	private String senderName;
	private String msg;
	
	public Message(String senderImage, String senderName, String msg) {
		super();
		this.senderImage = senderImage;
		this.senderName = senderName;
		this.msg = msg;
	}
	public String getSenderImage() {
		return senderImage;
	}
	public void setSenderImage(String senderImage) {
		this.senderImage = senderImage;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
	

}
