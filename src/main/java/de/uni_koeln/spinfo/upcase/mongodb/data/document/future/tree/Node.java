package de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Node {
	
	private String id;
	private String parent;
	private String text;
	private String type;
	private boolean children;
	
	public Node() {
	}
	
	public Node(String id, String text, String parent, String type) {
		this(id, text, parent, type, false);
	}
	
	public Node(String id, String text, String parent, String type, boolean children) {
		this.id = id;
		this.text = text;
		this.parent = parent;
		this.type = type;
		this.children = children;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public void setChildren(boolean children) {
		this.children = children;
	}
	
	public boolean isChildren() {
		return children;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", parent=" + parent + ", text=" + text + ", type=" + type + ", children=" + children
				+ "]";
	}
	

}
