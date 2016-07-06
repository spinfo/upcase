package de.uni_koeln.spinfo.upcase.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import de.uni_koeln.spinfo.upcase.CollectionAlreadyExistsException;
import de.uni_koeln.spinfo.upcase.model.form.UploadForm;
import de.uni_koeln.spinfo.upcase.model.validator.UploadFormValidator;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepositoryImpl;
import de.uni_koeln.spinfo.upcase.service.CollectionService;

@Controller
public class CollectionController {

	// @ModelAttribute("encodings")
	// public Encoding[] getEncodings() {
	// return Encoding.values();
	// }

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CollectionService collectionService;

	@Autowired
	private CollectionRepositoryImpl collectionRepository;

	@InitBinder("uploadForm")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new UploadFormValidator());
	}

	@RequestMapping(value = "/user/collection/{id}", method = RequestMethod.GET)
	public String userCollection(@PathVariable("id") String id, Model model) {
		model.addAttribute("collection", collectionRepository.findbyId(id));
		return "user/userCollection";
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/create/collection", method = RequestMethod.GET)
	public String uploadFiles(UploadForm uploadForm, Model model) {
		model.addAttribute("uploadForm", uploadForm);
		return "upload";
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/create/collection", method = RequestMethod.POST)
	public String uploadFiles(@Valid UploadForm uploadForm, BindingResult bindingResult) throws CollectionAlreadyExistsException {

		if (bindingResult.hasErrors()) {
			logger.error("Errors detected, error count: " + bindingResult.getGlobalErrorCount());
			return "upload";
		}
		String collectionId = collectionService.createCollection(uploadForm);
		return "redirect:/home";
	}

	private String getUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
