package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import javax.inject.Inject;

import org.springframework.data.mongodb.core.MongoOperations;

public class UpcaseUserRepositoryImpl implements UpcaseUserRepository {
	
	
	private MongoOperations operations;

	@Inject
	public UpcaseUserRepositoryImpl(MongoOperations operations) {
		this.operations = operations;
	}

	

}
