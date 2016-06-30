package de.uni_koeln.spinfo.upcase.mongodb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.UpcaseUser;

public interface UpcaseUserRepository extends Repository<UpcaseUser, String> {
	
	public UpcaseUser findByEmail(String email);
	
	public UpcaseUser findById(String id);
	
	public List<UpcaseUser> findAll();
	
	public List<UpcaseUser> findByCreationDate(Date creationDate);
	
	public UpcaseUser save(UpcaseUser upcaseUser);
	
	public UpcaseUser delete(UpcaseUser upcaseUser);
	
	public UpcaseUser deleteByEmail(String email);
	
	public void deleteAll();
	
	public UpcaseUser update(UpcaseUser upcaseUser);
	
	public long count();

}
