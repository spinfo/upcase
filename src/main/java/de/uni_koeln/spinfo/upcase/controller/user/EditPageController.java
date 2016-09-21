package de.uni_koeln.spinfo.upcase.controller.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.uni_koeln.spinfo.upcase.model.Annotatable;
import de.uni_koeln.spinfo.upcase.model.AnnotationUpdate;
import de.uni_koeln.spinfo.upcase.model.WordUpdate;
import de.uni_koeln.spinfo.upcase.model.tagset.STTS;
import de.uni_koeln.spinfo.upcase.model.tagset.STTSSmall;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Page;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Word;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.PageRepository;
import de.uni_koeln.spinfo.upcase.service.WordUpdateService;

@Controller
public class EditPageController {
	
	
	@Autowired private PageRepository pageRepository;
	@Autowired private WordUpdateService wordUpdateService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	@ModelAttribute("userName")
	public Model userName(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = "unknown";
		if (auth != null)
			userName = auth.getName();
		model.addAttribute("userName", userName);
		return model;
	}
	
	@ModelAttribute("tagSets")
	public Map<String, Set<Annotatable>> getTagSets() {
		Map<String, Set<Annotatable>> tagSets = new HashMap<String, Set<Annotatable>>();
		
		List<STTS> stts = Arrays.asList(STTS.values());
		Set<Annotatable> sttsTagSet = new HashSet<>(stts);
		tagSets.put(STTS.class.getSimpleName(), sttsTagSet);
		
		List<STTSSmall> sttsSmall = Arrays.asList(STTSSmall.values());
		Set<Annotatable> sttsSmallTagSet = new HashSet<>(sttsSmall);
 		tagSets.put(STTSSmall.class.getSimpleName(), sttsSmallTagSet);
 		
		return tagSets;
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/edit/page/{id}", method = RequestMethod.GET)
	public String editPage(@PathVariable("id") String id, Model model) {
		Page page = pageRepository.findByPageId(id);
		model.addAttribute("page", page);
		return "user/editPage";
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/edit/page/update/word/")
	public @ResponseBody String updateWord(@RequestBody WordUpdate update) {
		Word word = wordUpdateService.update(update);
		if (word != null)
			return HttpStatus.OK.toString();
		return HttpStatus.BAD_REQUEST.toString();
	}
	
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/edit/page/update/word/tag/")
	public @ResponseBody List<Word> updateWordTag(@RequestBody List<AnnotationUpdate> updates) {
		return wordUpdateService.update(updates);
	}
	
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/edit/page/delete/word/tag/")
	public @ResponseBody Word deleteWordTag(@RequestBody AnnotationUpdate toDelete) {
		return wordUpdateService.delete(toDelete);
	}
	
	

}
