package org.vgr.app.util;

import java.util.HashMap;
import java.util.Map;

public class JsonUtil {
	private static String QUOTE="\"";
	private static String COLON=":";
	private static String CAMA=",";
	
	
	public static StringBuilder toJson(String key, String[] elements){
		StringBuilder jsonString=new StringBuilder("{\""+key+"\":[");
		if(elements.length>=0){
		 for (int i = 0; i < elements.length; i++) {
			 jsonString.append("\""+elements[i]+"\",");
		}}
		else{
			 jsonString.append("[");
		}
		 jsonString.append("]}");
		 int len= jsonString.length()-3;
		 jsonString.replace(len, len+3, "]}");
		 return jsonString;
	}
	
	public static String toJson(Map<String,String> data) {
		StringBuilder json=new StringBuilder("{");
	  	data.forEach((key,val)->{
			json.append(QUOTE+key+QUOTE+COLON+QUOTE+val+QUOTE+CAMA);
		});
	  	int len=json.length();
	  	json.replace(len-1, len+1, "");
		json.append("}");
		return new String(json);
	}
}
