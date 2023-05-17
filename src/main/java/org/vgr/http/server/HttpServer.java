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
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.annot.Service;
import org.vgr.ioc.core.RequestDispatcher;

/**
 * HttpServer starting point. SocketServer is started on given port and waits for HTTP requests.
 * Whenever there is new request, new socket is created and request processing is delegated to RequestProcessor. 
 * @author vyeredla
 *
 */

@Service()
public class HttpServer implements Runnable{
	private static final Logger LOG=LoggerFactory.getLogger(HttpServer.class);
   /**
     * Maximum time to wait on Socket.getInputStream().read() (in milliseconds)
     * This is required as the Keep-Alive HTTP connections would otherwise block
     * the socket reading thread forever (or as long the browser is open).
     */
	
    public static final int SOCKET_READ_TIMEOUT = 5000;
    private int SERVER_PORT=2050;
    ServerSocket socketServer=null;
    ExecutorService executorService=Executors.newFixedThreadPool(10);
    
	@Inject("requestDispatcher")
	RequestDispatcher requestDispatcher=null;
	private boolean stopSignal=false;
	public HttpServer() {	}
	public HttpServer(int port) {
		this.SERVER_PORT=port;
	}

	@Override
	public void run() {
	try{
		socketServer=new ServerSocket(SERVER_PORT);
		LOG.info("Server Started at port: "+SERVER_PORT);
		while(!stopSignal) {
			 Thread.sleep(0);
		  	 socketServer.setReuseAddress(true);
		  	 this.handleRequest(socketServer.accept());
		   }
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}finally {
			try {
				socketServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean handleRequest(Socket socket) {
		RequestProcessor processor= new RequestProcessor(socket, new HttpRequest(socket), new HttpResponse());
		processor.setRequestDispatcher(requestDispatcher);
		Future<String> future = executorService.submit(processor);
		try {
			String string = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
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
	public void setRequestDispatcher(RequestDispatcher requestDispatcher) {
		this.requestDispatcher = requestDispatcher;
	}
}
