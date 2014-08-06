package edu.citybike.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.citybike.model.view.NewBike;

public class NewBikeValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return NewBike.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "technicalDetails.name",
				"technicalDetails.name", "Nie może być puste");	
	}
}
