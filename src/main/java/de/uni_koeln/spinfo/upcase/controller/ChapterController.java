package de.uni_koeln.spinfo.drc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.uni_koeln.spinfo.drc.mongodb.DataBase;
import de.uni_koeln.spinfo.drc.mongodb.data.document.Chapter;
import de.uni_koeln.spinfo.drc.mongodb.data.document.Volume;

@Controller
@RequestMapping(value = "/drc")
public class ChapterController {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DataBase db;

	@RequestMapping(value = "/chapters", method = RequestMethod.GET)
	public String getChapters(@RequestParam("volumeId") String volumeId, Model model) {
		Volume volume = db.getVolumeRepository().findOne(volumeId);
		List<Chapter> chapters = db.getChapterRepository().findByVolumeId(volumeId);
		model.addAttribute("chapters", chapters);
		model.addAttribute("volume", volume);
		return "fragments/chapter-list :: chapter-list";
	}

}
