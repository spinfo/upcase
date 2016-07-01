package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.ContributionRequest;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;

public interface ContributionRequestRepository extends CrudRepository<ContributionRequest, String> {
	
	public List<ContributionRequest> findByUpcaseUser(UpcaseUser upcaseUser);

}
