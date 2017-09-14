package org.vgr.app.domain;

import java.util.List;

public class MailBox {

private String fromMail;
private String toMail;
private String fromPassword;
private String subject;
private String message;

public List<Inbox> mails=null;

public String getFromMail() {
	return fromMail;
}
public void setFromMail(String fromMail) {
	this.fromMail = fromMail;
}
public String getToMail() {
	return toMail;
}
public void setToMail(String toMail) {
	this.toMail = toMail;
}
public String getFromPassword() {
	return fromPassword;
}
public void setFromPassword(String fromPassword) {
	this.fromPassword = fromPassword;
}
public List<Inbox> getMails() {
	return mails;
}
public void setMails(List<Inbox> mails) {
	this.mails = mails;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}





}
