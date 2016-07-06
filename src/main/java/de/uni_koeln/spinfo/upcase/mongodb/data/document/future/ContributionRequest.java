package de.uni_koeln.spinfo.upcase.mongodb.data.document.future;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "contribution_requests")
public class ContributionRequest {
	
	@Transient
	public static final String COLLECTION = "contribution_requests";
	
	@Id private String id;
	
	private Date creationDate;
	
//	@DBRef private Collection collection;
	private String collection;
	
//	@DBRef private UpcaseUser requestor;
	private String requestor;
	
//	@DBRef private UpcaseUser owner;
	private String owner;
	
	public ContributionRequest(Collection collection, String requestorId) {
		super();
		this.creationDate = new Date();
		this.collection = collection.getId();
		this.owner = collection.getOwnerId();
		this.requestor = requestorId;
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


	public String getCollection() {
		return collection;
	}


	public void setCollection(final String collectionId) {
		this.collection = collectionId;
	}


	public String getRequestor() {
		return requestor;
	}


	public void setRequestor(final String requestorId) {
		this.requestor = requestorId;
	}

	public String getOwner() {
		return owner;
	}
	
	public void setOwner(UpcaseUser owner) {
		this.owner = owner.getId();
	}

	
	public void setOwner(String ownerId) {
		this.owner = ownerId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ContributionRequest other = (ContributionRequest) obj;
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
		return true;
	}


	@Override
	public String toString() {
		return "ContributionRequest [id=" + id + ", creationDate=" + creationDate + ", collection=" + collection
				+ ", requestor=" + requestor + "]";
	}
	

}
