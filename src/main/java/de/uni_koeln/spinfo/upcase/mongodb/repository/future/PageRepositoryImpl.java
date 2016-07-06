package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Page;

@Repository
public class PageRepositoryImpl implements PageRepository {

	private MongoTemplate mongoTemplate;

	@Inject
	public PageRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Page findByImageUrl(String imageUrl) {
		return mongoTemplate.findOne(new Query(Criteria.where("imageUrl").is(imageUrl)), Page.class);
	}

	@Override
	public List<Page> findByUser(String user) {
		return mongoTemplate.find(new Query(Criteria.where("user").is(user)), Page.class);
	}

	@Override
	public List<Page> findAll() {
		return mongoTemplate.find(new Query(), Page.class);
	}

	@Override
	public Page save(Page page) {
		mongoTemplate.save(page);
		return page;
	}

	@Override
	public String findOne() {
		return mongoTemplate.findOne(new Query(), String.class, Page.COLLECTION);
	}

	@Override
	public Page findByPageId(String pageId) {
		return mongoTemplate.findOne(new Query(Criteria.where("_id").is(pageId)), Page.class);
	}

	@Override
	public String findOne(String pageId) {
		return mongoTemplate.findOne(new Query(Criteria.where("_id").is(pageId)), String.class, Page.COLLECTION);
	}

	@Override
	public List<Page> findByCollection(String collection) {
		return mongoTemplate.find(new Query(Criteria.where("collection").is(collection)), Page.class);
	}

	@Override
	public List<Page> save(List<Page> pages) {
		mongoTemplate.insert(pages, Page.class);
		return pages;
	}

}
