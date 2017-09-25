package org.vgr.http.server;

public enum MimeType {
	HTML("text/html"),
	TEXT("text/plain"),
	JS("text/javascript"),
	JSON("application/json"),
	CSS("text/css"),
	GIF(""),
	PNG(""),
	JPEG(""),
	BMP("image/bmp"),
	PDF("application/pdf"),
	XML("application/xml"),
	FORM("multipart/form-data"),
	JAVASCRIPT("application/js");
	
	private String type ;
	MimeType(String type){
		this.type=type;
	}
	 public String getMimeType() {
		 return this.type;
	 }
}
