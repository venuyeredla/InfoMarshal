package org.vgr.store.io;

import java.util.Map;

public class FileUtil {
	private static String windows="C:\\Work\\opensource\\appdata\\";
	protected static String linux="/home/venugopal/Documents/Work/io/";
	
    public static String getPath(String fileName) {
			Map<String, String> sysEnv=System.getenv();  //.forEach((k,v)->System.out.println(k+":"+v));
			String os=sysEnv.get("OS").toLowerCase();
	   	    return os.startsWith("windows")?windows+fileName:linux+fileName;
    }
}
