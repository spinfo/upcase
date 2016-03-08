package de.uni_koeln.spinfo.drc.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

public class UploadForm {

	
	@NotNull
	private MultipartFile multiPart;
	
	@NotBlank
	private String workTitle;
	
	@NotBlank
	private String workAuthor;
	
	@URL
	@NotBlank
	private String source;
	
	@NotBlank
	private String language;
	
	@NotBlank
	private String encoding;
	
	
	public UploadForm() {

	}

	public UploadForm(MultipartFile multiPart, String workTitle, 
			String workAuthor, String source, String language, String encoding) {
		super();
		this.multiPart = multiPart;
		this.workTitle = workTitle;
		this.workAuthor = workAuthor;
		this.source = source;
		this.language = language;
		this.encoding = encoding;
	}

	public MultipartFile getMultiPart() {
		return multiPart;
	}

	public void setMultiPart(MultipartFile multiPart) {
		this.multiPart = multiPart;
	}

	public String getWorkTitle() {
		return workTitle;
	}

	public void setWorkTitle(String workTitle) {
		this.workTitle = workTitle;
	}

	public String getWorkAuthor() {
		return workAuthor;
	}

	public void setWorkAuthor(String workAuthor) {
		this.workAuthor = workAuthor;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((encoding == null) ? 0 : encoding.hashCode());
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		result = prime * result
				+ ((multiPart == null) ? 0 : multiPart.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result
				+ ((workAuthor == null) ? 0 : workAuthor.hashCode());
		result = prime * result
				+ ((workTitle == null) ? 0 : workTitle.hashCode());
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
		if (encoding != other.encoding)
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (multiPart == null) {
			if (other.multiPart != null)
				return false;
		} else if (!multiPart.equals(other.multiPart))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (workAuthor == null) {
			if (other.workAuthor != null)
				return false;
		} else if (!workAuthor.equals(other.workAuthor))
			return false;
		if (workTitle == null) {
			if (other.workTitle != null)
				return false;
		} else if (!workTitle.equals(other.workTitle))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UploadForm [multiPart=" + multiPart + ", workTitle="
				+ workTitle + ", workAuthor=" + workAuthor + ", source="
				+ source + ", language=" + language + ", encoding=" + encoding
				+ "]";
	}

}
