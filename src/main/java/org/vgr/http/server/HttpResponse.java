package org.vgr.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.app.util.JsonUtil;

public class HttpResponse  {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(HttpResponse.class);
    private HttpMethod method;
    private HttpStatus status=HttpStatus.OK; 
    private MimeType mimeType=MimeType.HTML;
    private String close="close";
	private static String NEW_LINE=" \n";
	private Map<String,String> data;
	
	private String filenName;
	
	Map<String,String> headers=null;
	public HttpResponse() {
		headers=new LinkedHashMap<String,String>();
	}
	
	
	public void writeResposne(Socket socket) {
		try {
			 PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			 printWriter.append("HTTP/1.1 "+status.getDesc() +NEW_LINE);
			 headers.put("Content-Type", mimeType.getMimeType());
			 headers.put("Date", new Date().toString());
			 headers.put("Server", "My Server/1.0 (Ubuntu)");
			 headers.put("Last-Modified", new Date().toString());
			 headers.put("Connection", this.close);
			 //headers.put("Content-Length", "");
			 //headers.put("Set-Cookie", "");
			 StringBuilder str=new StringBuilder();
			 headers.forEach((key,value)->{
				 str.append(key+": "+value+NEW_LINE);
			 });
			 printWriter.write(new String(str));
			 printWriter.append("\n\r"); 
			 
			 
			 switch (this.getMimeType()){
			 
			case TEXT:
				 String textData=this.getTextData(filenName);
				 int contentSize=textData.getBytes().length;
				 printWriter.append(data+"\n");
				 break;
			 
			 case JSON:
				   printWriter.append(JsonUtil.toJson(data)+"\n");
				   printWriter.flush();
				   printWriter.close();
				 
				 break ;
				 
			 case JS:
				 
				 String josnData=this.getTextData(filenName);
				  contentSize=josnData.getBytes().length;
				 printWriter.append(data+"\n");
				 break;
				 
			default :
				break;
				 
			 }
			 
			 printWriter.flush();
			 printWriter.close();
			 LOGGER.info("Flushed data and closed printwriter");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	


	
	public String getTextData(String fileName) {
		 InputStream is=this.getClass().getResourceAsStream(fileName);
		 try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
			 String line=null;
			 StringBuilder msg= new StringBuilder();
			 while((line=bufferedReader.readLine())!=null) {
				 msg.append(line+NEW_LINE);
			 }
			 return new String(msg);	
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	
    public MimeType getMimeType() {
		return mimeType;
	}
	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
	}
    public HttpStatus getStatus() {
        return this.status;
    }
	public HttpMethod getMethod() {
		return method;
	}
	public void setMethod(HttpMethod method) {
		this.method = method;
	}
	public Map<String,String> getData() {
		return data;
	}
	public void setData(Map<String,String> data) {
		this.data = data;
	}
	public String getFilenName() {
		return filenName;
	}
	public void setFilenName(String filenName) {
		this.filenName = filenName;
	}
	
	
	
}
