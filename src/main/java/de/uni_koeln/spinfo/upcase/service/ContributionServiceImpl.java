package de.uni_koeln.spinfo.upcase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.ContributionRequest;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.ContributionRequestRepository;

public class ContributionServiceImpl implements ContributionService {
	
	@Autowired private CollectionRepository collectionRepository;
	
	@Autowired private ContributionRequestRepository contributionRequestRepository;

	@Override
	public void accept(ContributionRequest request) {
		request.getCollection().setContributer(request.getRequestor());
		collectionRepository.save(request.getCollection());
		close(request);
	}

	@Override
	public void close(ContributionRequest request) {
		contributionRequestRepository.delete(request);
	}

	@Override
	public List<ContributionRequest> hasContributionRequests(UpcaseUser upcaseUser) {
		return contributionRequestRepository.findByUpcaseUser(upcaseUser);
	}

}
