package de.uni_koeln.spinfo.upcase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserSessionController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = { "/login"})
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = { "/access_denied"})
	public @ResponseBody String accessDenied(Authentication auth) {
		return "<h1>Access Denied!</h1>" + auth;
	}
	
}
