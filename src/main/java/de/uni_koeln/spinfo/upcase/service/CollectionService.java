package de.uni_koeln.spinfo.upcase.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import de.uni_koeln.spinfo.upcase.CollectionAlreadyExistsException;
import de.uni_koeln.spinfo.upcase.model.form.UploadForm;

@Service
public interface CollectionService {

	/**
	 * 
	 * @param uploadForm
	 * @return collectionId
	 * @throws CollectionAlreadyExistsException
	 */
	public String createCollection(UploadForm uploadForm, String path) throws CollectionAlreadyExistsException;
	
	
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteAllColletions();
	
}
