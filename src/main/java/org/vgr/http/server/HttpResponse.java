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
	Map<String,String> headers=null;
	public HttpResponse() {
		headers=new LinkedHashMap<String,String>();
	}
	/**
	 * Reads files like Write text data like JS,html,css.
	 * @param socket
	 * @param fileName
	 * @throws IOException
	 */
	public void writeText(Socket socket,String fileName) throws IOException {
		 PrintWriter printWriter=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
 		 String data=this.getTextData(fileName);
		 int contentSize=data.getBytes().length;
         this.writeHeader(printWriter, contentSize);		 
	     printWriter.append(data+"\n");
	     printWriter.flush();
	     printWriter.close();
    }
	
	public void writeJson(Socket socket) throws IOException {
		 PrintWriter printWriter=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		 this.writeHeader(printWriter, 0);		 
	     printWriter.append(JsonUtil.toJson(data)+"\n");
	     printWriter.flush();
	     printWriter.close();
   }
	
	public void writeBinary(Socket socket) throws IOException {
		 PrintWriter printWriter=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		 this.writeHeader(printWriter, 0);		 
	     printWriter.append(JsonUtil.toJson(data)+"\n");
	     printWriter.flush();
	     printWriter.close();
  }     
	
	
	private void writeHeader(PrintWriter printWriter,int contentSize) {
		 headers.put("HTTP/1.1", status.getDesc());
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
	
}
