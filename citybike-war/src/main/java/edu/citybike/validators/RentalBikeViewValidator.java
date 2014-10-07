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
		
		if(rental.getName().length() < 2 || !rental.getName().matches("[a-zA-ZążźćńłśęóŻŹĆŁŚ ]{2,}")){
			errors.rejectValue("name", "rentalBikeView.name");
		}
		
		if(rental.getLastName().length() < 2 || !rental.getLastName().matches("[a-zA-ZążźćńłśęóŻŹĆŁŚ ]{2,}")){
			errors.rejectValue("lastName", "rentalBikeView.lastname");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userKey",
				"rentalBikeView.userkey", "Pole nie może być puste");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bikeKey",
				"rentalBikeView.bikekey", "Pole nie może być puste");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentalOfficeKey",
				"rentalBikeView.rentalofficekey", "Pole nie może być puste");

		
	}

}
