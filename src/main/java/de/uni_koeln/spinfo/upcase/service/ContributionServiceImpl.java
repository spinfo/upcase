package de.uni_koeln.spinfo.upcase.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.ContributionRequest;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.ContributionRequestRepository;

@Service
public class ContributionServiceImpl implements ContributionService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired 
	private CollectionRepository collectionRepository;
	
	@Autowired 
	private ContributionRequestRepository contributionRequestRepository;

	@Override
	public void accept(ContributionRequest request) {
		String collectionId = request.getCollection();
		Collection collection = collectionRepository.findbyId(collectionId);
		collection.setContributer(request.getRequestor());
		collectionRepository.save(collection);
		close(request);
	}

	@Override
	public void close(ContributionRequest request) {
		contributionRequestRepository.delete(request);
	}

	@Override
	public List<ContributionRequest> hasContributionRequests(UpcaseUser upcaseUser) {
		return contributionRequestRepository.findByOwner(upcaseUser.getId());
	}

}
