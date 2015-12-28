package de.uni_koeln.spinfo.drc.mongodb.repository;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import de.uni_koeln.spinfo.drc.mongodb.data.document.Page;

@Repository
public class PageRepositoryImpl implements PageRepository {

	private MongoOperations operations;

	@Inject
	public PageRepositoryImpl(MongoOperations operations) {
		this.operations = operations;
	}

	@Override
	public Page findByUrl(String url) {
		return operations.findOne(new Query(Criteria.where("url").is(url)),
				Page.class);
	}

	@Override
	public List<Page> findByUserId(String userId) {
		return operations.find(new Query(Criteria.where("userId").is(userId)),
				Page.class);
	}

	@Override
	public List<Page> findByVolumeId(String volumeId) {
		return operations.find(new Query(Criteria.where("volumeId")
				.is(volumeId)), Page.class);
	}

	@Override
	public List<Page> findByChapterIds(List<String> chapterIds) {
		return operations.find(
				new Query(Criteria.where("chapterIds").is(chapterIds)),
				Page.class);
	}

	@Override
	public List<Page> findByLanguageIds(List<String> LanguageIds) {
		return operations.find(
				new Query(Criteria.where("LanguageIds").is(LanguageIds)),
				Page.class);
	}

	@Override
	public List<Page> findAll() {
		return operations.find(new Query(), Page.class);
	}

	@Override
	public Page save(Page page) {
		operations.save(page);
		return page;
	}

	@Override
	public String findOne() {
		return operations.findOne(new Query(), String.class, "pages");
	}

	@Override
	public Page findByPageId(String pageId) {
		return operations.findOne(new Query(Criteria.where("_id").is(pageId)),
				Page.class);
	}

	@Override
	public String findOne(String pageId) {
		return operations.findOne(new Query(Criteria.where("_id").is(pageId)),
				String.class, "pages");
	}
	
	@Override
	public List<Page> findByRange(int from, int to) {
		List<Page> wordRange = operations.find(new Query(Criteria.where("index").gte(from)
				.andOperator(Criteria.where("index").lte(to))), Page.class);
		return wordRange;
	}

}
