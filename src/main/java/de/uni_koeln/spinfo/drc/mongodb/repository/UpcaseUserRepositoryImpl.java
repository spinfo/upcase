package de.uni_koeln.spinfo.drc.mongodb.repository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import de.uni_koeln.spinfo.drc.mongodb.data.document.UpcaseUser;

public class UpcaseUserRepositoryImpl implements UpcaseUserRepository {

	private MongoOperations operations;

	@Inject
	public UpcaseUserRepositoryImpl(MongoOperations operations) {
		this.operations = operations;
	}

	@Override
	public UpcaseUser findByUserName(String userName) {
		return operations.findOne(new Query(Criteria.where("userName").is(userName)), UpcaseUser.class);
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
		return upcaseUser;
	}

	@Override
	public UpcaseUser delete(UpcaseUser upcaseUser) {
		operations.remove(upcaseUser);
		return upcaseUser;
	}

	@Override
	public UpcaseUser deleteByUserName(String userName) {
		UpcaseUser user = findByUserName(userName);
		delete(user);
		return user;
	}

	@Override
	public UpcaseUser deleteByEmail(String email) {
		UpcaseUser user = findByEmail(email);
		delete(user);
		return user;
	}

	@Override
	public void deleteAll() {
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
