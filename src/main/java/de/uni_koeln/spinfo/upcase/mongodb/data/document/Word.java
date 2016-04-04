package de.uni_koeln.spinfo.upcase.mongodb.data.document;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import de.uni_koeln.spinfo.upcase.mongodb.data.PoS;
import de.uni_koeln.spinfo.upcase.mongodb.data.Rectangle;
import de.uni_koeln.spinfo.upcase.mongodb.data.Version;

@Document(collection = "words")
public class Word implements Serializable, Comparable<Word> {

	private static final long serialVersionUID = 5972674115082493109L;

	@Id
	private String id;
	
	private int index;
	private List<Version> versions;
	private List<PoS> posList;
	private Rectangle rectangle;
	
	private String pageId;
	private String chapterId;
	private String volumeId;
	private String languageId;
	private List<String> taggerPosOptions;
	
	

	@PersistenceConstructor
	public Word(int index, List<Version> versions, List<PoS> posList, Rectangle rectangle) {
		super();
		this.index = index;
		this.versions = versions;
		this.posList = posList;
		this.rectangle = rectangle;
	}

	public List<PoS> getPosList() {
		return posList;
	}

	public void setPosList(List<PoS> posList) {
		this.posList = posList;
	}

	public List<String> getTaggerPosOptions() {
		return taggerPosOptions;
	}

	public void setTaggerPosOptions(List<String> taggerPosOptions) {
		this.taggerPosOptions = taggerPosOptions;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<Version> getVersions() {
		Collections.sort(versions);
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}
	
	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	

	@Override
	public String toString() {
		return "Word [id=" + id + ", index=" + index + ", pageId=" + pageId
				+ ", chapterId=" + chapterId + ", volumeId=" + volumeId
				+ ", languageId=" + languageId + ", versions=" + versions
				+ ", posList=" + posList + ", rectangle=" + rectangle
				+ ", taggerPosOptions=" + taggerPosOptions + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (index != other.index)
			return false;
		return true;
	}

	public Version getCurrentVersion() {
		List<Version> tmp = getVersions();
		Collections.sort(tmp); // SORTED BY DATE
		return tmp.get(0);
	}
	
	public PoS getCurrentPos() {
		List<PoS> posList = getPosList();
		if(posList.size() > 0) {
			Collections.sort(posList); // SORTED BY DATE
			return posList.get(0);
		}
		PoS poS = new PoS();
		poS.setPosTag("not set");
		return poS;
	}

	@Override
	public int compareTo(Word o) {
		return Integer.valueOf(this.getIndex()).compareTo(Integer.valueOf(o.getIndex()));
	}

}
