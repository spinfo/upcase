package de.uni_koeln.spinfo.upcase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;

@Controller
public class HomeController {
	
	@Autowired
	private CollectionRepository collectionRepository;

	@ModelAttribute("userName")
	public Model userName(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = "unknown";
		if (auth != null)
			userName = auth.getName();
		model.addAttribute("userName", userName);
		return model;
	}

	@RequestMapping(value = "/")
	public String init(Model model) {
		List<Collection> collections = collectionRepository.findAll();
		model.addAttribute("collections", collections);
		return "home";
	}

//	@RequestMapping(value = "/about")
//	public ModelAndView getInfo() {
//		return new ModelAndView("index");
//	}

//	@RequestMapping(value = "/contact.html")
//	public @ResponseBody ModelAndView getContacts() {
//		return new ModelAndView("contact");
//	}

}
