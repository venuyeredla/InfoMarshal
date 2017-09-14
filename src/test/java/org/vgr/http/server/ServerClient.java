package org.vgr.http.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerClient {
	
	static HttpServer   httpServer=null;
	
	@BeforeClass
	public static void startServer() {
		httpServer=new HttpServer();
		httpServer.start();
	}
	@AfterClass
	public static void stopServer() {
	   try {
		Thread.sleep(1000);
		httpServer.stop();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	}
	
	@Test
	public void testServer(){
		  String serverName = "localhost"; int port =8080;
	      try
	      {
	         System.out.println("Fetching data from--"+serverName+ ":" + port);
	         Socket client = new Socket(serverName, port);
	        // System.out.println("Just connected to " + client.getRemoteSocketAddress());
	         OutputStream outToServer = client.getOutputStream();
	         DataOutputStream out =    new DataOutputStream(outToServer);
	         out.writeUTF("Hello from "  + client.getLocalSocketAddress());
	         
	         InputStream inFromServer = client.getInputStream();
	         DataInputStream in = new DataInputStream(inFromServer);
	         System.out.println("Server says " + in.readUTF());
	         client.close();
	         
	      }catch(IOException e)
	      {
	         e.printStackTrace();
	      }
	      
	   
	
	}
}
