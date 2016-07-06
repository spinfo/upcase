package de.uni_koeln.spinfo.upcase.mongodb.util;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;


@Component("db")
public class DataBase {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * Returns an entry as a JSON-String from the given collection
	 * @param collectionName
	 * @return JSON-String
	 */
	public String findOne(final String collectionName) {
		return mongoTemplate.findOne(new Query(), String.class, collectionName);
	}
	
	/**
	 * Returns all entries as a list of JSON-String from the given collection
	 * @param collectionName
	 * @return JSON-String
	 */
	public List<String> find(final String collectionName) {
		return mongoTemplate.find(new Query(), String.class, collectionName);
	}
	
	/*
	 * Returns a set containing the collections names within the data base
	 */
	public Set<String> getCollectionNames() {
		return mongoTemplate.getCollectionNames();
	}

	/**
	 * Creates and returns new {@link DBCollection} with the given name
	 * @param collectionName
	 */
	public DBCollection createCollection(final String collectionName) {
		return mongoTemplate.createCollection(collectionName);
	}
	
	/**
	 * Deletes all collections from the data base
	 */
	public void dropAllCollections() {
		Set<String> collectionNames = getCollectionNames();
		for (String collectionName : collectionNames) {
			mongoTemplate.dropCollection(collectionName);
		}
	}
	
	/**
	 * Returns the data base name
	 * @return
	 */
	public String getDbName() {
		return mongoTemplate.getDb().getName();
	}
	
	/**
	 * Returns the total number of collections
	 * @param collectionName
	 * @return
	 */
	public Long collectionCount(final String collectionName) {
		return mongoTemplate.getCollection(collectionName).count();
	}
	
	/**
	 * Prints some basic data base statistics
	 * @throws JsonProcessingException
	 */
	public void dbStats() throws JsonProcessingException {
		String dbStatsCommand = "{ dbStats: 1, scale: 1024 }";
		CommandResult commandResult = mongoTemplate.executeCommand(dbStatsCommand);
		logger.debug("DB STATS: " + commandResult.toString());
	}

}
