package org.vgr.app.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResult {

	private long totalHits=0;
	private long time=0;
	Map<String,Map<String,Object>> docs=null;
	Map<String,List<String>> facets=null;
	public SearchResult(){
		docs=new HashMap<String, Map<String,Object>>();
		facets=new HashMap<String, List<String>>();
		time=0;
	}

	public long getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(long totalHits) {
		this.totalHits = totalHits;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
   public boolean addDoc(String key, Map<String,Object> value){
	   docs.put(key, value);
	   return true ; 
   }
   public boolean addfacet(String key, List<String> value){
	   facets.put(key, value);
	   return true ; 
   }

@Override
public String toString() {
	StringBuilder builder=new StringBuilder("{\"totalHits\":"+totalHits);
	 builder.append(", \"time\":" + time);
	 builder.append(", \"docs\":[");
	 StringBuilder docsString=new StringBuilder();
	 for (String key : docs.keySet()) {
		 Map<String,Object> temp= docs.get(key);
		 docsString.append("{");
		 for (String key2 : temp.keySet()) {
			 if(key2.equals("teaser") || key2.equals("teaser1") ){
				 String val=(String)temp.get(key2);
				 String tempVal=val.replaceAll("\"", "\'");
				 docsString.append("\""+key2+"\":\""+tempVal+"\",");
			 }else{
				 docsString.append("\""+key2+"\":\""+temp.get(key2)+"\",");	 
			 }
			 
		  }
		 docsString.deleteCharAt(docsString.length()-1);
		 docsString.append("},");
	    }
	 
	 if(docsString.length()>2)
	 docsString.deleteCharAt(docsString.length()-1);
	 builder.append(docsString);
	 builder.append("]");
	 builder.append(", \"facets\":[");
	 
	 StringBuilder facetsString=new StringBuilder();
	 
	 for (String facetKey : facets.keySet()) {
		 facetsString.append("{\""+facetKey+"\":[");
		 for (String facetValue : facets.get(facetKey)) {
			 facetsString.append("\""+facetValue+"\",");
		}
		 facetsString.deleteCharAt(facetsString.length()-1);
		 facetsString.append("]}]");
	 	}
	 
	 builder.append(facetsString);
	 builder.append("}");
	
	
	
	return  builder.toString();
}
}
