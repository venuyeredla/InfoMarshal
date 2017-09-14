package org.vgr.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.vgr.app.domain.Friend;
import org.vgr.app.domain.Message;
import org.vgr.http.server.HttpRequest;
import org.vgr.http.server.HttpResponse;
import org.vgr.ioc.annot.Controller;
import org.vgr.ioc.annot.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller(id="messageController")
public class MessageController {
	
	private static final Logger LOG=LoggerFactory.getLogger(MessageController.class);
	//private MessageService messageService=null;
	/**
	 * This mehtod returns the Home Page Of User
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 */
	@Handler(path="/things.htm")
   	public String getThings(HttpRequest servletRequest,HttpResponse servletResponse){
   		String option=servletRequest.getParameter("option");
   		String view="con/";
   		MessCatg catg=MessCatg.valueOf(option);
   		switch(catg){
   		   case msg:
   			       view=view+"msg";
   			   break;
   		   case friend:
		   			List<Friend> friends=new ArrayList<Friend>();
		   			friends.add(new Friend(1, "Venugopal", "", "images/venu.jpg"));
		   			friends.add(new Friend(1, "Venkat",    "", "images/venu.jpg"));
		   			friends.add(new Friend(1, "Raghu",     "", "images/venu.jpg"));
		   			friends.add(new Friend(1, "Venugopal", "", "images/venu.jpg"));
		   			friends.add(new Friend(1, "Venugopal", "", "images/venu.jpg"));
		   			friends.add(new Friend(1, "Venugopal", "", "images/venu.jpg"));
		   			friends.add(new Friend(1, "Venugopal", "", "images/venu.jpg"));
		   			servletRequest.setAttribute("friends", friends);
   			        view=view+"friend";
   			   break;
   		   case profile:
   			        view=view+"profile";
   			   break;
   	       case photos:
   	    	         view=view+"photos";
   			    break;
   	       case online:
   			         view=view+"online";
   				break;
   		}
      return view;		
	}
	@Handler(path="/messages.htm")
	public String getMessages(HttpRequest servletRequest,HttpResponse servletResponse){
		
		List<Message> messages=new ArrayList<Message>();
		
		messages.add(new Message("img/venu.jpg", "Venugopal", "Admire the beauty but don't desire it."));
		messages.add(new Message("img/venu.jpg", "Venugopal", "What is learned with please is learned full measure."));
		messages.add(new Message("img/venu.jpg", "Venugopal", "Enjoy while you learn and learn while you enjoy."));
		messages.add(new Message("img/venu.jpg", "Venugopal", "A person who learns more experience."));
		messages.add(new Message("img/venu.jpg", "Venugopal", "To much is too bad."));
		messages.add(new Message("img/venu.jpg", "Venugopal", "A person who learns more experience."));
		
		StringBuilder jsonString=new StringBuilder();
		jsonString.append("{\"messages\":[");
		 for(Message msg : messages) {
			 jsonString.append("{\"image\":\""+msg.getSenderImage()+"\" ,\"msg\":\""+msg.getMsg()+"\",\"name\":\""+msg.getSenderName()+"\" },");
		 }
		 jsonString.append("]}");
		 int len= jsonString.length()-3;
		 jsonString.replace(len, len+3, "]}");
		 servletRequest.setAttribute("jsonString", jsonString);
		 LOG.info("String:"+jsonString +"   path string:");
	    return "json";
	}
	
	@Handler(path="/admin.htm")
	public String getAdmin(HttpRequest servletRequest,HttpResponse servletResponse) {
				return "admin";
	}
/*	public MessageService getMessageService() {
		return messageService;
	}
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
*/
	
}
enum MessCatg {
	msg,friend,profile,online,photos;
}
