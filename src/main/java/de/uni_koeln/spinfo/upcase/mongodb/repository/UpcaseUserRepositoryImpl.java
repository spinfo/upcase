package de.uni_koeln.spinfo.upcase.mongodb.repository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.UpcaseUser;

@Repository
public class UpcaseUserRepositoryImpl implements UpcaseUserRepository {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private MongoOperations operations;

	@Inject
	public UpcaseUserRepositoryImpl(MongoOperations operations) {
		this.operations = operations;
	}

	@Override
	public UpcaseUser findByEmail(String email) {
		return operations.findOne(new Query(Criteria.where("email").is(email)), UpcaseUser.class);
	}

	@Override
	public List<UpcaseUser> findAll() {
		return operations.findAll(UpcaseUser.class);
	}

	@Override
	public List<UpcaseUser> findByCreationDate(Date creationDate) {
		 return operations.find(new Query(Criteria.where("creationDate").gte(creationDate)), UpcaseUser.class);
	}

	@Override
	public UpcaseUser save(UpcaseUser upcaseUser) {
		operations.save(upcaseUser, "users");
		logger.info("SAVE: " + upcaseUser);
		return upcaseUser;
	}

	@Override
	public UpcaseUser delete(UpcaseUser upcaseUser) {
		operations.remove(upcaseUser);
		return upcaseUser;
	}

	@Override
	public UpcaseUser deleteByEmail(String email) {
		UpcaseUser user = findByEmail(email);
		delete(user);
		return user;
	}

	@Override
	public void deleteAll() {
		logger.info("DELETE_ALL: " + count());
		operations.remove(new Query(), UpcaseUser.class);
	}

	@Override
	public UpcaseUser update(UpcaseUser upcaseUser) {
		UpcaseUser toUpdate = findById(upcaseUser);
		toUpdate = upcaseUser;
		save(toUpdate);
		return toUpdate;
	}

	@Override
	public long count() {
		return operations.count(new Query(), UpcaseUser.class);
	}
	
	private UpcaseUser findById(UpcaseUser upcaseUser) {
		return operations.findOne(new Query(Criteria.where("_id").is(upcaseUser.getId())), UpcaseUser.class);
	}
	
}
