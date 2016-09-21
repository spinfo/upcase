package de.uni_koeln.spinfo.upcase.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;
import de.uni_koeln.spinfo.upcase.service.security.UpcaseAuthProvider;

@Controller
public class ProfileController {
	
	@Autowired private UpcaseUserRepository upcaseUserRepository;
	@Autowired private CollectionRepository collectionRepository;
	@Autowired private UpcaseAuthProvider authProvider;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "user/profile/{id}")
	public String showProfile(@PathVariable("id") String id, Model model) {
		findUser(id, model);
		return "profile";
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "user/profile/my")
	public String showMyProfile(Model model) {
		UpcaseUser user = authProvider.getCurrentUser();
		findUser(user.getId(), model);
		return "profile";
	}

	private void findUser(String id, Model model) {
		UpcaseUser user = upcaseUserRepository.findById(id);
		List<Collection> collections = collectionRepository.findByOwner(user.getId());
		model.addAttribute("collections", collections);
		model.addAttribute("user", user);
	}

}
