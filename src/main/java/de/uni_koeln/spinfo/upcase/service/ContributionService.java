package de.uni_koeln.spinfo.upcase.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.ContributionRequest;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;

public interface ContributionService {

	@PreAuthorize("hasRole('USER')")
	public void accept(ContributionRequest request);
	
	@PreAuthorize("hasRole('USER')")
	public void close(ContributionRequest request);
	
	@PreAuthorize("hasRole('USER')")
	public List<ContributionRequest> hasContributionRequests(UpcaseUser upcaseUser);
	
}
