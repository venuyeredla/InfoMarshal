package org.vgr.store.rdbms;

public class ScheamaInfo {
	private int pageId;
	private String schemaName;
	private String userName;
	private String passWord;
	private int totalPages;
	private boolean hasIndex;
	private int rootPage;
	
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
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
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
	@Override
	public String toString() {
		return "ScheamaInfo [pageId=" + pageId + ", schemaName=" + schemaName + ", userName=" + userName + ", passWord="
				+ passWord + ", totalPages=" + totalPages + "]";
	}
   
}
