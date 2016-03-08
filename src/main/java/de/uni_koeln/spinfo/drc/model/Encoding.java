package de.uni_koeln.spinfo.drc.model;

public enum Encoding {

	UTF_8("UTF-8"), UTF_16("UTF-16"), LATIN_1("ISO 8859-1"), LATIN_2(
			"ISO 8859-2"), LATIN_3("ISO 8859-3"), LATIN_4("ISO 8859-4"), CYRILLIC(
			"ISO 8859-5"), ARABIC("ISO 8859-6"), GREEK("ISO 8859-7"), HEBREW(
			"ISO 8859-8"), TURKISH("ISO 8859-9");

	private String encoding;

	private Encoding(final String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}

}
