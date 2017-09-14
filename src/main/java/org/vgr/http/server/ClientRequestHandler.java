package org.vgr.http.server;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientRequestHandler {
	
	ExecutorService executorService=Executors.newFixedThreadPool(10);
	public boolean handleRequest(Socket socket) {
		RequestProcessor processor= new RequestProcessor(socket, new HttpRequest(), new HttpResponse());
		executorService.submit(processor);
		return true;
	}
}
