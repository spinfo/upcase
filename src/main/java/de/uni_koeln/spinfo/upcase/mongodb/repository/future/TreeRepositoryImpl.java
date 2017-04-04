package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree.Tree;

@Repository
public class TreeRepositoryImpl implements TreeRepository {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	private MongoTemplate mongoTemplate;

	@Inject
	public TreeRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Tree findById(String id) {
		return mongoTemplate.findById(id, Tree.class);
	}

	@Override
	public Tree findByName(String name) {
		return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), Tree.class);
	}

	@Override
	public Tree update(Tree tree) {
		Tree update = mongoTemplate.findById(tree.getId(), Tree.class);
		update.setNodes(tree.getNodes());
		update.setName(tree.getName());
		mongoTemplate.save(update, Tree.TREE_COLLECTION);
		return update;
	}

	@Override
	public void save(Tree tree) {
		mongoTemplate.save(tree, Tree.TREE_COLLECTION);
	}

	@Override
	public void delete(Tree tree) {
		mongoTemplate.remove(tree);
	}

	@Override
	public Tree findByCollectionId(String collectionId) {
		return mongoTemplate.findOne(new Query(Criteria.where("collectionId").is(collectionId)), Tree.class);
	}

}
