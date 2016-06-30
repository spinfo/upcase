package de.uni_koeln.spinfo.upcase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	@RequestMapping(value = "/")
	public ModelAndView init() {
		return new ModelAndView("home");
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
