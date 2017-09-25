package org.vgr.ioc.web;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.http.server.HttpRequest;
import org.vgr.http.server.HttpResponse;
import org.vgr.ioc.annot.Service;
import org.vgr.ioc.core.ContainerAware;
import org.vgr.ioc.core.IocContainer;

@Service(id="requestDispatcher")
public class RequestDispatcher implements ContainerAware {
	private static final Logger LOGGER=LoggerFactory.getLogger(RequestDispatcher.class);
	 
	IocContainer iocContainer=null;

    public void init(){
    	LOGGER.info("RequestDispatcher initilized");
			iocContainer=null;
	}
			
	public String doRequestProcessing(HttpRequest request,HttpResponse response){
		   String viewName="error";
				try {
			  		 String servletPath=request.getUri();
				     if(iocContainer.isValidPath(servletPath)) {
						    HandlerConfig handlerConfig=iocContainer.getHandlerConfig(servletPath);
						    response.setMimeType(handlerConfig.getMimeType());
							Object controller=iocContainer.getBean(handlerConfig.getController());
							Method method= controller.getClass().getMethod(handlerConfig.getMethod(), new Class[]{HttpRequest.class,HttpResponse.class});
							viewName=(String)method.invoke(controller, request,response);
				             if(viewName.startsWith("redirect:")) {
				            	 viewName=viewName.substring(9); 
				             }
				             return viewName;
					 }
										   
			      } catch (Exception e) {
     	        	 e.printStackTrace();
             	}
            return viewName;
	     }

	@Override
	public void setContainer(IocContainer iocContainer) {
      this.iocContainer=iocContainer;		
	}
	}
