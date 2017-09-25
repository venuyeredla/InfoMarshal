package org.vgr.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	private Map<String,String> headers=new HashMap<String,String>();
	private Map<String,String> params=new HashMap<String,String>();
	private String method;
	private String HTTP_VERSION;
	private String uri;
	
	public HttpRequest(Socket socket) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			/*	bufferedReader.lines().forEach(
						System.out::println
						);*/
				String line=bufferedReader.readLine();
				socket.shutdownInput();
			  	while(line!=null && !line.equals("")) {
			  		if(line.startsWith("GET")) {
			  			parseRequest(line);
			  		}else {
			  			String[] header=line.split(":");
			  			headers.put(header[0].trim(), header[1].trim());
			  		}
			  		line=bufferedReader.readLine().trim();
			  		}
				System.out.println("Uri : "+getUri());	
			
		}catch (IOException ioe) {
			ioe.printStackTrace();
			}

	}
	private void parseRequest(String line) {
          try {
        	  String[] str=line.split(" ");
     		  method=str[0];
     		  HTTP_VERSION=str[2];
     		  String uriStr=str[1];
     		  if(uriStr.contains("?")) {
     			 String[] uriParts=uriStr.split("\\?");
        		  setUri(uriParts[0]);
        		  if(uriParts.length>0) {
        			 String queryString=uriParts[1];   
        			  Arrays.asList(queryString.split("&")).stream().forEach(s ->{
           			   String[] paramValues=s.split("=");
           			   params.put(paramValues[0].trim(), paramValues[1].trim());
           		  });
        		  }
     		  }else {
     			  setUri(uriStr);
     		  }
     		  
     		
        	  
          }catch(Exception e) {
        	  e.printStackTrace();
          }
		 
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getParameter(String string) {
		return params.get(string);
	}

	public void setAttribute(String string, Object obj) {
		// TODO Auto-generated method stub
		
	}

	public HttpSession getSession(boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

}
