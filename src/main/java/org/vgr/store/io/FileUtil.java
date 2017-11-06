package org.vgr.store.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class FileUtil {
	private static String W_PATH="C:\\Work\\opensource\\appdata\\";
	protected static String L_PATH="/home/venugopal/Documents/Work/io/";
	private static String OS=null;
	private static String WINDOWS="windows";
	private static String LINUX="linux";
	
	public static String getOs() {
		Map<String, String> sysEnv=System.getenv();  //.forEach((k,v)->System.out.println(k+":"+v));
		String os=sysEnv.get("OS");
		if(os==null) {
			return LINUX;
		}
		return WINDOWS;
	}
	
	
    public static String getPath(String fileName) {
    	    String currentOs=getOs();
	   	    return currentOs.startsWith("windows")?W_PATH+fileName:L_PATH+fileName;
    }
    
    public static void getMemorInfo() {
    	ProcessBuilder builder=new ProcessBuilder("/sbin/fdisk");
    	builder.redirectErrorStream(true);
    	try {
		   Process process=builder.start();
		   BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String line;
	        while (true) {
	            line = r.readLine();
	            if (line == null) { break; }
	            System.out.println(line);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
