package de.uni_koeln.spinfo.drc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.uni_koeln.spinfo.drc.mongodb.Constans;
import de.uni_koeln.spinfo.drc.mongodb.DataBase;
import de.uni_koeln.spinfo.drc.mongodb.data.Version;
import de.uni_koeln.spinfo.drc.mongodb.data.document.Chapter;
import de.uni_koeln.spinfo.drc.mongodb.data.document.Page;
import de.uni_koeln.spinfo.drc.mongodb.data.document.Word;
import de.uni_koeln.spinfo.drc.mongodb.data.light.Letter;
import de.uni_koeln.spinfo.drc.mongodb.data.light.RectangleLight;

@Controller()
@RequestMapping(value = "/drc")
public class ContentController {

	@Autowired
	private DataBase db;

	@RequestMapping(value = "/pages")
	public ModelAndView getPages(@RequestParam("chapterId") String chapterId,
			@RequestParam("chapterTitle") String chapterTitle,
			@RequestParam("volumeId") String volumeId,
			@RequestParam("volumeTitle") String volumeTitle) {

		List<Page> pages = db.getPageRepository().findByChapterIds(
				Arrays.asList(new String[] { chapterId }));

		ModelAndView mv = new ModelAndView(Constans.PAGES);
		mv.addObject("pages", pages);
		mv.addObject("chapterTitle", chapterTitle);
		mv.addObject("chapterId", chapterId);
		mv.addObject("volumeId", volumeId);
		mv.addObject("volumeTitle", volumeTitle);
		return mv;
	}

	@RequestMapping(value = "/correct")
	public ModelAndView correctPage(@RequestParam("pageId") String pageId,
			@RequestParam("chapterId") String chapterId,
			@RequestParam("chapterTitle") String chapterTitle,
			@RequestParam("volumeId") String volumeId,
			@RequestParam("volumeTitle") String volumeTitle)
			throws JsonGenerationException, JsonMappingException, IOException {

		Page page = db.getPageRepository().findByPageId(pageId);
		List<Word> words = db.getWordRepository().findByRange(page.getStart(), page.getEnd());
		Collections.sort(words);
		List<Letter> letters = new ArrayList<>();
		List<RectangleLight> rectangles = new ArrayList<>();
		AtomicInteger textLength = new AtomicInteger();
		for (Word word : words) {
			Version currentVersion = word.getCurrentVersion();
			String id = word.getId();
			int index = word.getIndex();
			rectangles.add(new RectangleLight(id, word.getRectangle()));
			char[] charArray = currentVersion.getValue().toCharArray();
			AtomicInteger wordLength = new AtomicInteger();
			for (char c : charArray) {
				if ((int) c != 32) {
					letters.add(new Letter(id, index, c, wordLength
							.getAndIncrement(), textLength.getAndIncrement()));
				}

			}
		}
		ModelAndView mv = new ModelAndView("correct");
		mv.addObject("letters", letters);
		mv.addObject("rectangles", rectangles);
		mv.addObject("page", page);
		mv.addObject("chapterTitle", chapterTitle);
		mv.addObject("chapterId", chapterId);
		mv.addObject("chapters", getChapters(page));
		mv.addObject("volumeId", volumeId);
		mv.addObject("volumeTitle", volumeTitle);
		return mv;
	}

	@RequestMapping(value = "/page")
	public ModelAndView pageDetails(@RequestParam("pageId") String pageId)
			throws JsonGenerationException, JsonMappingException, IOException {
		Page page = db.getPageRepository().findByPageId(pageId);
		List<Word> words = db.getWordRepository().findByRange(page.getStart(), page.getEnd());
		Collections.sort(words);
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("words", words);
		mv.addObject("page", page);
		return mv;
	}

	public Iterable<Chapter> getChapters(Page page) {
		return db.getChapterRepository().findAll(page.getChapterIds());
	}

	@RequestMapping(value = "/language", method = RequestMethod.GET)
	@ResponseBody
	public String getLanguage(@RequestParam("languageId") String languageId) {
		return db.getLanguageRepository().findOne(languageId).getTitle();
	}

}
