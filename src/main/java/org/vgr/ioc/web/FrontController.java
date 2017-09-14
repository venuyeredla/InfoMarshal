package org.vgr.ioc.web;

/**
 * Servlet implementation class FrontController
 */
public class FrontController {
	/*private static final long serialVersionUID = 1L;
	private static final Logger LOGGER=LoggerFactory.getLogger(FrontController.class);
	 
	ViewResolver viewResolver=null;
	IocContainer iocContainer=null;

	*//**
     * @see HttpServlet#HttpServlet()
     *//*
    public FrontController() {
        super();
     }
	
    public void init(){
		try {
			super.init(config);
			viewResolver=new ViewResolver();
			iocContainer=(IocContainer)getServletContext().getAttribute("ioc");
		  } catch (ServletException e) {
			  e.printStackTrace();
			  LOGGER.error("Intialization of FrontController"+e);
		}
	}
			
	*//**
	 * @see HttpServlet#doGet(HttpRequest request, HttpResponse response)
	 *//*
	protected void doGet(HttpRequest request, HttpResponse response) throws ServletException, IOException {
			doRequestProcessing(request, response);
     	}

	*//**
	 * @see HttpServlet#doPost(HttpRequest request, HttpResponse response)
	 *//*
	protected void doPost(HttpRequest request, HttpResponse response) throws ServletException, IOException {
			doRequestProcessing(request, response);
	}

	private void  doRequestProcessing(HttpRequest request,HttpResponse response) throws ServletException, IOException{
				try {
					 String nextView=null;
			  		 String servletPath=request.getServletPath();
				     if(iocContainer.isValidPath(servletPath)) {
						    HandlerConfig configuration=iocContainer.getHandlerConfig(servletPath);
							Object controller=iocContainer.getBean(configuration.getController());
							Method method= controller.getClass().getMethod(configuration.getMethod(), new Class[]{HttpRequest.class,HttpResponse.class});
							nextView=(String)method.invoke(controller, request,response);
				             if(nextView.startsWith("redirect:")) {
				            	 nextView=nextView.substring(9); 
				            	 response.sendRedirect(nextView);
				             }
				             else{
				            	 nextView=viewResolver.getViewPath(nextView);  
				            	 RequestDispatcher dispatcher=request.getRequestDispatcher(nextView);
				 				 dispatcher.forward(request, response);
				             }
					 }
										   
			      } catch (Exception e) {
     	        	 e.printStackTrace();
             	}

	     }*/
	}
