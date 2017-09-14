package org.vgr.app.domain;

public class Image {
	
	private int Nbr;
	private String Header;
	private String path;
	private String desc;
	private int userId;
	
	public Image(){
	}
	public Image(int nbr, String header, String path, String desc) {
		super();
		Nbr = nbr;
		Header = header;
		this.path = path;
		this.desc = desc;
	}
	public int getNbr() {
		return Nbr;
	}
	public void setNbr(int nbr) {
		Nbr = nbr;
	}
	public String getHeader() {
		return Header;
	}
	public void setHeader(String header) {
		Header = header;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Image :{ Nbr :\"" + Nbr + "\", "
			         + "Header : \"" + Header +"\","
			         + "path :\"" + path+"\","
			         + "desc :\"" + desc +"\","
			         + "userId : \"" + userId + "\"}";
	}
}
