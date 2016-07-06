package de.uni_koeln.spinfo.upcase.mongodb.data.document.future;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ref_collections")
public class Collection {

	@Transient
	public static final String COLLECTION = "ref_collections";
	
	@Id private String id;

	private Date creationDate;
	private Date lastModified;
	private boolean contributable;
	private String title;

//	@DBRef private UpcaseUser owner;
	private String owner;
	
//	@DBRef private List<UpcaseUser> contributers;
	private Set<String> contributers;
	
//	@DBRef private List<Page> pages;
	private Set<String> pages;
	
//	@DBRef private List<Chapter> chapters;
//	@DBRef private List<Volume> volumes;

	
	public Collection() {
		// default constructor
	}
	
	public Collection(final String title) {
		this.title = title;
		this.creationDate = new Date();
		this.lastModified = new Date();
		this.contributable = false;
		this.pages = new HashSet<>();
		this.contributers = new HashSet<>();
	}
	
	public Collection(String title, UpcaseUser owner) {
		this.title = title;
		this.creationDate = new Date();
		this.lastModified = new Date();
		this.contributable = false;
		this.owner = owner.getId();
		this.pages = new HashSet<>();
		this.contributers = new HashSet<>();
//		this.chapters = new ArrayList<>();
//		this.volumes = new ArrayList<>();
	}
	
	public Collection(String title, UpcaseUser owner, Set<String> pageIds) {
		super();
		this.title = title;
		this.creationDate = new Date();
		this.lastModified = new Date();
		this.contributable = false;
		this.owner = owner.getId();
		this.pages = pageIds;
		this.contributers = new HashSet<>();
//		this.chapters = new ArrayList<>();
//		this.volumes = new ArrayList<>();
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public boolean isContributable() {
		return contributable;
	}

	public void setContributable(boolean contributable) {
		this.contributable = contributable;
	}

	public String getOwnerId() {
		return owner;
	}

	public void setOwner(UpcaseUser owner) {
		this.owner = owner.getId();
	}
	
	public void setOwner(final String ownerId) {
		this.owner = ownerId;
	}

	public Set<String> getContributers() {
		return contributers;
	}

	public void setContributers(Set<String> contributers) {
		this.contributers = contributers;
	}
	
	public void setContributer(final String contributorId) {
		this.contributers.add(contributorId);
	}
	
	public Set<String> getPages() {
		return pages;
	}

	public void setPages(Set<String> pages) {
		this.pages = pages;
	}

//	public List<Chapter> getChapters() {
//		return chapters;
//	}
//
//	public void setChapters(List<Chapter> chapters) {
//		this.chapters = chapters;
//	}
//
//	public List<Volume> getVolumes() {
//		return volumes;
//	}
//
//	public void setVolumes(List<Volume> volumes) {
//		this.volumes = volumes;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (contributable ? 1231 : 1237);
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Collection other = (Collection) obj;
		if (contributable != other.contributable)
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Collection [id=" + id + ", creationDate=" + creationDate + ", contributable=" + contributable
				+ ", title=" + title + ", pages=" + pages.size() + ", owner=" + owner + "]";
	}

	
}
