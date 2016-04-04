package de.uni_koeln.spinfo.drc.mongodb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.security.access.annotation.Secured;

import de.uni_koeln.spinfo.drc.mongodb.data.document.UpcaseUser;

public interface UpcaseUserRepository extends Repository<UpcaseUser, String> {
	
	public UpcaseUser findByUserName(String userName);
	
	public UpcaseUser findByEmail(String email);
	
	public List<UpcaseUser> findAll();
	
	public List<UpcaseUser> findByCreationDate(Date creationDate);
	
	public UpcaseUser save(UpcaseUser upcaseUser);
	
	public UpcaseUser delete(UpcaseUser upcaseUser);
	
	@Secured("ROLE_ADMIN")
	public UpcaseUser deleteByUserName(String userName);
	
	@Secured("ROLE_ADMIN")
	public UpcaseUser deleteByEmail(String email);
	
	@Secured("ROLE_ADMIN")
	public void deleteAll();
	
	public UpcaseUser update(UpcaseUser upcaseUser);
	
	public long count();

}
