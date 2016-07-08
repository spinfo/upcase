package de.uni_koeln.spinfo.upcase.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import de.uni_koeln.spinfo.upcase.model.WordUpdate;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Box;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Page;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Word;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.WordVersion;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.PageRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.WordRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.WordVersionRepository;

@Component
public class WordUpdateServiceImpl implements WordUpdateService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WordRepository wordRepository;
	
	@Autowired
	private WordVersionRepository wordVersionRepository;
	
	@Autowired
	private PageRepository pageRepository;
	
	@Autowired
	private UpcaseUserRepository upcaseUserRepository;

	@Override
	public Word update(WordUpdate update) {
		
		// GET CURRENT USER
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		UpcaseUser user = upcaseUserRepository.findByEmail(email);
//		UpcaseUser user = null;
		logger.info("user.email:" + user.getEmail() + " updating word...");
		
		// UPDATE DATA
		String id = update.getId();
		Box box = update.getBox();
		String token = update.getToken();
		String pageId = update.getPageId();
		
		// RETRIEVE WORD OBJECT
		Word word = wordRepository.findOne(id);
		logger.info("Word found  and ready to be updated... " + word);
		
		// VERSION COPY
		Word version = new Word();
		version.setId(word.getId());
		version.setToken(word.getToken());
		version.setBox(word.getBox());
		version.setAnnotations(word.getAnnotations());
		version.setLastModified(word.getLastModified());
		WordVersion wordVersion = new WordVersion(word, user.getEmail());
		wordVersionRepository.save(wordVersion);
		logger.info("Version of word created " + wordVersion);
		
		// DO THE UPDATE
		word.setBox(box);
		word.setToken(token);
		word.setLastModified(new Date());
		wordRepository.save(word);
		logger.info("Saved word in wordRepository " + word);
		Page page = pageRepository.findByPageId(pageId);
		Integer index = page.getWordIdToIndex().get(word.getId());
		page.getWords().set(index, word);
		pageRepository.save(page);
		logger.info("Saved word in pageRepository " + word);
		logger.info("Word update completed " + word);
		
		return word;
	}

}
