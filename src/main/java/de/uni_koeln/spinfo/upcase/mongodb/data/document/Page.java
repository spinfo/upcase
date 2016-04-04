package de.uni_koeln.spinfo.drc.mongodb.data.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import de.uni_koeln.spinfo.drc.mongodb.data.core.RangeDocument;

@Document(collection = "pages")
public class Page extends RangeDocument implements Comparable<Page>,
		Serializable {

	private static final long serialVersionUID = -8489134538582298484L;
	private String url;
	private String printedPageNuber;
	private String volumeId;
	private List<String> chapterIds = new ArrayList<>();
	private List<String> languageIds = new ArrayList<>();
	private List<Word> words = new ArrayList<>();

	@PersistenceConstructor
	public Page(final String url, int start, int end, final String userId) {
		super();
		this.url = url;
		setStart(start);
		setEnd(end);
		setUserId(userId);
	}

	public String getPrintedPageNuber() {
		return printedPageNuber;
	}

	public void setPrintedPageNuber(final String printedPageNuber) {
		this.printedPageNuber = printedPageNuber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public List<String> getChapterIds() {
		return chapterIds;
	}

	public void setChapterIds(List<String> chapterIds) {
		this.chapterIds = chapterIds;
	}

	public boolean setChapterId(final String chapterId) {
		return this.chapterIds.add(chapterId);
	}

	public String getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	public boolean setLanguageId(final String languageId) {
		return languageIds.add(languageId);
	}

	public List<String> getLanguageIds() {
		return languageIds;
	}

	public void setLanguageIds(List<String> languageIds) {
		this.languageIds = languageIds;
	}

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}

	@Override
	public int compareTo(Page o) {
		return getUrl().compareToIgnoreCase(o.getUrl());
	}

	@Override
	public String toString() {
		return "Page {url: " + url + "}";
	}
}
