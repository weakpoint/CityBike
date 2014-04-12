package edu.citybike.model;

import java.util.Date;

public class Bike {

	private TechnicalDetails technicalDetails;
	private String status;
	private Integer rentalCount;
	private Date lastServiceDate;
	private String bikeCode;
	private String rentalOfficeCode;
	private String rentalNetworkCode;

	public TechnicalDetails getTechnicalDetails() {
		return technicalDetails;
	}

	public void setTechnicalDetails(TechnicalDetails technicalDetails) {
		this.technicalDetails = technicalDetails;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRentalCount() {
		return rentalCount;
	}

	public void setRentalCount(Integer rentalCount) {
		this.rentalCount = rentalCount;
	}

	public Date getLastServiceDate() {
		return lastServiceDate;
	}

	public void setLastServiceDate(Date lastServiceDate) {
		this.lastServiceDate = lastServiceDate;
	}

	public String getBikeCode() {
		return bikeCode;
	}

	public void setBikeCode(String bikeCode) {
		this.bikeCode = bikeCode;
	}

	public String getRentalOfficeCode() {
		return rentalOfficeCode;
	}

	public void setRentalOfficeCode(String rentalOfficeCode) {
		this.rentalOfficeCode = rentalOfficeCode;
	}

	public String getRentalNetworkCode() {
		return rentalNetworkCode;
	}

	public void setRentalNetworkCode(String rentalNetworkCode) {
		this.rentalNetworkCode = rentalNetworkCode;
	}

}
