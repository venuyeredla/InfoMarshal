package org.vgr.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.ioc.annot.Service;
import org.vgr.ioc.core.AppContext;
import org.vgr.ioc.core.ContainerAware;

/**
 * HttpServer starting point. SocketServer is started on given port and waits for HTTP requests.
 * Whenever there is new request, new socket is created and request processing is delegated to RequestProcessor. 
 */

@Service()
public class HttpServer implements Runnable,ContainerAware{
	private static final Logger LOG=LoggerFactory.getLogger(HttpServer.class);
   /**
     * Maximum time to wait on Socket.getInputStream().read() (in milliseconds)
     * This is required as the Keep-Alive HTTP connections would otherwise block
     * the socket reading thread forever (or as long the browser is open).
     */
	
    public static final int SOCKET_READ_TIMEOUT = 5000;
    
    private int SERVER_PORT=8080;
    
    ServerSocket serverSocket=null;
    ExecutorService executorService=Executors.newFixedThreadPool(10);
    
    private AppContext iocContainer;
    
	private boolean stopSignal=false;
	
	public HttpServer() {}
	public HttpServer(int port) {
		this.SERVER_PORT=port;
	}

	@Override
	public void run() {
	try{
		serverSocket=new ServerSocket(SERVER_PORT);
		LOG.info("Server Started at port: "+SERVER_PORT);
		while(!stopSignal) {
			 Thread.sleep(0);
			 serverSocket.setReuseAddress(true);
			 Socket requestSocket = serverSocket.accept();
		  	 this.handleRequest(requestSocket);
		   }
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean handleRequest(Socket socket) {
		RequestProcessor processor= (RequestProcessor)this.iocContainer.getBean("requestProcessor");
		processor.setRequestSocket(socket);
		
		Future<String> future = executorService.submit(processor);
		try {
			String futureValue  = future.get();
			LOG.info("Future value : {}",futureValue);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean start() {
		 Thread dThread= new Thread(this, "Server Deamon Thread");
         //dThread.setDaemon(true);
		 dThread.start();
		 return true;
	}
 
	public boolean stop() {
		this.stopSignal=true;
		LOG.info("Server Stop request submitted at port: "+SERVER_PORT);
		return true;
	}
	@Override
	public void setContainer(AppContext iocContainer) {
		this.iocContainer=iocContainer;
		
	}
	
}
