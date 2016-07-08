package de.uni_koeln.spinfo.upcase.model;

public class Tag {

	private String type;
	private String value;

	public Tag() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Tag [type=" + type + ", value=" + value + "]";
	}

}
