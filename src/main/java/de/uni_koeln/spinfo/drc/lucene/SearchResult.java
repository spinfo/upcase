package de.uni_koeln.spinfo.drc.lucene;

/**
 * Container to wrap up page infos to be displayed in a result view.
 * 
 * @author spinfo
 *
 */
public class SearchResult {

	private String filename;
	private String pageId;
	private String content;
	private String language;
	private String chapter;
	private String volume;
	private String chapterId;
	private String volumeId;
	private String quotation;
	private String url;

	public SearchResult() {
	}

	/**
	 * Full constructor.
	 * 
	 * @param filename
	 * @param pageId
	 * @param content
	 * @param language
	 * @param chapterId
	 * @param chapter
	 * @param volumeId
	 * @param volume
	 */
	public SearchResult(String filename, String pageId, String content,
			String language, String chapterId, String chapter, String volumeId,
			String volume, String quotation) {
		this.filename = filename;
		this.pageId = pageId;
		this.content = content;
		this.language = language;
		this.chapterId = chapterId;
		this.chapter = chapter;
		this.volumeId = volumeId;
		this.volume = volume;
		this.quotation = quotation;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the pageId
	 */
	public String getPageId() {
		return pageId;
	}

	/**
	 * @param pageId
	 *            the pageId to set
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the chapter
	 */
	public String getChapter() {
		return chapter;
	}

	/**
	 * @param chapter
	 *            the chapter to set
	 */
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	/**
	 * @return the volume
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 *            the volume to set
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}

	/**
	 * @return the chapterId
	 */
	public String getChapterId() {
		return chapterId;
	}

	/**
	 * @param chapterId
	 *            the chapterId to set
	 */
	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	/**
	 * @return the volumeId
	 */
	public String getVolumeId() {
		return volumeId;
	}

	/**
	 * @param volumeId
	 *            the volumeId to set
	 */
	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	public String getQuotation() {
		return quotation;
	}

	public void setQuotation(String quotation) {
		this.quotation = quotation;
	}

	@Override
	public String toString() {
		return "SearchResult [filename=" + filename + ", pageId=" + pageId
				+ ", content="
				+ content.substring(0, Math.min(50, content.length()))
				+ ", language=" + language + ", chapter=" + chapter
				+ ", volume=" + volume + ", chapterId=" + chapterId
				+ ", volumeId=" + volumeId + ", quotation=" + quotation + "]";
	}

	public void setURL(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
