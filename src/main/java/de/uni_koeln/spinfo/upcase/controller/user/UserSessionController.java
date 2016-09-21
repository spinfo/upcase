package de.uni_koeln.spinfo.upcase.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserSessionController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = { "/login" })
	public String login() {
		return "login";
	}

	@RequestMapping(value = { "/something_went_wrong" })
	public @ResponseBody String accessDenied() {
		return "<h1>Ups, something went wrong</h1><p>Please contact your local administrator for help!</p> ";
	}
	
}
