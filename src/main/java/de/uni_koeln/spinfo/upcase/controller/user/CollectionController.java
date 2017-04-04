package de.uni_koeln.spinfo.upcase.controller.user;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import de.uni_koeln.spinfo.upcase.CollectionAlreadyExistsException;
import de.uni_koeln.spinfo.upcase.exceptions.CollectionNotFoundException;
import de.uni_koeln.spinfo.upcase.model.form.UploadForm;
import de.uni_koeln.spinfo.upcase.model.validator.UploadFormValidator;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Page;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.PageRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;
import de.uni_koeln.spinfo.upcase.service.CollectionService;
import de.uni_koeln.spinfo.upcase.service.security.UpcaseAuthProvider;

@Controller
public class CollectionController {

	@Autowired private CollectionService collectionService;
	@Autowired private CollectionRepository collectionRepository;
	@Autowired private UpcaseUserRepository upcaseUserRepository;
	@Autowired private PageRepository pageRepository;
	@Autowired private UpcaseAuthProvider authProvider;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	@InitBinder("uploadForm")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new UploadFormValidator());
	}

	@RequestMapping(value = "/user/collection/{title}", method = RequestMethod.GET)
	public String userCollection(@PathVariable("title") String title, Model model) {
		Collection collection = collectionRepository.findbyTitle(title);
		if(collection == null || collection.isPrivate()) {
			throw new CollectionNotFoundException();
		}
		UpcaseUser owner = upcaseUserRepository.findById(collection.getOwner());
		List<Page> pages = pageRepository.findByCollectionId(collection.getId());
		model.addAttribute("collection", collection);
		model.addAttribute("owner", owner);
		model.addAttribute("pages", pages);
		return "user/userCollection";
	}
	
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/user/collections/my", method = RequestMethod.GET)
	public String myCollections(Model model) {
		UpcaseUser user = authProvider.getCurrentUser();
		List<Collection> collections = collectionRepository.findByOwner(user.getId());
		model.addAttribute("collections", collections);
		model.addAttribute("user", user);
		return "user/myCollections";
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/create/collection", method = RequestMethod.GET)
	public String uploadFiles(UploadForm uploadForm, Model model) {
		model.addAttribute("uploadForm", uploadForm);
		return "upload";
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/create/collection", method = RequestMethod.POST)
	public String uploadFiles(@Valid UploadForm uploadForm, BindingResult bindingResult, HttpServletRequest request) throws CollectionAlreadyExistsException, InterruptedException, ExecutionException {
		if (bindingResult.hasErrors()) {
			logger.error("Errors detected, error count: " + bindingResult.getGlobalErrorCount());
			return "upload";
		}
		collectionService.createCollection(uploadForm, request.getSession().getServletContext().getRealPath("/"));
		return "redirect:/user/collection/" + uploadForm.getCollectionName();
	}
	
	@ExceptionHandler({CollectionNotFoundException.class})
	public ModelAndView handleResourceNotFoundException(HttpServletRequest req, Exception e) {
		ModelAndView mv = new ModelAndView("notfound"); 
		mv.addObject("errorTitle", "404 - CollectionNotFoundException");
		mv.addObject("errorMessage", "The collection your are looking for does not exist...");
		return mv;
	}

}
