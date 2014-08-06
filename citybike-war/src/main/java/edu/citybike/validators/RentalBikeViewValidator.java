package edu.citybike.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.citybike.model.view.RentalBikeView;

public class RentalBikeViewValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		
		return RentalBikeView.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		RentalBikeView rental = (RentalBikeView) obj;
		
		if(rental.getName().length() < 2 || !rental.getName().matches("\\w{2,}")){
			errors.rejectValue("name", "rentalBikeView.name");
		}
		
		if(rental.getLastName().length() < 2 || !rental.getLastName().matches("\\w{2,}")){
			errors.rejectValue("lastName", "rentalBikeView.lastname");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userkey",
				"rentalBikeView.userkey", "Pole nie może być puste");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bikekey",
				"rentalBikeView.bikekey", "Pole nie może być puste");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentalofficekey",
				"rentalBikeView.rentalofficekey", "Pole nie może być puste");

		
	}

}
