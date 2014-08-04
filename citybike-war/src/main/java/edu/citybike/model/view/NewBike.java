package edu.citybike.model.view;

import edu.citybike.model.TechnicalDetails;

public class NewBike {

	private  TechnicalDetails technicalDetails;

	private String rentalOfficeCode;
	
	public TechnicalDetails getTechnicalDetails() {
		return technicalDetails;
	}
	public void setTechnicalDetails(TechnicalDetails technicalDetails) {
		this.technicalDetails = technicalDetails;
	}
	
	
	public String getRentalOfficeCode() {
		return rentalOfficeCode;
	}

	public void setRentalOfficeCode(String rentalOfficeCode) {
		this.rentalOfficeCode = rentalOfficeCode;
	}
}
