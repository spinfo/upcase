package de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trees")
public class Tree {
	
	public static final String TREE_COLLECTION = "trees";
	
	@Id private String id; // TREE_ID
	
	private String name; // TREE_NAME
	
	private String collectionId; // COLLECTION_REF
	
	private List<String> nodes = new ArrayList<>(); // NODES_REF
	
	private String root; // ROOT_REF
	
	public Tree() {
	}
	
	public Tree(String name, String collectionId) {
		this.name = name;
		this.collectionId = collectionId;
	}
	
	public String getRoot() {
		return root;
	}
	
	public void setRoot(String root) {
		this.root = root;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean addNode(String nodeId) {
		return nodes.add(nodeId);
	}
	
	public List<String> getNodes() {
		return nodes;
	}
	
	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}
	
	public boolean addAll(List<String> nodes) {
		return this.nodes.addAll(nodes);
	}
	
	public String getCollectionId() {
		return collectionId;
	}
	
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	@Override
	public String toString() {
		return "Tree [id=" + id + ", name=" + name + ", collectionId=" + collectionId + ", root=" + root + "]";
	}
	
	

}
