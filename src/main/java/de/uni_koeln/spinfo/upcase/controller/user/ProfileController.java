package de.uni_koeln.spinfo.upcase.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;

@Controller
public class ProfileController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UpcaseUserRepository upcaseUserRepository;

	@Autowired
	private CollectionRepository collectionRepository;

	@ModelAttribute("userName")
	public Model userName(Model model) {
		model.addAttribute("userName", getUserName());
		return model;
	}

	@RequestMapping(value = "user/profile/{id}")
	public String showProfile(@PathVariable("id") String id, Model model) {
		findUser(id, model);
		return "profile";
	}

	@RequestMapping(value = "user/profile/my")
	public String showMyProfile(Model model) {
		String email = getUserName();
		UpcaseUser user = upcaseUserRepository.findByEmail(email);
		findUser(user.getId(), model);
		return "profile";
	}

	private void findUser(String id, Model model) {
		UpcaseUser user = upcaseUserRepository.findById(id);
		List<Collection> collections = collectionRepository.findByOwner(user.getId());
		model.addAttribute("collections", collections);
		model.addAttribute("user", user);
	}

	private String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = "unknown";
		if (auth != null)
			userName = auth.getName();
		return userName;
	}

}
