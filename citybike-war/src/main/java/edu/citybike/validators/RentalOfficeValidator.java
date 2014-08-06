package edu.citybike.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.citybike.model.Address;
import edu.citybike.model.RentalOffice;

public class RentalOfficeValidator implements Validator {

	private Validator addressValidator;

	public RentalOfficeValidator(Validator addressValidator) {
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
		return RentalOffice.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		RentalOffice rentalOffice = (RentalOffice) obj;

		if (!rentalOffice.getLongitude().matches("[1-9][0-9]{0,2}\\.[0-9]{6}")) {
			errors.rejectValue("latitude", "latitude");
		}

		if (!rentalOffice.getLongitude().matches("[1-9][0-9]{0,2}\\.[0-9]{6}")) {
			errors.rejectValue("latitude", "latitude");
		}

		// Address
		try {
			errors.pushNestedPath("address");
			ValidationUtils.invokeValidator(this.addressValidator, rentalOffice.getAddress(), errors);
		} finally {
			errors.popNestedPath();
		}
		
	}
	
}
