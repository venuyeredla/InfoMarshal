package org.vgr.http.server;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpServer implements Runnable{
   /**
     * Maximum time to wait on Socket.getInputStream().read() (in milliseconds)
     * This is required as the Keep-Alive HTTP connections would otherwise block
     * the socket reading thread forever (or as long the browser is open).
     */
    public static final int SOCKET_READ_TIMEOUT = 5000;
    private int SERVER_PORT=8080;
    ServerSocket socketServer=null;
    /**
     * Common MIME type for dynamic content: plain text
     */
    public static final String MIME_PLAINTEXT = "text/plain";

    /**
     * Common MIME type for dynamic content: html
     */
    public static final String MIME_HTML = "text/html";
	
	ClientRequestHandler clientRequestHandler=new ClientRequestHandler();

	private boolean stopSignal=false;
	
	
	public HttpServer() {
	}
	
	public HttpServer(int port) {
		this.SERVER_PORT=port;
	}

	@Override
	public void run() {
	try{
		socketServer=new ServerSocket(SERVER_PORT);
		System.out.println("HttpSever Started on port : "+SERVER_PORT);
		while(!stopSignal) {
			 Thread.sleep(0);
		  	 socketServer.setReuseAddress(true);
		  	 clientRequestHandler.handleRequest(socketServer.accept());
		   }
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean start() {
		 Thread dThread= new Thread(new HttpServer(), "Server Deamon Thread");
       //  dThread.setDaemon(true);
		 dThread.start();
		 return true;
	}
 
	public boolean stop() {
		this.stopSignal=true;
		System.out.println("HttpSever Stopped on port:2017");
		return true;
	}
	
	
}
