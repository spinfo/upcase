package de.uni_koeln.spinfo.upcase.mongodb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.Language;

public interface LanguageRepository extends CrudRepository<Language, String> {
	
	public List<Language> findByUserId(String userId);
	
	public List<Language> findByTitle(String title);
	
	public List<Language> findByVolumeId(String volumeId);

}
