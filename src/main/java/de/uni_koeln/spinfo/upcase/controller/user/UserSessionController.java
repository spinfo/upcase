package de.uni_koeln.spinfo.upcase.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserSessionController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute("userName")
	public Model userName(Model model) {
		model.addAttribute("userName", getUserName());
		return model;
	}

	@RequestMapping(value = { "/login" })
	public String login() {
		return "login";
	}

	@RequestMapping(value = { "/something_went_wrong" })
	public @ResponseBody String accessDenied() {
		return "<h1>Ups, something went wrong</h1><p>Please contact your local administrator for help!</p> ";
	}
	
	private String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = "unknown";
		if (auth != null)
			userName = auth.getName();
		return userName;
	}

}
