package de.uni_koeln.spinfo.upcase.model;

import java.util.List;

import org.apache.lucene.document.Document;

public class SearchResult {

	private String imageUrl;
	private String ownerId;
	private String collectionId;
	private String pageId;
	private String contents;
	private String collectionTitle;
	private String collectionDescription;
	private List<String> quotations;
	

	public SearchResult() {
	}

	
	public SearchResult(String imageUrl, String ownerId, String collectionId, String pageId, String contents,
			String collectionTitle, String collectionDescription, List<String> quotations) {
		super();
		this.imageUrl = imageUrl;
		this.ownerId = ownerId;
		this.collectionId = collectionId;
		this.pageId = pageId;
		this.contents = contents;
		this.collectionTitle = collectionTitle;
		this.collectionDescription = collectionDescription;
		this.quotations = quotations;
	}


	public SearchResult(Document doc, List<String> quotations) {
		this.imageUrl = doc.get("imageUrl");
		this.ownerId = doc.get("ownerId");
		this.collectionId = doc.get("collectionId");
		this.pageId = doc.get("pageId");
		this.contents = doc.get("contents");
		this.collectionTitle = doc.get("collectionTitle");
		this.collectionDescription = doc.get("collectionDescription");
		this.quotations = quotations;
	}


	public List<String> getQuotations() {
		return quotations;
	}
	
	public void setQuotations(List<String> quotations) {
		this.quotations = quotations;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getCollectionTitle() {
		return collectionTitle;
	}

	public void setCollectionTitle(String collectionTitle) {
		this.collectionTitle = collectionTitle;
	}

	public String getCollectionDescription() {
		return collectionDescription;
	}

	public void setCollectionDescription(String collectionDescription) {
		this.collectionDescription = collectionDescription;
	}

	@Override
	public String toString() {
		return "SearchResult [imageUrl=" + imageUrl + ", ownerId=" + ownerId + ", collectionId=" + collectionId
				+ ", pageId=" + pageId + ", contents=" + contents + ", collectionTitle=" + collectionTitle
				+ ", collectionDescription=" + collectionDescription + "]";
	}
	
	
	

	
}
