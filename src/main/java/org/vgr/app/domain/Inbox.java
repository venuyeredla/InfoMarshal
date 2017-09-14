package org.vgr.app.domain;

public class Inbox {
  public String fromMail;  
  public String subject;
  public StringBuilder message;
  
public String getFromMail() {
	return fromMail;
}
public void setFromMail(String fromMail) {
	this.fromMail = fromMail;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public StringBuilder getMessage() {
	return message;
}
public void setMessage(StringBuilder message) {
	this.message = message;
}

  
  
  
  
  
}
