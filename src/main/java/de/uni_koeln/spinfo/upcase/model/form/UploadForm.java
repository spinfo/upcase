package de.uni_koeln.spinfo.upcase.model.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {

	private List<MultipartFile> files;
	private String collectionName;
	private String description;
	private String license;

	public UploadForm() {
		super();
	}

	public UploadForm(List<MultipartFile> files, String collectionName, final String license) {
		super();
		this.files = files;
		this.collectionName = collectionName;
		this.license = license;
	}

	public UploadForm(List<MultipartFile> files, String collectionName, String description, final String license) {
		super();
		this.files = files;
		this.collectionName = collectionName;
		this.description = description;
		this.license = license;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCollectionName() {
		return collectionName;
	}
	
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
	public String getLicense() {
		return license;
	}
	
	public void setLicense(String license) {
		this.license = license;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collectionName == null) ? 0 : collectionName.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		UploadForm other = (UploadForm) obj;
		if (collectionName == null) {
			if (other.collectionName != null)
				return false;
		} else if (!collectionName.equals(other.collectionName))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UploadForm [collectionName=" + collectionName + ", description=" + description + "]";
	}


}
