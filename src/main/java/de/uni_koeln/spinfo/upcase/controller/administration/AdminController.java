package de.uni_koeln.spinfo.upcase.controller.administration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;


@Controller()
public class AdminController {
	
	@Autowired
	private UpcaseUserRepository upcaseUserRepository;
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String mainPanel() {
		return"/admin/main";
	}
	
	@RequestMapping(value = "/admin/deleteAll", method = RequestMethod.GET)
	public String deleteAll() {
		upcaseUserRepository.deleteAll();
		return mainPanel();
	}

}
