package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import java.util.List;

import org.springframework.data.repository.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Page;

public interface PageRepository extends Repository<Page, String> {
	
	public Page findByPageId(String pageId);
	
	public List<Page> findByUser(String user);
	
	public Page findByImageUrl(String imageUrl);
	
	public List<Page> findByCollection(String collection);
	
	public List<Page> findAll();
	
	public Page save(Page page);
	
	public String findOne();
	
	public String findOne(String pageId);

	public List<Page> save(List<Page> pages);
	
}
