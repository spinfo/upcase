package de.uni_koeln.spinfo.upcase.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnnotationUpdate {

	@JsonProperty("id")
	private String wordId;

	@JsonProperty("page")
	private String pageId;

	private Tag tag;

	public AnnotationUpdate() {

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
