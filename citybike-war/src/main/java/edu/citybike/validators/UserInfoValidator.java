package edu.citybike.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.citybike.model.User;
import edu.citybike.model.view.UserInfo;

public class UserInfoValidator implements Validator {
	private Validator userValidator;

	public UserInfoValidator(Validator userValidator) {
		if (userValidator == null) {
			throw new IllegalArgumentException("The supplied [Validator] is required and must not be null.");
		}
		if (!userValidator.supports(User.class)) {
			throw new IllegalArgumentException(
					"The supplied [Validator] must support the validation of [User] instances.");
		}
		this.userValidator = userValidator;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UserInfo.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		UserInfo user = (UserInfo) obj;

		if (user.getExternalID().isEmpty() && !user.getPassword().equals(user.getRepeatpassword())) {
			errors.rejectValue("password", "userinfo.password.repeat");
			errors.rejectValue("repeatpassword", "userinfo.password.repeat");
		}
		
		if (user.getExternalID().isEmpty() && !user.getPassword().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})")) {
			errors.rejectValue("password", "userinfo.password.wrongpassword");
		}

		// User
		try {
			errors.pushNestedPath("user");
			ValidationUtils.invokeValidator(this.userValidator, user.getUser(), errors);
		} finally {
			errors.popNestedPath();
		}
	}

}
