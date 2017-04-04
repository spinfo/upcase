package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import org.springframework.data.repository.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree.Tree;

public interface TreeRepository extends Repository<Tree, String> {
	
	Tree findById(String id);
	
	Tree findByName(String name);
	
	Tree findByCollectionId(String collectionId);
	
	Tree update(Tree tree);
	
	void save(Tree tree);
	
	void delete(Tree tree);

}
