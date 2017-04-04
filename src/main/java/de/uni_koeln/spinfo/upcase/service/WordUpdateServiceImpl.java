package de.uni_koeln.spinfo.upcase.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import de.uni_koeln.spinfo.upcase.lucene.Indexer;
import de.uni_koeln.spinfo.upcase.model.AnnotationUpdate;
import de.uni_koeln.spinfo.upcase.model.Tag;
import de.uni_koeln.spinfo.upcase.model.WordUpdate;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Annotation;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Box;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Page;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Word;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.WordVersion;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.PageRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.WordRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.WordVersionRepository;

@Component
public class WordUpdateServiceImpl implements WordUpdateService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired private WordRepository wordRepository;

	@Autowired private WordVersionRepository wordVersionRepository;

	@Autowired private PageRepository pageRepository;

	@Autowired private UpcaseUserRepository upcaseUserRepository;
	
	@Autowired private CollectionRepository collectionRepository;
	
	@Autowired private Indexer indexer;

	@Override
	public Word update(WordUpdate update) {
		logger.info("Updating... " + update);
		UpcaseUser user = getCurrentUser();

		// UPDATE DATA
		String id = update.getId();
		Box box = update.getBox();
		String token = update.getToken();
		String pageId = update.getPageId();

		// RETRIEVE WORD OBJECT
		Word word = wordRepository.findOne(id);

		// VERSION COPY
		createVersion(user, word);

		// DO THE UPDATE
		word.setBox(box);
		word.setToken(token);
		word.setLastModified(new Date());
		wordRepository.save(word);

		Page page = updateInPage(pageId, word);
		
		// UPDATE LUCENE INDEX
		Collection collection = collectionRepository.findbyId(page.getCollection());
		try {
			indexer.update(page, collection);
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.info("Word updated... " + word);

		return word;
	}

	@Override
	public List<Word> update(List<AnnotationUpdate> updates) {

		Annotation annotation = new Annotation();
		List<Word> toReturn = new ArrayList<>();
		logger.info("Updating... " + updates);
		if(updates != null) {
			for (AnnotationUpdate annotationUpdate : updates) {

				// UPDATE ATTRIBUTES
				Tag tag = annotationUpdate.getTag();
				annotation.setTag(tag.getValue());
				annotation.setType(tag.getType());

				String wordId = annotationUpdate.getWordId();
				String pageId = annotationUpdate.getPageId();
				
				Word word = wordRepository.findOne(wordId);
				
				// VALIDATE ANNOTATION
				if (!word.getAnnotations().contains(annotation)) {

					logger.info(annotation + " NOT IN " + word);
					
					// SET ATTRIBUTE
					word.setAnnotation(annotation);
					wordRepository.save(word);

					// CREATE WORD VERSION
					createVersion(getCurrentUser(), word);
					
					// UPDATE PAGE
					updateInPage(pageId, word);

					toReturn.add(word);
				} else {
					logger.info(annotation + " ALREADY IN " + word);
					toReturn.add(word);
				}
			}
			return toReturn;
		}
		return toReturn;
	}

	private Page updateInPage(String pageId, Word word) {
		Page page = pageRepository.findByPageId(pageId);
		Integer index = page.getWordIdToIndex().get(word.getId());
		page.getWords().set(index, word);
		page.setLastModified(new Date());
		pageRepository.save(page);
		return page;
	}

	private UpcaseUser getCurrentUser() {
		// GET CURRENT USER
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		UpcaseUser user = upcaseUserRepository.findByEmail(email);
		return user;
	}

	private void createVersion(UpcaseUser user, Word word) {
		Word version = new Word();
		version.setId(word.getId());
		version.setToken(word.getToken());
		version.setBox(word.getBox());
		version.setAnnotations(word.getAnnotations());
		version.setLastModified(word.getLastModified());
		WordVersion wordVersion = new WordVersion(word, user.getEmail());
		wordVersionRepository.save(wordVersion);
		logger.info(wordVersion.toString());
	}

	@Override
	public Word delete(AnnotationUpdate toDelete) {
		
		String wordId = toDelete.getWordId();
		String pageId = toDelete.getPageId();
		String type = toDelete.getTag().getType();
		String value = toDelete.getTag().getValue();
		
		Word word = wordRepository.findOne(wordId);
		Set<Annotation> annotations = word.getAnnotations();
		
		Annotation match = new Annotation(value, type);
		annotations.remove(match);
		
		// SAVE WORD
		wordRepository.save(word);
		// CREATE WORD VERSION
		createVersion(getCurrentUser(), word);
		// UPDATE PAGE
		updateInPage(pageId, word);

		return word;
	}

}
