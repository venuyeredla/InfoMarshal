package org.vgr.http.server;

import java.net.Socket;
import java.util.concurrent.Callable;

public class RequestProcessor implements Callable<String>{
	private Socket socket=null; 
	private HttpRequest httpRequest=null;
	private HttpResponse httpResponse=null;
	
	public RequestProcessor(Socket skt,HttpRequest request,HttpResponse response) {
			this.httpRequest=request;
			this.httpResponse=response;
			this.socket=skt;
	}

	@Override
	public String call() throws Exception {
		httpRequest.readData(socket);
		httpResponse.writeData(socket);
   	    socket.close();
		return "success";
	}

	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	

}
