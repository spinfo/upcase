package de.uni_koeln.spinfo.upcase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;

@Controller
public class HomeController {

	@Autowired private CollectionRepository collectionRepository;

	@RequestMapping(value = "/")
	public String init(Model model) {
		List<Collection> collections = collectionRepository.findAll();
		model.addAttribute("collections", collections);
		return "home";
	}

}
