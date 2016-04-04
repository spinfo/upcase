package de.uni_koeln.spinfo.upcase.mongodb.repository;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.Word;

@Repository
public class WordRepositoryImpl implements WordRepository {

	private MongoOperations operations;

	@Inject
	public WordRepositoryImpl(MongoOperations operations) {
		this.operations = operations;
	}

	@Override
	public List<Word> findByVolumeId(final String volumeId) {
		return operations.find(new Query(Criteria.where("volumeId")
				.is(volumeId)), Word.class);
	}

	@Override
	public List<Word> findByPageId(final String pageId) {
		return operations.find(new Query(Criteria.where("pageId").is(pageId)),
				Word.class);
	}

	@Override
	public List<Word> findByChapterId(final String chapterId) {
		return operations.find(
				new Query(Criteria.where("chapterId").is(chapterId)),
				Word.class);
	}

	@Override
	public List<Word> findByLanguageId(final String languageId) {
		return operations.find(
				new Query(Criteria.where("languageId").is(languageId)),
				Word.class);
	}

	@Override
	public Word findByIndex(int index) {
		return operations.findOne(new Query(Criteria.where("index").is(index)),
				Word.class);
	}

	@Override
	public Word save(Word word) {
		operations.save(word);
		return word;
	}
	
	public List<Word> findByRange(int from, int to) {
		List<Word> wordRange = operations.find(new Query(Criteria.where("index").gte(from)
				.andOperator(Criteria.where("index").lte(to))), Word.class);
		return wordRange;
	}

	@Override
	public long count() {
		return operations.count(new Query(), Word.class);
	}

}
