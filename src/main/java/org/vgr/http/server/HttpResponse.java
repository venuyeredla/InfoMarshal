package org.vgr.http.server;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse implements Closeable {
	private static final Logger LOGGER=LoggerFactory.getLogger(HttpResponse.class);
    /**
     * copy of the header map with all the keys lowercase for faster searching.
     */
    private final Map<String, String> lowerCaseHeader = new HashMap<String, String>();

    /**
     * The request method that spawned this response.
     */
    private Method requestMethod;

    /**
     * Use chunkedTransfer
     */
    private boolean chunkedTransfer;

    private boolean keepAlive;

    private List<String> cookieHeaders;

    private GzipUsage gzipUsage = GzipUsage.DEFAULT;

    private static enum GzipUsage {
        DEFAULT,
        ALWAYS,
        NEVER;
    }
    /**
     * HTTP status code after processing, e.g. "200 OK", Status.OK
     */
    private Status status;

    /**
     * MIME type of content, e.g. "text/html"
     */
    private String mimeType;

    /**
     * Data of the response, may be null.
     */
    private InputStream data;

    private long contentLength;

    
	private static String NEW_LINE=" \n";

	public HttpResponse() {
	}
	
	public void writeData(Socket socket) throws IOException {
		         //BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		  		 PrintWriter printWriter=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		  		 String msg=this.getMsg();
  				 int length=msg.getBytes().length;
			     StringBuilder headerMsg=httpHeader("",length);
			     printWriter.append(headerMsg);
			     /*printWriter.flush();*/
			     printWriter.append("\n\r");
			     printWriter.append(msg);
			     printWriter.append("\n");
			     printWriter.flush();
			     printWriter.close();
			     socket.getOutputStream().close();
				 socket.shutdownOutput();				 
	}
	
	public StringBuilder httpHeader(String status,int contentLength) {
		 StringBuilder stringBuilder=new StringBuilder();
		 stringBuilder.append("HTTP/1.1 200 OK"+NEW_LINE);
		 stringBuilder.append("Date: "+new Date()+NEW_LINE);
		 stringBuilder.append("Server: HTTPSERVER/2.2.14 (Ubuntu)"+NEW_LINE);
		 stringBuilder.append("Last-Modified: "+new Date()+NEW_LINE);
		 /*stringBuilder.append("Content-Length: "+contentLength+NEW_LINE);*/
		 stringBuilder.append("Content-Type: text/html; charset=utf-8"+NEW_LINE);
		 stringBuilder.append("Connection: close"+NEW_LINE);
		 return stringBuilder;
	}

	public String getMsg() {
		 StringBuilder msg= new StringBuilder();
         msg.append("<html>");
		 msg.append("<body>");
		 msg.append("<h1>Hello, Venugopal is hero!</h1>");
		 msg.append("</body>");
		 msg.append("</html>");
		 return new String(msg);		 
	}
	
	
	
    /**
     * Headers for the HTTP response. Use addHeader() to add lines. the
     * lowercase map is automatically kept up to date.
     */
    @SuppressWarnings("serial")
    private final Map<String, String> header = new HashMap<String, String>() {

        public String put(String key, String value) {
            lowerCaseHeader.put(key == null ? key : key.toLowerCase(), value);
            return super.put(key, value);
        };
    };


    /**
     * Creates a fixed length response if totalBytes>=0, otherwise chunked.
     */
    @SuppressWarnings({
        "rawtypes",
        "unchecked"
    })
    protected HttpResponse(Status status, String mimeType, InputStream data, long totalBytes) {
        this.status = status;
        this.mimeType = mimeType;
        if (data == null) {
            this.data = new ByteArrayInputStream(new byte[0]);
            this.contentLength = 0L;
        } else {
            this.data = data;
            this.contentLength = totalBytes;
        }
        this.chunkedTransfer = this.contentLength < 0;
        this.keepAlive = true;
        this.cookieHeaders = new ArrayList(10);
    }

    @Override
    public void close() throws IOException {
        if (this.data != null) {
            this.data.close();
        }
    }

  
    /**
     * Sends given response to the socket.
     */
    public void send(OutputStream outputStream) {
        SimpleDateFormat gmtFrmt = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        gmtFrmt.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            if (this.status == null) {
                throw new Error("sendResponse(): Status can't be null.");
            }
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, new ContentType(this.mimeType).getEncoding())), false);
            pw.append("HTTP/1.1 ").append(this.status.getDescription()).append(" \r\n");
            if (this.mimeType != null) {
                printHeader(pw, "Content-Type", this.mimeType);
            }
            if (getHeader("date") == null) {
                printHeader(pw, "Date", gmtFrmt.format(new Date()));
            }
            for (Entry<String, String> entry : this.header.entrySet()) {
                printHeader(pw, entry.getKey(), entry.getValue());
            }
            for (String cookieHeader : this.cookieHeaders) {
                printHeader(pw, "Set-Cookie", cookieHeader);
            }
            if (getHeader("connection") == null) {
                printHeader(pw, "Connection", (this.keepAlive ? "keep-alive" : "close"));
            }
            if (getHeader("content-length") != null) {
                setUseGzip(false);
            }
            if (useGzipWhenAccepted()) {
                printHeader(pw, "Content-Encoding", "gzip");
                setChunkedTransfer(true);
            }
            long pending = this.data != null ? this.contentLength : 0;
            if (this.requestMethod != Method.HEAD && this.chunkedTransfer) {
                printHeader(pw, "Transfer-Encoding", "chunked");
            } else if (!useGzipWhenAccepted()) {
                pending = sendContentLengthHeaderIfNotAlreadyPresent(pw, pending);
            }
            pw.append("\r\n");
            pw.flush();
            sendBodyWithCorrectTransferAndEncoding(outputStream, pending);
            outputStream.flush();
           // NanoHTTPD.safeClose(this.data);
        } catch (IOException ioe) {
        	ioe.printStackTrace();
        	LOGGER.info("Could not send response to the client");
        }
    }

    @SuppressWarnings("static-method")
    protected void printHeader(PrintWriter pw, String key, String value) {
        pw.append(key).append(": ").append(value).append("\r\n");
    }

    protected long sendContentLengthHeaderIfNotAlreadyPresent(PrintWriter pw, long defaultSize) {
        String contentLengthString = getHeader("content-length");
        long size = defaultSize;
        if (contentLengthString != null) {
            try {
                size = Long.parseLong(contentLengthString);
            } catch (NumberFormatException ex) {
            	LOGGER.info("content-length was no number " + contentLengthString);
            }
        } else {
            pw.print("Content-Length: " + size + "\r\n");
        }
        return size;
    }

    private void sendBodyWithCorrectTransferAndEncoding(OutputStream outputStream, long pending) throws IOException {
        if (this.requestMethod != Method.HEAD && this.chunkedTransfer) {
            ChunkedOutputStream chunkedOutputStream = new ChunkedOutputStream(outputStream);
            sendBodyWithCorrectEncoding(chunkedOutputStream, -1);
            chunkedOutputStream.finish();
        } else {
            sendBodyWithCorrectEncoding(outputStream, pending);
        }
    }

    private void sendBodyWithCorrectEncoding(OutputStream outputStream, long pending) throws IOException {
        if (useGzipWhenAccepted()) {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
            sendBody(gzipOutputStream, -1);
            gzipOutputStream.finish();
        } else {
            sendBody(outputStream, pending);
        }
    }

    /**
     * Sends the body to the specified OutputStream. The pending parameter
     * limits the maximum amounts of bytes sent unless it is -1, in which case
     * everything is sent.
     * 
     * @param outputStream
     *            the OutputStream to send data to
     * @param pending
     *            -1 to send everything, otherwise sets a max limit to the
     *            number of bytes sent
     * @throws IOException
     *             if something goes wrong while sending the data.
     */
    private void sendBody(OutputStream outputStream, long pending) throws IOException {
        long BUFFER_SIZE = 16 * 1024;
        byte[] buff = new byte[(int) BUFFER_SIZE];
        boolean sendEverything = pending == -1;
        while (pending > 0 || sendEverything) {
            long bytesToRead = sendEverything ? BUFFER_SIZE : Math.min(pending, BUFFER_SIZE);
            int read = this.data.read(buff, 0, (int) bytesToRead);
            if (read <= 0) {
                break;
            }
            try {
                outputStream.write(buff, 0, read);
            } catch (Exception e) {
                if (this.data != null) {
                    this.data.close();
                }
            }
            if (!sendEverything) {
                pending -= read;
            }
        }
    }

    public void setChunkedTransfer(boolean chunkedTransfer) {
        this.chunkedTransfer = chunkedTransfer;
    }

    public void setData(InputStream data) {
        this.data = data;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setRequestMethod(Method requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Create a response with unknown length (using HTTP 1.1 chunking).
     */
    public static HttpResponse newChunkedResponse(Status status, String mimeType, InputStream data) {
        return new HttpResponse(status, mimeType, data, -1);
    }

    public static HttpResponse newFixedLengthResponse(Status status, String mimeType, byte[] data) {
        return newFixedLengthResponse(status, mimeType, new ByteArrayInputStream(data), data.length);
    }

    /**
     * Create a response with known length.
     */
    public static HttpResponse newFixedLengthResponse(Status status, String mimeType, InputStream data, long totalBytes) {
        return new HttpResponse(status, mimeType, data, totalBytes);
    }

    /**
     * Create a text response with known length.
     */
    public static HttpResponse newFixedLengthResponse(Status status, String mimeType, String txt) {
        ContentType contentType = new ContentType(mimeType);
        if (txt == null) {
            return newFixedLengthResponse(status, mimeType, new ByteArrayInputStream(new byte[0]), 0);
        } else {
            byte[] bytes;
            try {
                CharsetEncoder newEncoder = Charset.forName(contentType.getEncoding()).newEncoder();
                if (!newEncoder.canEncode(txt)) {
                    contentType = contentType.tryUTF8();
                }
                bytes = txt.getBytes(contentType.getEncoding());
            } catch (UnsupportedEncodingException e) {
            	LOGGER.info("encoding problem, responding nothing", e);
                bytes = new byte[0];
            }
            return newFixedLengthResponse(status, contentType.getContentTypeHeader(), new ByteArrayInputStream(bytes), bytes.length);
        }
    }

    /**
     * Create a text response with known length.
     */
    public static HttpResponse newFixedLengthResponse(String msg) {
        return newFixedLengthResponse(Status.OK, HttpServer.MIME_HTML, msg);
    }

    public HttpResponse setUseGzip(boolean useGzip) {
        gzipUsage = useGzip ? GzipUsage.ALWAYS : GzipUsage.NEVER;
        return this;
    }

    // If a Gzip usage has been enforced, use it.
    // Else decide whether or not to use Gzip.
    public boolean useGzipWhenAccepted() {
        if (gzipUsage == GzipUsage.DEFAULT)
            return getMimeType() != null && (getMimeType().toLowerCase().contains("text/") || getMimeType().toLowerCase().contains("/json"));
        else
            return gzipUsage == GzipUsage.ALWAYS;
    }

	public Object getParameterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addCookie(Cookie firstName) {
		// TODO Auto-generated method stub
		
	}
	
	  /**
     * Adds a cookie header to the list. Should not be called manually, this is
     * an internal utility.
     */
    public void addCookieHeader(String cookie) {
        cookieHeaders.add(cookie);
    }

    /**
     * Should not be called manually. This is an internally utility for JUnit
     * test purposes.
     * 
     * @return All unloaded cookie headers.
     */
    public List<String> getCookieHeaders() {
        return cookieHeaders;
    }

    /**
     * Adds given line to the header.
     */
    public void addHeader(String name, String value) {
        this.header.put(name, value);
    }

    /**
     * Indicate to close the connection after the Response has been sent.
     * 
     * @param close
     *            {@code true} to hint connection closing, {@code false} to let
     *            connection be closed by client.
     */
    public void closeConnection(boolean close) {
        if (close)
            this.header.put("connection", "close");
        else
            this.header.remove("connection");
    }

    /**
     * @return {@code true} if connection is to be closed after this Response
     *         has been sent.
     */
    public boolean isCloseConnection() {
        return "close".equals(getHeader("connection"));
    }

    public InputStream getData() {
        return this.data;
    }

    public String getHeader(String name) {
        return this.lowerCaseHeader.get(name.toLowerCase());
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public Method getRequestMethod() {
        return this.requestMethod;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setKeepAlive(boolean useKeepAlive) {
        this.keepAlive = useKeepAlive;
    }


}
