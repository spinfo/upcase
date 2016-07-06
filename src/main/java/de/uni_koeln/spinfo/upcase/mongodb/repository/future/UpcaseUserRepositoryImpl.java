package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;

@Repository
public class UpcaseUserRepositoryImpl implements UpcaseUserRepository {

	Logger logger = LoggerFactory.getLogger(getClass());

	private MongoTemplate template;

	@Autowired
	public UpcaseUserRepositoryImpl(MongoTemplate template) {
		this.template = template;
	}

	@Override
	public UpcaseUser findByEmail(String email) {
		return template.findOne(new Query(Criteria.where("email").is(email)), UpcaseUser.class);
	}

	@Override
	public long count() {
		return template.getCollection(UpcaseUser.COLLECTION).count();
	}

	@Override
	public UpcaseUser save(UpcaseUser user) {
		template.save(user, UpcaseUser.COLLECTION);
		return user;
	}

	@Override
	public UpcaseUser findById(String id) {
		return template.findOne(new Query(Criteria.where("_id").is(id)), UpcaseUser.class);
	}

	@Override
	public List<UpcaseUser> findAll() {
		return template.find(new Query(), UpcaseUser.class, UpcaseUser.COLLECTION);
	}
	
	@Override
	public void deleteAll() {
		template.remove(new Query(), UpcaseUser.class);
	}

}
