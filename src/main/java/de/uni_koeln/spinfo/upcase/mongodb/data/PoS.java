package de.uni_koeln.spinfo.upcase.mongodb.data;

import de.uni_koeln.spinfo.upcase.mongodb.data.core.AbstractDocument;

public class PoS extends AbstractDocument implements Comparable<PoS> {

	private String posTag;

	public String getPosTag() {
		return posTag;
	}

	public void setPosTag(final String posTag) {
		this.posTag = posTag;
	}

	@Override
	public String toString() {
		return "PoS [posTag=" + posTag + ", getDate()=" + getDate() + ", getUserId()=" + getUserId() + "]";
	}

	@Override
	public int compareTo(PoS o) {
		return o.getDate().compareTo(this.getDate());
	}

}
