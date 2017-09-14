package org.vgr.app.controllers;

import org.vgr.http.server.HttpRequest;
import org.vgr.http.server.HttpResponse;
import org.vgr.ioc.annot.Controller;
import org.vgr.ioc.annot.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller(id="searchController")
public class SearchController {
	private static final Logger LOG=LoggerFactory.getLogger(SearchController.class);	

	
	@Handler(path="/search.htm")
	public String search(HttpRequest request,HttpResponse response){
		return "search";
	}
	/*
	@Handler(path="/suggestions.htm")
	public String suggestion(HttpRequest request,HttpResponse response){
		String query=request.getParameter("query");
		String[] suggestions= luceneSearchService.getSuggestions(query);
		StringBuilder jsonString=JsonUtil.toJson("suggestions", suggestions);
		request.setAttribute("jsonString", jsonString);
		return "json";
	}
	@Handler(path="/sresults.htm")
	public String sesarchResults(HttpRequest request,HttpResponse response){
		String query=request.getParameter("query");
		System.out.println("Object :"+luceneSearchService);
		SearchResult searchResult= luceneSearchService.search("contents",query);
		StringBuilder jsonString=new StringBuilder(searchResult.toString());
		request.setAttribute("jsonString", jsonString);
		return "json";
	}

	public SearchService getLuceneSearchService() {
		return luceneSearchService;
	}

	public void setLuceneSearchService(SearchService luceneSearchService) {
		this.luceneSearchService = luceneSearchService;
	}*/
	
}




