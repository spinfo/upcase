package de.uni_koeln.spinfo.upcase.lucene.json;

public class PGQuery {

	private String language;
	private String query;
	
	public PGQuery() {
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String toString() {
		return "PGQuery [language=" + language + ", query=" + query + "]";
	}
	
	


}
