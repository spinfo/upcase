package de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree;

public enum Type {
	
	ROOT("root"), FOLDER("folder"), FILE("file");
	
	private String type;
	
	Type(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}