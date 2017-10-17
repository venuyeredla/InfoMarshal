package org.vgr.http.server;

import java.net.Socket;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.ioc.annot.Service;
import org.vgr.ioc.web.RequestDispatcher;

@Service(id="requestProcessor")
public class RequestProcessor implements Callable<String>{
	private static final Logger LOG=LoggerFactory.getLogger(RequestProcessor.class);
	
	String viewPath="/static/html/%viewname%.html";
	/*@Inject(ref="requestDispatcher")*/
	RequestDispatcher requestDispatcher=null;
	private Socket socket=null; 
	private HttpRequest httpRequest=null;
	private HttpResponse httpResponse=null;
	
	public RequestProcessor(Socket skt,HttpRequest request,HttpResponse response) {
			this.httpRequest=request;
			this.httpResponse=response;
			this.socket=skt;
	}

	@Override
	public String call() {
		String result="success";
		try {
			String uri=httpRequest.getUri();
			if(uri.startsWith("/static")) {
				this.setMimeType(uri);
				httpResponse.writeText(socket,uri);
			}else {
				String nextView=requestDispatcher.doRequestProcessing(httpRequest,httpResponse);
				if(httpResponse.getMimeType()==MimeType.JSON) {
					httpResponse.writeJson(socket);
					return result;
				}
				nextView=viewPath.replaceAll("%viewname%", nextView);
				LOG.info("Next view name is :"+nextView);
				httpResponse.writeText(socket,nextView);
			}
	   	    socket.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private void setMimeType(String uri) {
		String fileExt=uri.substring(uri.lastIndexOf(".")+1).toLowerCase();
		switch (fileExt) {
		case "js":
			httpResponse.setMimeType(MimeType.JS);
			break;
		case "html":
			httpResponse.setMimeType(MimeType.HTML);
			break;
		case "css":
			httpResponse.setMimeType(MimeType.CSS);
			break;
		case "jpg":
			httpResponse.setMimeType(MimeType.JPEG);
			break;
		default:
			break;
		}
		
	}
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public RequestDispatcher getRequestDispatcher() {
		return requestDispatcher;
	}

	public void setRequestDispatcher(RequestDispatcher requestDispatcher) {
		this.requestDispatcher = requestDispatcher;
	}
}
