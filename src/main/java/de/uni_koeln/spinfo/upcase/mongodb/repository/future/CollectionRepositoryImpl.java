package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;

@Repository
public class CollectionRepositoryImpl implements CollectionRepository {

	private MongoOperations operations;

	@Autowired
	public CollectionRepositoryImpl(MongoOperations operations) {
		this.operations = operations;
	}

	@Override
	public Collection save(Collection collection) {
		operations.insert(collection, Collection.COLLECTION);
		return collection;
	}

	@Override
	public long count() {
		return operations.getCollection(Collection.COLLECTION).count();
	}

	@Override
	public Collection findbyId(String id) {
		return operations.findOne(new Query(Criteria.where("_id").is(id)), Collection.class);
	}

	@Override
	public Collection findbyTitle(String title) {
		return operations.findOne(new Query(Criteria.where("title").is(title)), Collection.class);
	}

	@Override
	public Collection update(Collection c) {
		Collection update = findbyId(c.getId());
		update.setContributable(c.isContributable());
		update.setContributers(c.getContributers());
		update.setLastModified(c.getLastModified());
		update.setPages(c.getPages());
		update.setTitle(c.getTitle());
		operations.save(update);
		return update;
	}

	@Override
	public List<Collection> findAll() {
		return operations.findAll(Collection.class);
	}

	@Override
	public List<Collection> findByOwner(String ownerId) {
		return operations.find(new Query(Criteria.where("owner").is(ownerId)), Collection.class);
	}

	@Override
	public void deleteALl() {
		operations.remove(new Query(), Collection.class);
	}

}
