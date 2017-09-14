package org.vgr.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.vgr.app.domain.Friend;
import org.vgr.app.service.FriendsService;
import org.vgr.http.server.HttpRequest;
import org.vgr.http.server.HttpResponse;
import org.vgr.ioc.annot.Controller;
import org.vgr.ioc.annot.Handler;
import org.vgr.ioc.annot.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller(id="friendsController")
public class FriendsController {
	private static final Logger LOG=LoggerFactory.getLogger(FriendsController.class);

   @Inject(ref="friendsService")
   private FriendsService friendsService=null;
	
   @Handler(path="/friends.htm")
  public String getFriends(HttpRequest servletRequest,HttpResponse servletResponse){
		List<Friend> friends=new ArrayList<Friend>();
		friends.add(new Friend(1, "Venugopal", "", "images/venu.jpg"));
		friends.add(new Friend(1, "Venkat",    "", "images/venu.jpg"));
		friends.add(new Friend(1, "Raghu",     "", "images/venu.jpg"));
		friends.add(new Friend(1, "Venugopal", "", "images/venu.jpg"));
		friends.add(new Friend(1, "Venugopal", "", "images/venu.jpg"));
		friends.add(new Friend(1, "Venugopal", "", "images/venu.jpg"));
		friends.add(new Friend(1, "Venugopal", "", "images/venu.jpg"));
		servletRequest.setAttribute("friends", friends);
		return "test";
	}
   @Handler(path="/friends1.htm")
  public String getFrineds1(HttpRequest servletRequest,HttpResponse servletResponse){
	   	servletRequest.setAttribute("friends", null);
		return "conent/test";
	}

public FriendsService getFriendsService() {
	return friendsService;
}

public void setFriendsService(FriendsService friendsService) {
	this.friendsService = friendsService;
}
  
}
