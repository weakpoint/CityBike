package edu.citybike.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.citybike.model.view.UserInfo;

public class UserInfoValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return UserInfo.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		UserInfo user = (UserInfo) obj;
		
		
		
	}

}
