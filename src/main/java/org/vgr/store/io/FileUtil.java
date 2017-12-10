package org.vgr.store.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class FileUtil {
	private static String WIN_DIR="C:\\Work\\opensource\\appdata\\";
	private static String LINUX_DIR="/home/venugopal/Documents/Work/io/";
	private static String MAC_DIR="/usr/local/data/";

	public static String getOs() {
		Map<String, String> sysEnv=System.getenv();  //.forEach((k,v)->System.out.println(k+":"+v));
		String os=sysEnv.get("OS");
		if(os!=null) {
			os=os.toLowerCase();
			if(os.startsWith("windows")) {
				return WIN_DIR;
			}
		}
	   if(os==null) {
			return MAC_DIR;
		}
		return LINUX_DIR;
	}
	
    public static String getPath(String fileName) {
    	    String DIR=getOs();
	    return DIR+fileName;
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
