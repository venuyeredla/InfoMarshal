package org.vgr.app.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.store.search.Request;
import org.vgr.store.search.Response;

public class Server implements Runnable {
	 private boolean flag=true;
	 private static Logger logger=LoggerFactory.getLogger(Server.class);

	 public static void main(String[] args){
	 if(new ServerListener().start()){
		 logger.info("Listener thread daemon started successfully");
	   }
		Thread bootStrap=new Thread(new Server(),"Boot-Strap Thread");
		bootStrap.start();
		  }

	@Override
	public void run() {
		while (flag) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}

 class ServerListener implements Runnable {
	 public static final int LISTENER_PORT=9000;
	 public static final int CONTROL_PORT=9001;
	
   private static Logger logger=LoggerFactory.getLogger(ServerListener.class);
	public boolean flag = true;
	private ServerSocket serverSocket=null;
	private RequestHandler requestHandler=null;
	
	public boolean start(){
		this.requestHandler=new RequestHandler("Empty");
		Thread thread=new Thread(this,"Server-Listener-Thread");
		thread.setDaemon(true);
		thread.start();
		return true;
	}
	@Override
	public void run() {
		    try {
				serverSocket=new ServerSocket(LISTENER_PORT);
				logger.info("Server Listner started and listening on port : "+LISTENER_PORT);
					while (flag) {
					try {
							Socket socket = serverSocket.accept();
							logger.info("Request from : "+socket.getRemoteSocketAddress());
							requestHandler.handleSocketRequest(socket);
					    } catch (SocketTimeoutException s) {
							System.out.println("Socket timed out!");
							break;
						} catch (IOException e) {
							e.printStackTrace();
							break;
						}
					}
				
			serverSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	}

}

 class ServerControlListener implements Runnable {
		private static Logger logger=LoggerFactory.getLogger(ServerControlListener.class);
		public boolean flag = true;
		private ServerSocket serverSocket=null;
		
		public ServerControlListener(int port, int timeout){
			try {
				serverSocket=new ServerSocket(port);
				/*serverSocket.setSoTimeout(500);*/
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (flag) {
				try {
				    logger.info("Waiting for control command");
					Socket server = serverSocket.accept();
					System.out.println("Just connected to "	+ server.getRemoteSocketAddress());
					DataInputStream in = new DataInputStream(server.getInputStream());
					System.out.println(in.readUTF());
					DataOutputStream out = new DataOutputStream(server.getOutputStream());
					out.writeUTF("Thank you for connecting to "+ server.getLocalSocketAddress() + "\nGoodbye!");
					server.close();
				} catch (SocketTimeoutException s) {
					System.out.println("Socket timed out!");
					break;
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}

			}

		}


	}

 
 
 class RequestWorker implements Runnable {
		
		private Request request=null;
		private Response response=null;
		private Socket socket=null;
		
		public RequestWorker(){
			
		}
		public RequestWorker(Request req, Response res, Socket soc){
			this.request=req;
			this.response=res;
			this.socket=soc;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}

	}

 
 class RequestHandler {
		private static Logger logger=LoggerFactory.getLogger(RequestHandler.class);
		
		private static final int NO_REQ_AND_RES_OBJECTS=5;
	    private static final int THREAD_POOL_SIZE=7;	
	    private static final int NO_WORKERS=10;	
		
		public Set<RequestWorker> requestHandlerThreads=null;
		public Set<Request> availableRequestPool=null;
		public Set<Response> availableResponsePool=null;
		public Set<Request> usedRequestPool=null;
		public Set<Response> usedResponsePool=null;
		public ExecutorService executorService=null;
		public Stack<RequestWorker> workers=null;
		
	   public	RequestHandler(){
		    availableRequestPool=new HashSet<Request>();
		    availableResponsePool=new HashSet<Response>();
		    requestHandlerThreads=new HashSet<RequestWorker>();
		    executorService=Executors.newFixedThreadPool(THREAD_POOL_SIZE);

		    for (int i = 0; i < NO_REQ_AND_RES_OBJECTS; i++) {
			   availableRequestPool.add(new Request());
			   availableResponsePool.add(new Response());
		    }
		   for (int i = 0; i <NO_WORKERS; i++) {
			   requestHandlerThreads.add(new RequestWorker());
	      	}
		}
	   public RequestHandler(String str){
		   
	   }
		
	      public void handleSocketRequest(Socket socket){
	    	  /*executorService.execute();*/
	    	  
	    	    System.out.println("Just connected to "	+ socket.getRemoteSocketAddress());
				try {
					DataInputStream in = new DataInputStream(socket.getInputStream());
					System.out.println(in.readUTF());
					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					out.writeUTF("Result is Thanks...." );
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	     public RequestWorker getWorkerFromPool(){
	    	// RequestWorker requestWorker=null;
	    	 
	    	 synchronized(this.availableRequestPool){
	    		 
	    		 if(this.availableRequestPool.size()>0){
	    		 }
	    	 }
	    	 return null;
	     }
	}
