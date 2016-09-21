package de.uni_koeln.spinfo.upcase.service.security;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import de.uni_koeln.spinfo.upcase.model.form.RegistrationForm;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;

@Service 
public class AccountService {
	
	@Autowired private UpcaseUserRepository upcaseUserRepository;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public String createAccount(RegistrationForm registrationForm, Model model) {
		UpcaseUser user = upcaseUserRepository.save(new UpcaseUser(registrationForm));
		logger.info("Created new 	user: " + user.getEmail() + " and signed in automatically!");
		
		Authentication authenticate = new UsernamePasswordAuthenticationToken(user, "ignored", user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		model.addAttribute("userName", user.getEmail());
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session = attr.getRequest().getSession(true);
	    session.setAttribute("userName", user.getEmail());
	    
		return "redirect:/";
	}

}
