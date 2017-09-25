package org.vgr.ioc.web;

/**
 * Application Lifecycle Listener implementation class DBListener
 *
 */
public class AppListener    {
	/*public static Logger logger=LoggerFactory.getLogger(AppListener.class);
	
	ServletContext serveletContext=null;
	
    *//**
     * Default constructor. 
     *//*
    public AppListener() {
        // TODO Auto-generated constructor stub
    }
	*//**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     *//*
    public void contextInitialized(ServletContextEvent contextEvent) {
		try {
			serveletContext = contextEvent.getServletContext();
			//String configLocation = (String) serveletContext.getInitParameter("configLocation");
			URL url= serveletContext.getResource("/WEB-INF/classes.txt");
			logger.info("Context path:" +url.getFile() );
			serveletContext.setAttribute("ioc", container);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     *//*
    public void contextDestroyed(ServletContextEvent event) {
    	 serveletContext=event.getServletContext();
    	 serveletContext.removeAttribute("APP_CONFIG");
      }*/
}
