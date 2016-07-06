package de.uni_koeln.spinfo.upcase.controller.user;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.uni_koeln.spinfo.upcase.model.form.RegistrationForm;
import de.uni_koeln.spinfo.upcase.model.validator.RegistrationFormValidator;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;

@Controller
public class RegistrationController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UpcaseUserRepository upcaseUserRepository;

	@InitBinder("registrationForm")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new RegistrationFormValidator());
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(RegistrationForm registrationForm, Model model) {
		model.addAttribute("registrationForm", registrationForm);
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
			BindingResult bindingResult) {
		UpcaseUser newUser = null;
		if (bindingResult.hasErrors()) {
			logger.error("Errors detected, error count: " + bindingResult.getGlobalErrorCount());
			return "signup";
		}
		UpcaseUser findByEmail = upcaseUserRepository.findByEmail(registrationForm.getEmail());
		if (findByEmail == null) {
			newUser = upcaseUserRepository.save(new UpcaseUser(registrationForm));
			logger.info("Signed up new user: " + newUser);
		} else {
			logger.error("Reject email, already in use: " + registrationForm.getEmail());
			bindingResult.rejectValue("email", "Invalid.registrationForm.email.exists");
			return "signup";
		}
		return "redirect:/user/userCollection/" + newUser.getId();
	}
	
}
