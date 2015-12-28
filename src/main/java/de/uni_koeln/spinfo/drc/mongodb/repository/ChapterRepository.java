package de.uni_koeln.spinfo.drc.mongodb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.uni_koeln.spinfo.drc.mongodb.data.document.Chapter;

public interface ChapterRepository extends CrudRepository<Chapter, String> {
	
	public Chapter findByTitle(String title);
	
	public List<Chapter> findByUserId(String userId);
	
	public List<Chapter> findByVolumeId(String volumeId);
	
	public List<Chapter> findByTitleAndUserId(String title, String userId);

}
