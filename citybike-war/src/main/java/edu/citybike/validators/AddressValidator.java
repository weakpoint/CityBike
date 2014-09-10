package edu.citybike.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.citybike.model.Address;

public class AddressValidator implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
		return Address.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		Address adr = (Address) obj;
		
		if(adr.getCity().length() < 2 || !adr.getCity().matches("^[[a-zA-ZążźćńłśęóŻŹĆŁŚ]]+.*$")){
			errors.rejectValue("city", "address.city");
		}
		
		if(adr.getStreet().length() < 2 || !adr.getStreet().matches("^[[a-zA-ZążźćńłśęóŻŹĆŁŚ]]+.*$")){
			errors.rejectValue("street", "address.street");
		}
		
		if(!adr.getHouseNumber().matches("^[1-9][0-9]*[a-zA-Z]*$")){
			errors.rejectValue("houseNumber", "address.houseNumber");
		}

		if(adr.getFlatNumber().length() > 0 && !adr.getFlatNumber().matches("[1-9][0-9]*")){
			errors.rejectValue("flatNumber", "address.flatNumber");
		}

		if(!adr.getPostalCode().matches("^[0-9]{2}-[0-9]{3}$")){
			errors.rejectValue("postalCode", "address.postalCode");
		}
	
	}
}
