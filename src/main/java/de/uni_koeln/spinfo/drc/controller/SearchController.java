package de.uni_koeln.spinfo.drc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import de.uni_koeln.spinfo.drc.lucene.SearchResult;
import de.uni_koeln.spinfo.drc.lucene.Searcher;
import de.uni_koeln.spinfo.drc.mongodb.DataBase;
import de.uni_koeln.spinfo.drc.mongodb.data.document.Chapter;
import de.uni_koeln.spinfo.drc.mongodb.data.document.Language;
import de.uni_koeln.spinfo.drc.mongodb.data.document.Volume;
import de.uni_koeln.spinfo.drc.util.ChapterComparator;
import de.uni_koeln.spinfo.drc.util.PropertyReader;
import de.uni_koeln.spinfo.drc.util.VolumeComparator;

@Controller()
@RequestMapping(value = "/drc")
public class SearchController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Searcher searcher;

	@Autowired
	private DataBase db;

	@Autowired
	PropertyReader propertyReader;

	/**
	 * The search page
	 */
	@RequestMapping(value = "/search")
	public ModelAndView search() {
		return new ModelAndView("search");
	}

	@ModelAttribute("languageList")
	public List<String> getLanguageList() {
		Iterable<Language> findAll = db.getLanguageRepository().findAll();
		List<String> languages = new ArrayList<>();
		languages.add("alle");
		Set<String> set = new TreeSet<>();
		findAll.forEach(l -> set.add(l.getTitle()));
		languages.addAll(set);
		return languages;
	}

	@ModelAttribute("volumeList")
	public List<String> getVolumeList() {
		Iterable<Volume> findAll = db.getVolumeRepository().findAll();
		List<String> volumes = new ArrayList<>();
		volumes.add("alle");
		findAll.forEach(l -> volumes.add(l.getTitle()));
		return volumes;
	}

	@RequestMapping(value = "chapters/by/volume/title/{volumeTitle}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody List<String> getChapterNamesByVolTitle(
			@PathVariable String volumeTitle) {
		Volume volume = db.getVolumeRepository().findByTitle(volumeTitle);
		List<Chapter> chapters = db.getChapterRepository().findByVolumeId(
				volume.getId());
		List<String> chapterNames = new ArrayList<>();
		chapterNames.add("alle");
		chapters.forEach(c -> chapterNames.add(c.getTitle()));
		return chapterNames;
	}

	/**
	 * The result view
	 */
	@RequestMapping(value = "/searchResult")
	public ModelAndView simpleResult(
			@RequestParam("searchPhrase") String searchPhrase, 
			@RequestParam(value = "regex", required = false) boolean regex, 
			@RequestParam(value = "volumeSelection", required = false) String volumeSelection,
			@RequestParam(value = "chapterSelection", required = false) String chapterSelection,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "volumeSort", required = false) String volumeSort,
			@RequestParam(value = "chapterSort", required = false) String chapterSort) {

		page = page == null ? 1 : page;
		volumeSelection = volumeSelection == null ? "alle" : volumeSelection;
		chapterSelection = chapterSelection == null ? "alle" : chapterSelection;
		
		List<SearchResult> resultList = null;
		try {
			
			resultList = searcher.withQuotations(searchPhrase, regex, volumeSelection, chapterSelection,  1, page);
			
			if(volumeSort != null) {
				if (!volumeSort.equals("none")) {
					Collections.sort(resultList, new VolumeComparator(volumeSort));
				}
			}
			if(chapterSort != null) {
				if (!chapterSort.equals("none")) {
					Collections.sort(resultList, new ChapterComparator(chapterSort));
				}
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} catch (InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}

		int chunks = searcher.getTotalHits() / propertyReader.getHitsPerPage();

		ModelAndView mv = new ModelAndView("searchResult");
		mv.addObject("page", page);
		mv.addObject("regex", regex);
		mv.addObject("searchPhrase", searchPhrase);
		mv.addObject("hits", resultList);
		mv.addObject("totalHits", searcher.getTotalHits());
		mv.addObject("offset", (page - 1) * propertyReader.getHitsPerPage());
		mv.addObject("chunks", chunks);
		mv.addObject("volumeSort", volumeSort);
		mv.addObject("chapterSort", chapterSort);
		mv.addObject("prev", (page - 1) == 0);
		mv.addObject("next", (page - 1) == chunks);
		return mv;
	}

}
