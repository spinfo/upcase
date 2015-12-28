package de.uni_koeln.spinfo.drc.mongodb.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import de.uni_koeln.spinfo.drc.mongodb.data.document.Page;

public interface PageRepository extends Repository<Page, String> {
	
	public Page findByUrl(String url);
	
	public List<Page> findByUserId(String userId);
	
	public List<Page> findByVolumeId(String volumeId);
	
	public List<Page> findByChapterIds(List<String> chapterIds);
	
	public List<Page> findByLanguageIds(List<String> LanguageIds);
	
	public List<Page> findAll();
	
	public Page save(Page page);
	
	public String findOne();
	
	public String findOne(String pageId);
	
	public Page findByPageId(String pageId);
	
	public List<Page> findByRange(int from, int to);

}
