package de.uni_koeln.spinfo.upcase.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import de.uni_koeln.spinfo.upcase.mongodb.repository.UpcaseUserRepository;

@Secured("ROLE_ADMIN")
@Controller()
public class AdminController {
	
	@Autowired
	private UpcaseUserRepository upcaseUserRepository;
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView main() {
		return new ModelAndView("/admin/main");
	}
	
	@RequestMapping(value = "/admin/deleteAll", method = RequestMethod.GET)
	public ModelAndView deleteAll() {
		upcaseUserRepository.deleteAll();
		return main();
	}

}
