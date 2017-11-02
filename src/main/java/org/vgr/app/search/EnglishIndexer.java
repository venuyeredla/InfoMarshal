package org.vgr.app.search;

import org.vgr.ioc.annot.Service;

@Service(id="englishIndexer")
public class EnglishIndexer {/*
	
	public static void main(String...strings) throws IOException{
		EnglishIndexer extractor=new EnglishIndexer();
		extractor.getPhrasalVerbs();
		}
	
	//For indexing purpose
	public List<EngData> getAmercianSlangsList(){
		List<EngData> engDatas=new ArrayList<EngData>();
		Document document=null;
		Elements elements=null;
		EngData engData=null;
		try{
			// Reading data from Manythings.com
				for(int i=1;i<=19;i++){  //19
					String url="http://www.manythings.org/slang/slang"+i+".html";
					document=Jsoup.parse(new URL(url), 10000000);
					elements=document.select("dd");
					Elements slangs=document.select("dl>dt");
					Iterator<Element> slangsIterator=	slangs.iterator();
					Iterator<Element> examplesIterator=	elements.iterator();
					while (slangsIterator.hasNext() && examplesIterator.hasNext()) {
						engData=new EngData();
						Element slangElement=slangsIterator.next();
						Element slangSibling=slangElement.nextElementSibling();
						engData.setWord(slangElement.text());
						engData.setMeaning(slangSibling.select("select>option").get(1).text());
						
						Element examplElemnt=examplesIterator.next();
						examplElemnt.select("select").remove();
						engData.setExample(examplElemnt.text());
						engData.setSource("slang");
						engData.setUrl(url);
						engData.setId("slang-"+engData.getWord());
						engDatas.add(engData);
					}
				}
			
			List<Integer> excludeList=new ArrayList<Integer>();
			excludeList.add(20);
			for(int i=1;i<=5;i++){  //193
				 engData=new EngData();
				 String page=new Integer(i).toString();
				 if(page.length()==1)       page="00"+page;
				 else if(page.length()==2)  page="0"+page;
				 String url2="http://www.englishdaily626.com/slang.php?"+page;
				 document=Jsoup.parse(new URL(url2), 10000000);
				 elements=document.select(".changeSize-A >tbody");
			     Element name=document.getElementsByAttributeValue("href", "slang.php?"+page).get(0);
				 for (Element element : elements) {
				   Element definition=element.select("tr:eq(1)>td").get(1);
				   Element example=element.select("tr:eq(2)>td").get(1);
				   Elements exams= example.select("p");    
				   String text=null;
				   if(exams!=null & exams.size()>0){
					   text=exams.get(0).text();
				   }else {
					   text=example.text();
				   }
				   text=text.replaceAll(Pattern.quote("1)"), "");
				   text=text.replaceAll(Pattern.quote("2)"), "");
				   
				   engData.setId("slang-"+name.text());
				   engData.setWord(name.text());
				   engData.setMeaning(definition.text());
				   engData.setExample(text);
				   engData.setSource("slang");
				   engData.setUrl(url2);
				   engDatas.add(engData);
			    }	
			document=null;
			elements=null;
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
		}
		System.out.println("Slang Word :"+engDatas.size());
		return engDatas;
	}
	

	public List<EngData> getPhrasalVerbs(){
		List<EngData> engDatas=new ArrayList<EngData>();
		EngData engData=null;
		Document document=null;
		Elements elements=null;
		List<String> urlList=new ArrayList<String>();
		urlList.add("A");
		urlList.add("B");
		urlList.add("C");
		urlList.add("D");
		urlList.add("E-F");
		urlList.add("G");
		urlList.add("H");
		urlList.add("IJK");
		urlList.add("L");
		urlList.add("M-N");
		urlList.add("OPQ");
		urlList.add("R-S");
		urlList.add("T");
		urlList.add("U-Z");
		
		try{
			for (String urlPart : urlList) {
				System.out.print(urlPart+" ,");
				engData=new EngData();
				String url="http://www.learn-english-today.com/phrasal-verbs/phrasal-verbs_"+urlPart+".html";
				document=Jsoup.parse(new URL(url), 20000);
				elements=document.getElementsByClass("ph-verbs").get(0).getElementsByTag("tr");
				Element[] array=new Element[elements.size()];
                elements.toArray(array);
				for (int i = 1; i < array.length-3; i++) {
				Element temp=array[i];
				if(temp.getAllElements().size()>2){
					   engData.setId("phrasalverb-"+temp.child(0).text());
					   engData.setWord(temp.child(0).text());
					   engData.setMeaning(temp.child(1).text());
					   engData.setExample(temp.child(2).text());
					   engData.setSource("slang");
					   engData.setUrl(url);
					   engDatas.add(engData);
				}
			  }
			}
			document=null;
			elements=null;			
		
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
		}
		
		return engDatas;
	}
	
	public List<EngData> getIdioms(){
		List<EngData> engDatas=new ArrayList<EngData>();
		EngData engData=null;
		Document document=null;
		Elements elements=null;
		try{
			for(int i=1;i<=18;i++){
			  String page=new Integer(i).toString();
			 if(page.length()==1){
				 page="00"+page;
			 }else if(page.length()==2){
				 page="0"+page;
			 }
			String url="http://www.englishdaily626.com/idioms.php?"+page;
			document=Jsoup.parse(new URL(url), 20000);
			elements=document.select("table > tbody > tr > td >table:eq(3) > tbody > tr:eq(2) > td:eq(1) > table > tbody > tr:eq(0) > td:eq(1) >table:eq(2) > tbody > tr");
		   // Element name=document.getElementsByAttributeValue("href", "slang.php?"+page).get(0);
			for (Element element : elements) {
				engData=new EngData();
				 Elements texasfa=element.select("[bgcolor=#CCFFCC]");
				 if(texasfa!=null && texasfa.size()>0){
					 Element sibling=element.nextElementSibling();
					 List<String> colors=new ArrayList<String>();
					 colors.add("[color=#FF33CC]");
					 colors.add("[color=#FF00FF]");
					 colors.add("[color=#000080]");
					 String definition="";
					 for (String color : colors) {
						 definition=sibling.select(color).text();
						 if(!definition.equals("")){
							 break;
						 }
					}
					   engData.setId("idiom-"+texasfa.text());
					   engData.setWord(texasfa.text());
					   engData.setMeaning(definition);
					   engData.setExample(sibling.text());
					   engData.setSource("idiom");
					   engData.setUrl(url);
					   engDatas.add(engData);
					 
				 }
			    }	
			document=null;
			elements=null;
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			
		}
		System.out.println("Idiom :"+engDatas.size());
		return engDatas;
	}
	
	
		public void getConversation() throws IOException{
			Document document=null;
			Elements elements=null;
			FileWriter converseWriter=null;
			System.out.println("Getting Page :");
			try{
				converseWriter=new FileWriter("/home/venugopal/Downloads/English/converse/converse.odt");
				for(int i=1;i<=950;i++){
					if(i%25==0){
						System.out.println();
					}
				  System.out.print(i+" , ");
				  String page=new Integer(i).toString();
				 if(page.length()==1){
					 page="00"+page;
				 }else if(page.length()==2){
					 page="0"+page;
				 }
				document=Jsoup.parse(new URL("http://www.englishdaily626.com/conversation.php?"+page), 20000);
				elements=document.select("table:eq(1) > tbody > tr > td > table:eq(3) > tbody > tr:eq(2) > td:eq(1) > table > tbody > tr:eq(0) > td:eq(1)");
				converseWriter.write(elements.select("table > tbody > tr >td").get(0).text() +" \n\n");
				Elements conversation=null;
				int divSize=elements.select("div").size();
				int tableSize=elements.select("table").size();
				if(divSize==2){
					conversation=elements.select("div > table > tbody");
					if(conversation.size()>1){
					conversation.remove(1);
					}
					Elements temps=conversation.select("tr");
					temps.remove(0);
					for (Element converse :temps) {
						if(converse.select("td:eq(0)").text().equals("") && converse.select("td:eq(1)").text().equals("")){
						}else{
							converseWriter.write(converse.select("td:eq(0)").text()+" : "+converse.select("td:eq(1)").text()+"\n");	
						}
					}
				} else if(divSize==1){
					conversation=elements.select("table > tbody");
					Elements temps=conversation.select("tr");
					temps.remove(0);
					temps.remove(0);
					for (Element converse :temps) {
						if(converse.select("td:eq(0)").text().equals("") && converse.select("td:eq(1)").text().equals("")){
							break;
						}
						converseWriter.write(converse.select("td:eq(0)").text()+" : "+converse.select("td:eq(1)").text()+"\n");
					}
				} else if(tableSize>2){
					conversation=elements.select("table:eq(1) > tbody");
					Elements temps=conversation.select("tr");
					temps.remove(0);
					for (Element converse : temps) {
						if(converse.select("td:eq(0)").text().equals("") && converse.select("td:eq(1)").text().equals("")){
							break;
						}
						converseWriter.write(converse.select("td:eq(0)").text()+" : "+converse.select("td:eq(1)").text()+"\n");
					}
				}
				else{
					conversation=elements.select("table:eq(0) > tbody");
					Elements temps=conversation.select("tr");
					temps.remove(0);
					temps.remove(0);
					for (Element converse : temps) {
						converseWriter.write(converse.select("td:eq(0)").text()+" : "+converse.select("td:eq(1)").text()+"\n");
					}
				}
				
				elements.select("")
				document=null;
				elements=null;
				converseWriter.write("\n");
				}
			}catch(Exception exception){
				exception.printStackTrace();
			}finally{
				converseWriter.flush();
				converseWriter.close();
			}
		
	}
*/
}
