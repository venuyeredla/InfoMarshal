package org.vgr.app.util;

public class JsonUtil {
	
	
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

}
