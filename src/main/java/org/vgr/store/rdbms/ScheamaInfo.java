package org.vgr.store.rdbms;

public class ScheamaInfo {
	private int pageId;
	private String schemaName;
	private String userName;
	private String passWord;
	private int noOfPages;
	private boolean hasIndex;
	private int rootPage;
	
	public ScheamaInfo() {
	}
	
	public ScheamaInfo(String schemaName, String userName, String passWord) {
		super();
		this.schemaName = schemaName;
		this.userName = userName;
		this.passWord = passWord;
	}
	public int getPageId() {
		return pageId;
	}
	public void setPageId(int pageId) {
		this.pageId = pageId;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getNoOfPages() {
		return noOfPages;
	}
	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
	}
	public boolean isHasIndex() {
		return hasIndex;
	}
	public void setHasIndex(boolean hasIndex) {
		this.hasIndex = hasIndex;
	}
	public int getRootPage() {
		return rootPage;
	}
	public void setRootPage(int rootPage) {
		this.rootPage = rootPage;
	}
	
	public int nextPageId() {
	   	return ++noOfPages;
	}
	@Override
	public String toString() {
		return "ScheamaInfo [pageId=" + pageId + ", schemaName=" + schemaName + ", userName=" + userName + ", passWord="
				+ passWord + ", noOfPages=" + noOfPages + ", hasIndex=" + hasIndex + ", rootPage=" + rootPage + "]";
	}
	

}
