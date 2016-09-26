package de.uni_koeln.spinfo.upcase.controller.tree;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TreeViewController {
	
	@RequestMapping(value = { "/tree" })
	public String tree() {
		return "tree";
	}

}
