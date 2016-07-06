package de.uni_koeln.spinfo.upcase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
// @RequestMapping(value = "/drc")
public class CollectionsNavController {

	Logger logger = LoggerFactory.getLogger(getClass());

//	@Autowired
//	private DataBase db;

//	@RequestMapping(value = "/chapters", method = RequestMethod.GET)
//	public String getChapters(@RequestParam("volumeId") String volumeId, Model model) {
//		Volume volume = db.getVolumeRepository().findOne(volumeId);
//		List<Chapter> chapters = db.getChapterRepository().findByVolumeId(volumeId);
//		model.addAttribute("chapters", chapters);
//		model.addAttribute("volume", volume);
//		return "fragments/chapter-list :: chapter-list";
//	}

	@RequestMapping(value = "/pages", method = RequestMethod.GET)
	public String getPages(@RequestParam("chapterId") String chapterId,
			@RequestParam("chapterTitle") String chapterTitle, @RequestParam("volumeId") String volumeId,
			@RequestParam("volumeTitle") String volumeTitle, Model model) {

//		List<Page> pages = db.getPageRepository().findByChapterIds(Arrays.asList(new String[] { chapterId }));
//
//		model.addAttribute("pages", pages);
//		model.addAttribute("chapterTitle", chapterTitle);
//		model.addAttribute("chapterId", chapterId);
//		model.addAttribute("volumeId", volumeId);
//		model.addAttribute("volumeTitle", volumeTitle);
		return "fragments/page-list :: page-list";
	}

}
