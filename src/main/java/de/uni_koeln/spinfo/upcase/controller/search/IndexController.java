package de.uni_koeln.spinfo.upcase.controller.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import de.uni_koeln.spinfo.upcase.lucene.Indexer;


@Controller
public class IndexController {
	
	@Autowired
	private Indexer indexer;
	
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/index/delete/")
	public String deletAllDocuments() {
		indexer.deleteIndex();
		return "home";
	}
	
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/index/reindex/")
	public String reIndexCollections() {
		indexer.reIndex();
		return "home";
	}
}
