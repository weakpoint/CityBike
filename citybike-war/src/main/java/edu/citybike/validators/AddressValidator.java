package edu.citybike.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.citybike.model.Address;

public class AddressValidator implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
		return Address.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
	
	}
}
