package de.uni_koeln.spinfo.upcase.controller.user;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import de.uni_koeln.spinfo.upcase.model.Encoding;
import de.uni_koeln.spinfo.upcase.model.UploadForm;

@Controller
public class UploadController {

	@ModelAttribute("encodings")
	public Encoding[] getEncodings() {
		return Encoding.values();
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView uploadFiles(UploadForm uploadForm) {
		ModelAndView mv = new ModelAndView("upload");
		mv.addObject("uploadForm", uploadForm);
		mv.addObject(getEncodings());
		return mv;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFiles(@Valid UploadForm uploadForm,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "upload";
		}
		// TODO: Persist collection details to data base
		// TODO: Store uploaded files in users working directory
		try {
			byte[] bytes = uploadForm.getMultiPart().getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File("USER_COLLECTIONS_DIR/" + uploadForm.getWorkTitle(),
							uploadForm.getMultiPart().getOriginalFilename())));
			stream.write(bytes);
			stream.close();
			// TODO: OCR on uploaded files
		} catch (Exception e) {
			return e.getMessage();
		}
		return "redirect:/userCollection";
	}

}
