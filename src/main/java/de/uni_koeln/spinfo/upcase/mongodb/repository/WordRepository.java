package de.uni_koeln.spinfo.upcase.mongodb.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.Word;

public interface WordRepository extends Repository<Word, String> {
	
	public List<Word> findByVolumeId(String volumeId);
	
	public List<Word> findByPageId(String pageId);
	
	public List<Word> findByChapterId(String chapterId);
	
	public List<Word> findByLanguageId(String languageId);
	
	public Word findByIndex(int index);
	
	public Word save(Word word);
	
	public List<Word> findByRange(int from, int to);
	
	public long count();

}
