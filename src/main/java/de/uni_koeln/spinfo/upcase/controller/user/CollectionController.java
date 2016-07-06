package de.uni_koeln.spinfo.upcase.controller.user;

import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.uni_koeln.spinfo.upcase.CollectionAlreadyExistsException;
import de.uni_koeln.spinfo.upcase.model.form.UploadForm;
import de.uni_koeln.spinfo.upcase.model.validator.UploadFormValidator;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;
import de.uni_koeln.spinfo.upcase.service.CollectionService;

@Controller
public class CollectionController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CollectionService collectionService;

	@Autowired
	private CollectionRepository collectionRepository;
	
	@Autowired
	private UpcaseUserRepository upcaseUserRepository;

	@InitBinder("uploadForm")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new UploadFormValidator());
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/user/collection/{id}", method = RequestMethod.GET)
	public String userCollection(@PathVariable("id") String id, Model model) {
		Collection collection = collectionRepository.findbyId(id);
		UpcaseUser owner = upcaseUserRepository.findById(collection.getOwnerId());
		model.addAttribute("collection", collection);
		model.addAttribute("owner", owner);
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
	public String uploadFiles(@Valid UploadForm uploadForm, BindingResult bindingResult) throws CollectionAlreadyExistsException, InterruptedException, ExecutionException {
		if (bindingResult.hasErrors()) {
			logger.error("Errors detected, error count: " + bindingResult.getGlobalErrorCount());
			return "upload";
		}
		String collectionId = collectionService.createCollection(uploadForm);
		return "redirect:/user/collection/" + collectionId;
	}

}
