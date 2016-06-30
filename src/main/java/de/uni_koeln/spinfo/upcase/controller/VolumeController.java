package de.uni_koeln.spinfo.upcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import de.uni_koeln.spinfo.upcase.mongodb.Constans;
import de.uni_koeln.spinfo.upcase.mongodb.util.DataBase;

@Controller()
//@RequestMapping(value = "/drc")
public class VolumeController {
	
	@Autowired
	private DataBase db;

	@RequestMapping(value = "/volumes", method = RequestMethod.GET)
	public String getVolumes(Authentication auth, Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = "unknown";
		if(auth != null)
			userName = auth.getName();
		model.addAttribute("volumes", db.getVolumeRepository().findAll());
		model.addAttribute("userName", userName);
		return "/volumes";
	}
	
	
	
	
	
	
	
	

}
