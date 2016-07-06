package de.uni_koeln.spinfo.upcase.controller.search;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.uni_koeln.spinfo.upcase.lucene.SearchResult;
import de.uni_koeln.spinfo.upcase.lucene.Searcher;
import de.uni_koeln.spinfo.upcase.util.ChapterComparator;
import de.uni_koeln.spinfo.upcase.util.PropertyReader;
import de.uni_koeln.spinfo.upcase.util.VolumeComparator;

@Controller()
//@RequestMapping(value = "/drc")
public class SearchController {

	// THYMELEAF-SYNTAX - "TEMPLATE-HTML-FILE :: TH:FRAGMENT";
	private static final String DOCUMENT_DETAILS_TEMPLATE = "document-details :: document-details";

	private static final String IMGS_URL = "http://hydra.spinfo.uni-koeln.de/img/";

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Searcher searcher;

//	@Autowired
//	private DataBase db;

	@Autowired
	PropertyReader propertyReader;

	/**
	 * The search page
	 */
	@RequestMapping(value = "/search")
	public ModelAndView search() {
		return new ModelAndView("search");
	}

//	@ModelAttribute("languageList")
//	public List<String> getLanguageList() {
//		Iterable<Language> findAll = db.getLanguageRepository().findAll();
//		List<String> languages = new ArrayList<>();
//		Set<String> set = new TreeSet<>();
//		findAll.forEach(l -> set.add(l.getTitle()));
//		languages.addAll(set);
//		return languages;
//	}

//	@ModelAttribute("volumeList")
//	public List<String> getVolumeList() {
//		Iterable<Volume> findAll = db.getVolumeRepository().findAll();
//		List<String> volumes = new ArrayList<>();
//		findAll.forEach(l -> volumes.add(l.getTitle()));
//		return volumes;
//	}

//	@RequestMapping(value = "chapters/by/volume/title/{volumeTitle}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
//	public @ResponseBody List<String> getChapterNamesByVolTitle(@PathVariable String volumeTitle) {
//		Volume volume = db.getVolumeRepository().findByTitle(volumeTitle);
//		if (volume == null) {
//			return new ArrayList<>();
//		}
//		List<Chapter> chapters = db.getChapterRepository().findByVolumeId(volume.getId());
//		List<String> chapterNames = new ArrayList<>();
//		chapters.forEach(c -> chapterNames.add(c.getTitle()));
//		return chapterNames;
//	}

//	@RequestMapping(value = "/showDocument", method = RequestMethod.GET)
//	public Model showDocument(Model model, @RequestParam("pageId") final String pageId,
//			@RequestParam("resultQuotations") String... resultQuotations) {
//
//		Page page = db.getPageRepository().findByPageId(pageId);
//		List<Word> words = db.getWordRepository().findByRange(page.getStart(), page.getEnd());
//		Collections.sort(words);
//		List<RectangleLight> rectangles = new ArrayList<>();
//
//		Pattern quotationFinder = Pattern.compile("<span[^>]*>(.*?)</span>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
//
//		List<String> matchedTokens = new ArrayList<>();
//
//		for (String quotation : resultQuotations) {
//			Matcher regexMatcher = quotationFinder.matcher(quotation);
//			while (regexMatcher.find()) {
//				matchedTokens.add(regexMatcher.group(1));
//			}
//		}
//
//		for (Word word : words) {
//			Version currentVersion = word.getCurrentVersion();
//			for (String token : matchedTokens) {
//				String normalized = currentVersion.getValue().replaceAll("\\W", "");
//				if (normalized.equalsIgnoreCase(token)) {
//					String id = word.getId();
//					RectangleLight rectangleLight = new RectangleLight(id, word.getRectangle());
//					logger.debug("version :: '" + currentVersion.getValue() + "' rectangle :: " + rectangleLight);
//					rectangles.add(rectangleLight);
//				}
//			}
//		}
//
//		model.addAttribute("page", page);
//		model.addAttribute("imgUrl", IMGS_URL + page.getUrl().replace(".xml", ""));
//		model.addAttribute("words", words);
//		model.addAttribute("rectangles", rectangles);
//
//		return model;
//	}

	/**
	 * The result view
	 */
	@RequestMapping(value = "/searchResult")
	public ModelAndView simpleResult(@RequestParam("searchPhrase") String searchPhrase,
			@RequestParam(value = "regex", required = false) boolean regex,
			@RequestParam(value = "volumeSelection", required = false) String volumeSelection,
			@RequestParam(value = "chapterSelection", required = false) String chapterSelection,
			@RequestParam(value = "langSelection", required = false) String langSelection,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "volumeSort", required = false) String volumeSort,
			@RequestParam(value = "chapterSort", required = false) String chapterSort) {

		List<SearchResult> resultList = null;
		try {

			page = page == null ? 1 : page;
			volumeSelection = volumeSelection == null ? "alle" : volumeSelection;
			chapterSelection = chapterSelection == null ? "alle" : chapterSelection;
			langSelection = langSelection == null ? "alle" : langSelection;

			resultList = searcher.withQuotations(searchPhrase, regex, volumeSelection, chapterSelection, langSelection,
					1, page);

			if (volumeSort != null) {
				if (!volumeSort.equals("none")) {
					Collections.sort(resultList, new VolumeComparator(volumeSort));
				}
			}
			if (chapterSort != null) {
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
		mv.addObject("volumeSelection", volumeSelection);
		mv.addObject("chapterSelection", chapterSelection);
		mv.addObject("langSelection", langSelection);
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
