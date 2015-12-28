package de.uni_koeln.spinfo.drc.mongodb.data.document;

import java.io.Serializable;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import de.uni_koeln.spinfo.drc.mongodb.data.core.RangeDocument;

@Document(collection = "chapters")
public class Chapter extends RangeDocument implements Serializable {

	private static final long serialVersionUID = 966219871511162636L;

	private String title;
	private String volumeId;
	
	@PersistenceConstructor
	public Chapter(final String title, int start, int end, final String userId) {
		super();
		this.title = title;
		setStart(start);
		setEnd(end);
		setUserId(userId);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	@Override
	public String toString() {
		return "Chapter {title: " + title + "}";
	}

}
