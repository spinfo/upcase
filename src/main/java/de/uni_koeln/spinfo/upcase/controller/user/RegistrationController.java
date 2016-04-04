package de.uni_koeln.spinfo.drc.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import de.uni_koeln.spinfo.drc.model.RegistrationForm;
import de.uni_koeln.spinfo.drc.model.RegistrationFormValidator;

@Controller
public class RegistrationController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new RegistrationFormValidator());
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView uploadFiles(RegistrationForm registrationForm) {
		ModelAndView mv = new ModelAndView("signup");
		mv.addObject("registrationForm", registrationForm);
		return mv;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String uploadFiles(@Valid RegistrationForm registrationForm,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup";
		}
		return "redirect:/userCollection";
	}

}
