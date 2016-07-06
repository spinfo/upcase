package de.uni_koeln.spinfo.upcase.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

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
	public String init() {
		return "home";
	}

	@RequestMapping(value = "/about")
	public ModelAndView getInfo() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/contact.html")
	public @ResponseBody ModelAndView getContacts() {
		return new ModelAndView("contact");
	}

}
