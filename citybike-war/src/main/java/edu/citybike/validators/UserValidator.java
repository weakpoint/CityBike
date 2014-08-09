package edu.citybike.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.citybike.model.Address;
import edu.citybike.model.User;

public class UserValidator implements Validator {
	private Validator addressValidator;

	public UserValidator(Validator addressValidator) {
		if (addressValidator == null) {
			throw new IllegalArgumentException("The supplied [Validator] is required and must not be null.");
		}
		if (!addressValidator.supports(Address.class)) {
			throw new IllegalArgumentException(
					"The supplied [Validator] must support the validation of [Address] instances.");
		}
		this.addressValidator = addressValidator;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		User user = (User) obj;

		if (user.getName().length() < 2 || !user.getName().matches("\\w{2,}")) {
			errors.rejectValue("name", "user.name");
		}

		if (user.getLastName().length() < 2 || !user.getLastName().matches("\\w{2,}")) {
			errors.rejectValue("lastName", "user.lastname");
		}

		if (!user.getPhoneNumber().matches("^[1-9][0-9]{8}$")) {
			errors.rejectValue("phoneNumber", "user.phonenumber");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress",
				"user.emailaddress", "Pole nie może być puste");
		
		if (!user.getEmailAddress().matches("^[a-zA-Z0-9_%+-][a-zA-Z0-9_%+-\\.]*@[a-zA-Z0-9_%+-][a-zA-Z0-9_%+-\\.]*[a-zA-Z0-9_%+-]\\.[a-zA-Z]{2,6}$")) {
			errors.rejectValue("emailAddress", "user.emailaddress");
		}
		
		// Address
		try {
			errors.pushNestedPath("address");
			ValidationUtils.invokeValidator(this.addressValidator, user.getAddress(), errors);
		} finally {
			errors.popNestedPath();
		}
	}

}
