package org.vgr.ioc.core;

import org.vgr.http.server.Filter;

/**
 * Servlet Filter implementation class UserLoginFilter
 */
public class SecurityFilter implements Filter {

	/*private ArrayList<String> urls=null;

	private static final Logger LOGGER=LoggerFactory.getLogger(SecurityFilter.class);
	
	*//**
	 * @see Filter#init(FilterConfig)
	 *//*
	public void init(FilterConfig fConfig) throws ServletException {
		this.urls=new ArrayList<String>();
		urls.add("/login.htm");
		urls.add("/home.htm");
		urls.add("/newprofile.htm");
		urls.add("/vmail.htm");
		urls.add("/suggestions.htm");
		urls.add("/sresults.htm");
	}
	
	 *//**
     * Default constructor. 
     *//*
    public SecurityFilter() {
        // TODO Auto-generated constructor stub
    }

	*//**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 *//*
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest httpRequest=(HttpServletRequest)request;
            HttpResponse httpResponse=(HttpResponse)response;
                String path=httpRequest.getServletPath();
                LOGGER.info("Requested url is : "+path);
                
                if(!urls.contains(path)){
                	  HttpSession httpSession= httpRequest.getSession(false);
                	  if(!((httpSession != null) &&((httpSession.getAttribute("valid")!=null &&  (Boolean)httpSession.getAttribute("valid"))))){
                		    LOGGER.info("Don't have acces to : {}",path);
                		    httpResponse.sendRedirect("/home.htm");
                		    return ;
                	   }
                      }
            chain.doFilter(request, response);
		}
	
	*//**
	 * @see Filter#destroy()
	 *//*
	public void destroy() {
		// TODO Auto-generated method stub
	}

*/
}
