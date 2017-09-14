package org.vgr.app.search;

public class EngData {
	
	private String id;
	private String url;
	private String word;
	private String meaning;
	private String example;
	private String source;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	public String getExample() {
		return example;
	}
	public void setExample(String example) {
		this.example = example;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Override
	public String toString() {
		return "EngData [id=" + id + ", url=" + url + ", word=" + word
				+ ", meaning=" + meaning + ", example=" + example + ", source="
				+ source + "]";
	}
 	
	

}
