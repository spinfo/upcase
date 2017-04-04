package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import java.util.List;

import org.springframework.data.repository.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree.Node;

public interface NodeRepository extends Repository<Node, String> {
	
	List<Node> findByParentId(String parent);
	
	List<Node> findByParent(Node parent);
	
	Node findById(String id);
	
	List<Node> findByNodes(List<String> nodes);
	
	List<Node> findAll();
	
	Node update(Node node);
	
	void save(Node node);
	
	void delete(Node node);
	
}
