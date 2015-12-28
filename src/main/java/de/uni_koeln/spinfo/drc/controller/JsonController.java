package de.uni_koeln.spinfo.drc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.uni_koeln.spinfo.drc.mongodb.DataBase;
import de.uni_koeln.spinfo.drc.mongodb.data.document.Page;
import de.uni_koeln.spinfo.drc.mongodb.data.document.Word;

@RestController()
public class JsonController {
	
	
	@Autowired
	private DataBase db;
	
	@RequestMapping(value="/drc/rest/word", method=RequestMethod.GET)
	public Word getWordfromIndex(@RequestParam(value = "index") int index) {
		return db.getWordRepository().findByIndex(index);
	}
	
	@RequestMapping(value="/drc/rest/page", method=RequestMethod.GET)
	public Page getPageWithUrl(@RequestParam(value = "url") String url) {
		return db.getPageRepository().findByUrl(url);
	}

}