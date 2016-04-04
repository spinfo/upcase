package de.uni_koeln.spinfo.upcase.mongodb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.Chapter;

public interface ChapterRepository extends CrudRepository<Chapter, String> {
	
	public List<Chapter> findByTitle(String title);
	
	public List<Chapter> findByUserId(String userId);
	
	public List<Chapter> findByVolumeId(String volumeId);
	
	public List<Chapter> findByVolumeTitle(String volumeTitle);
	
}
