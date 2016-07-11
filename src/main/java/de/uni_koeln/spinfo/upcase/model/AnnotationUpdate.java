package de.uni_koeln.spinfo.upcase.model;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnnotationUpdate {

	@JsonProperty("id")
	private String wordId;

	@JsonProperty("page")
	private String pageId;

	private Tag tag;

	public AnnotationUpdate() {
	}
	
	public AnnotationUpdate(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		AnnotationUpdate object = mapper.readValue(json, AnnotationUpdate.class);
		this.pageId = object.getPageId();
		this.wordId = object.getWordId();
		this.tag = object.getTag();
	}
	

	public String getWordId() {
		return wordId;
	}

	public void setWordId(String wordId) {
		this.wordId = wordId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "AnnotationUpdate [wordId=" + wordId + ", pageId=" + pageId + ", tag=" + tag + "]";
	}
	
}
