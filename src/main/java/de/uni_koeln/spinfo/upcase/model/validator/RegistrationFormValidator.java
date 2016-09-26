package de.uni_koeln.spinfo.upcase.model.validator;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.uni_koeln.spinfo.upcase.model.form.RegistrationForm;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;

public class RegistrationFormValidator implements Validator {

	private UpcaseUserRepository upcaseUserRepository;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	public RegistrationFormValidator(UpcaseUserRepository upcaseUserRepository) {
		this.upcaseUserRepository = upcaseUserRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return RegistrationForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		logger.info(((RegistrationForm) target).toString());

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotBlank.registrationForm.firstName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotBlank.registrationForm.lastName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotBlank.registrationForm.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotBlank.registrationForm.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotBlank.registrationForm.confirmPassword");

		RegistrationForm form = (RegistrationForm) target;

		EmailValidator emailValidator = new EmailValidator();
		if (!emailValidator.isValid(form.getEmail(), null)) {
			errors.rejectValue("email", "Invalid.registrationForm.email");
		}

		if (form.getConfirmPassword() != null && form.getPassword() != null) {
			if(form.getPassword().length() < 5) {
				errors.rejectValue("password", "Length.registrationForm.password");
			}
			if (!form.getConfirmPassword().equals(form.getPassword())) {
				errors.rejectValue("confirmPassword", "NotEqual.registrationForm.confirmPassword");
			}
		}
		
		UpcaseUser user = upcaseUserRepository.findByEmail(form.getEmail());
		
		if (user != null) {
			logger.info("Email " + form.getEmail() + " already exists!");
			errors.rejectValue("email", "Invalid.registrationForm.email.exists");
		}
	}

}
