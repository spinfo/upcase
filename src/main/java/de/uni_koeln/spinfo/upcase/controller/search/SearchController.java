package de.uni_koeln.spinfo.upcase.controller.search;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.uni_koeln.spinfo.upcase.lucene.Searcher;
import de.uni_koeln.spinfo.upcase.model.SearchResult;
import de.uni_koeln.spinfo.upcase.util.PropertyReader;

@Controller()
public class SearchController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Searcher searcher;

	@Autowired
	PropertyReader propertyReader;
	

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
	public String simpleResult(@RequestParam("searchPhrase") String searchPhrase,
			@RequestParam(value = "page", required = false) Integer page, Model model) {

		List<SearchResult> resultList = null;
		try {

			page = page == null ? 1 : page;


			if(searcher.openDirectory().numDocs() <= 0) {
				return "home";
			}
			
			resultList = searcher.withQuotations(searchPhrase, true, 1, page);
			

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} catch (InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}

		int chunks = searcher.getTotalHits() / propertyReader.getHitsPerPage();
		
		model.addAttribute("page", page);
		model.addAttribute("searchPhrase", searchPhrase);
		model.addAttribute("hits", resultList);
		model.addAttribute("totalHits", searcher.getTotalHits());
		model.addAttribute("offset", (page - 1) * propertyReader.getHitsPerPage());
		model.addAttribute("chunks", chunks);
		model.addAttribute("prev", (page - 1) == 0);
		model.addAttribute("next", (page - 1) == chunks);

		
		
		return "searchResult";
	}

}
