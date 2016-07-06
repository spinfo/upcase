package de.uni_koeln.spinfo.upcase.model.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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
		logger.info(uploadForm.toString());
		
		if(uploadForm.getMultiPart().isEmpty()) {
			errors.rejectValue("multiPart", "NotBlank.uploadForm.multiPart");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "collectionName", "NotBlank.uploadForm.collectionName");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "multiPart", "NotBlank.uploadForm.multiPart");
	}

}
