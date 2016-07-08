package de.uni_koeln.spinfo.upcase.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Box;

public class WordUpdate {

	private String id;
	private String token;
	private Box box;
	
	@JsonProperty("page")
	private String pageId;
	
	public WordUpdate() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}
	
	public String getPageId() {
		return pageId;
	}
	
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	@Override
	public String toString() {
		return "WordUpdate [id=" + id + ", token=" + token + ", box=" + box + ", pageId=" + pageId + "]";
	}

}
