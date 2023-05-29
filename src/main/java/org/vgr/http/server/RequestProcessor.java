package org.vgr.http.server;

import java.lang.reflect.Method;
import java.net.Socket;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.ioc.annot.Service;
import org.vgr.ioc.core.AppContext;
import org.vgr.ioc.core.ContainerAware;
import org.vgr.ioc.core.HandlerConfig;

@Service("requestProcessor")
public class RequestProcessor implements Callable<String>, ContainerAware{
	
	private static final Logger LOG=LoggerFactory.getLogger(RequestProcessor.class);
	
	AppContext iocContainer = null;
	
	String viewPath="/static/html/%viewname%.html";
	private Socket socket=null; 
	private HttpRequest httpRequest=null;
	private HttpResponse httpResponse=null;
	
	public RequestProcessor(Socket skt) {
			this.httpRequest=new HttpRequest(socket);
			this.httpResponse=new HttpResponse();
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
				String nextView=this.doRequestProcessing(httpRequest,httpResponse);
				if(httpResponse.getMimeType()==MimeType.JSON) {
					httpResponse.writeJson(socket);
					return result;
				}
				nextView=viewPath.replaceAll("%viewname%", nextView);
				LOG.debug("Next view name is :"+nextView);
				httpResponse.writeText(socket,nextView);
			}
	   	    socket.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	public String doRequestProcessing(HttpRequest request, HttpResponse response) {
		String viewName = "error";
		try {
			String servletPath = request.getUri();
			if (iocContainer.isValidPath(servletPath)) {
				HandlerConfig handlerConfig = iocContainer.getHandlerConfig(servletPath);
				response.setMimeType(handlerConfig.getMimeType());
				Object controller = iocContainer.getBean(handlerConfig.getController());
				Method method = controller.getClass().getMethod(handlerConfig.getMethod(),
						new Class[] { HttpRequest.class, HttpResponse.class });
				viewName = (String) method.invoke(controller, request, response);
				if (viewName.startsWith("redirect:")) {
					viewName = viewName.substring(9);
				}
				return viewName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewName;
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

	@Override
	public void setContainer(AppContext iocContainer) {
		this.iocContainer=iocContainer;
	}
	
}
