package de.uni_koeln.spinfo.upcase.mongodb.data.document.future;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ref_word_versions")
public class WordVersion {
	
	@Transient
	public static final String COLLECTION = "ref_word_versions";
	
	@Id private String id;
	private Date creationDate;
	private Word word;
	private String userId;
	
	public WordVersion() {
		// default constructor
		this.creationDate = new Date();
	}
	
	public WordVersion(Word word, final String userId) {
		this.creationDate = new Date();
		this.word = word;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		WordVersion other = (WordVersion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WordVersion [id=" + id + ", creationDate=" + creationDate + ", word=" + word + ", userId=" + userId
				+ "]";
	}

}
