package org.vgr.app.domain;

public class Friend {
   private long id; 
   private String name;
   private String homeUrl;
   private String image;
   
public Friend(long id, String name, String homeUrl, String image) {
	super();
	this.id = id;
	this.name = name;
	this.homeUrl = homeUrl;
	this.image = image;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getHomeUrl() {
	return homeUrl;
}
public void setHomeUrl(String homeUrl) {
	this.homeUrl = homeUrl;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
   
}
