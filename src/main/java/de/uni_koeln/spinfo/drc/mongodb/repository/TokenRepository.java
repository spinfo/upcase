package de.uni_koeln.spinfo.drc.mongodb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.uni_koeln.spinfo.drc.mongodb.data.document.Token;

public interface TokenRepository extends CrudRepository<Token, String> {
	
	public List<Token> findByVolumeId(String volumeId);
	
	public List<Token> findByPageId(String pageId);
	
	public List<Token> findByChapterId(String chapterId);
	
	public List<Token> findByLanguageId(String languageId);
	
	public Token findByIndex(int index);

}
