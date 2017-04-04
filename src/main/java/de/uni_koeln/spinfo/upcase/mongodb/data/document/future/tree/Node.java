package de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nodes")
public class Node {
	
	public static final String NODE_COLLECTION = "nodes";
	
	@Id private String id; // NODE_ID
	private String parent; // PARENT_ID
	private String text; // NODE_VALUE
	private String type; // NODE_TYPE
	// private boolean children; // HAS_CHILDREN
	private int position; // INDEX
	
	private String pageId; // PAGE_REF
	
	public Node() {
	}
	
	public Node(String text, String parent, String type) {
		this.text = text;
		this.parent = parent;
		this.type = type;
		//this.children = children;
	}
	
//	public Node(String id, String text, String parent, String type) {
//		this(id, text, parent, type);
//	}
	
	public Node(String id, String text, String parent, String type) {
		this.id = id;
		this.text = text;
		this.parent = parent;
		this.type = type;
		//this.children = children;
	}
	
	public Node(String id, String text, String parent, String type, int position) {
		this.id = id;
		this.text = text;
		this.parent = parent;
		this.type = type;
//		this.children = children;
		this.position = position;
	}
	
//	public Node(String id, String text, String parent, String type, int position) {
//		this.id = id;
//		this.text = text;
//		this.parent = parent;
//		this.type = type;
//		this.position = position;
//	}

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

//	public void setChildren(boolean children) {
//		this.children = children;
//	}
	
//	public boolean isChildren() {
//		return children;
//	}

	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getPageId() {
		return pageId;
	}
	
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", parent=" + parent + ", text=" + text + ", type=" + type 
				+ ", position=" + position + "]";
	}

}
