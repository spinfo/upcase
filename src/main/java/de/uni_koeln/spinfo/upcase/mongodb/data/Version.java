package de.uni_koeln.spinfo.drc.mongodb.data;

import java.io.Serializable;

import de.uni_koeln.spinfo.drc.mongodb.data.core.AbstractDocument;

public class Version extends AbstractDocument implements Comparable<Version>, Serializable {

	private static final long serialVersionUID = -3750458969837772393L;

	// THE TOKEN
	private String version;

	public String getValue() {
		return version;
	}

	public void setValue(final String version) {
		this.version = version;
	}

	@Override
	public int compareTo(Version o) {
		return o.getDate().compareTo(this.getDate());
	}

}
