package de.uni_koeln.spinfo.upcase.controller.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import de.uni_koeln.spinfo.upcase.model.RegistrationForm;
import de.uni_koeln.spinfo.upcase.model.RegistrationFormValidator;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.UpcaseUserRepository;

@Controller
public class RegistrationController {

	@Autowired
	private UpcaseUserRepository upcaseUserRepository;

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
	public String uploadFiles(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "signup";
		}

		upcaseUserRepository.save(new UpcaseUser(registrationForm));
		// TODO: Security - USER_ROLE
		return "redirect:/userCollection";
	}

}
