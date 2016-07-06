package de.uni_koeln.spinfo.upcase;

public class CollectionAlreadyExistsException extends Exception {
	
	private static final long serialVersionUID = 1487724146570813552L;

	public CollectionAlreadyExistsException() {
		super("Collection already exists...");
	}
	
	public CollectionAlreadyExistsException(String arg) {
		super("Collection with " + arg + " name already exists...");
	}

}
