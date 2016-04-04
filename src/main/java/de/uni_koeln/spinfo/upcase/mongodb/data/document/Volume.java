package de.uni_koeln.spinfo.drc.mongodb.data.document;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import de.uni_koeln.spinfo.drc.mongodb.data.core.RangeDocument;

@Document(collection = "volumes")
public class Volume extends RangeDocument {
	
	private String title;
	private int chapterNumber;
	
	public Volume() {
		super();
	}
	
	@PersistenceConstructor
	public Volume(final String title, int start, int end, final String userId) {
		super();
		this.title = title;
		setStart(start);
		setEnd(end);
		setUserId(userId);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(final String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Volume {title: " + title + ", _id: " + getId() + "}";
	}

	public void setChapterNumber(int chapterNumber) {
		this.chapterNumber = chapterNumber;
	}
	
	public int getChapterNumber() {
		return chapterNumber;
	}
}
