package de.uni_koeln.spinfo.drc.controller;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.uni_koeln.spinfo.drc.lucene.SearchResult;
import de.uni_koeln.spinfo.drc.lucene.Searcher;

@Controller()
@RequestMapping(value = "/drc")
public class SearchController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Searcher searcher;

	/**
	 * The search page
	 */
	@RequestMapping(value = "/search")
	public ModelAndView search() {
		return new ModelAndView("search");
	}

	/**
	 * The result view
	 */
	@RequestMapping(value = "/searchResult")
	public ModelAndView simpleResult(
			@RequestParam("searchForm") String searchPhrase) {
		logger.info("search=" + searchPhrase);
		List<SearchResult> resultList = null;
		try {
			resultList = searcher.search(searchPhrase);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView("searchResult");
		mv.addObject("searchPhrase", searchPhrase);
		mv.addObject("hits", resultList);
		mv.addObject("totalHits", searcher.getTotalHits());
		mv.addObject("offset", 0);
		mv.addObject("resultPage", 1);
		return mv;
	}

}
