package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree.Node;

@Repository
public class NodeRepositoryImpl implements NodeRepository {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	private MongoTemplate mongoTemplate;

	@Inject
	public NodeRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<Node> findByParentId(String parent) {
		return  mongoTemplate.find(new Query(Criteria.where("parent").is(parent)), Node.class);
	}

	@Override
	public List<Node> findByParent(Node parent) {
		return  mongoTemplate.find(new Query(Criteria.where("parent").is(parent.getId())), Node.class);
	}

	@Override
	public Node findById(String id) {
		return mongoTemplate.findById(id, Node.class);
	}

	@Override
	public List<Node> findAll() {
		return mongoTemplate.findAll(Node.class);
	}

	@Override
	public Node update(Node node) {
		Node update = mongoTemplate.findById(node.getId(), Node.class);
//		update.setChildren(node.isChildren());
		update.setParent(node.getParent());
		update.setPosition(node.getPosition());
		update.setText(node.getText()); 
		update.setType(node.getType());
		mongoTemplate.save(update, Node.NODE_COLLECTION);
		return update;
	}

	@Override
	public void save(Node node) {
		mongoTemplate.save(node, Node.NODE_COLLECTION);
	}

	@Override
	public void delete(Node node) {
		mongoTemplate.remove(node, Node.NODE_COLLECTION);
	}

	@Override
	public List<Node> findByNodes(List<String> nodes) {
		return mongoTemplate.find(new Query(Criteria.where("id").in(nodes)), Node.class);
	}

}
