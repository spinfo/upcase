package de.uni_koeln.spinfo.upcase.model.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import de.uni_koeln.spinfo.upcase.model.form.UploadForm;

public class UploadFormValidator implements Validator {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UploadForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UploadForm uploadForm = (UploadForm) target;
		
		
		for (MultipartFile multipartFile : uploadForm.getFiles()) {
			if(multipartFile.getOriginalFilename().isEmpty()) {
				logger.info("FILES NOT UPLOADED");
				errors.rejectValue("files", "NotBlank.uploadForm.multiPart");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "collectionName", "NotBlank.uploadForm.collectionName");
	}

}
