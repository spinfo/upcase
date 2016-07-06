package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import java.util.List;

import org.springframework.data.repository.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;

public interface UpcaseUserRepository extends Repository<UpcaseUser, String> {

	public UpcaseUser save(UpcaseUser user);
	
	public UpcaseUser findByEmail(String email);
	
	public long count();
	
	public UpcaseUser findById(String id);
	
	public List<UpcaseUser> findAll();

	public void deleteAll();

}
