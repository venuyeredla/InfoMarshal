package org.vgr.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	Map<String,String> values=new HashMap<String,String>();
	
	public void readData(Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		/*bufferedReader.lines().forEach(
				System.out::println
				);*/
		String line=bufferedReader.readLine();
		socket.shutdownInput();
	  	while(line!=null) {
			System.out.println(line);
			line=bufferedReader.readLine();
		}
	    System.out.println("Reading data finished");
	}

	public String getParameter(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAttribute(String string, Object obj) {
		// TODO Auto-generated method stub
		
	}

	public HttpSession getSession(boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

}
