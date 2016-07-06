package de.uni_koeln.spinfo.upcase.mongodb.data.document.future;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ref_chapters")
public class Chapter {
	
	@Transient
	public static final String COLLECTION = "ref_chapters";
	
	@Id private String id;
	
	private String title;
	private Date lastModified;
	
//	@DBRef private List<Page> pages;
	private Set<String> pages;
	
	public Chapter(String title) {
		super();
		this.title = title;
		this.lastModified = new Date();
		this.pages = new HashSet<>();
	}

	public Chapter(String title, Set<String> pageIds) {
		super();
		this.title = title;
		this.lastModified = new Date();
		this.pages = pageIds;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Set<String> getPages() {
		return pages;
	}

	public void setPages(Set<String> pageIds) {
		this.pages = pageIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
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
		Chapter other = (Chapter) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastModified == null) {
			if (other.lastModified != null)
				return false;
		} else if (!lastModified.equals(other.lastModified))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Chapter [id=" + id + ", lastModified=" + lastModified + "]";
	}
	
}
