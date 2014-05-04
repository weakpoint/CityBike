package edu.citybike.model;

import java.util.Date;

public class Bike {

	private TechnicalDetails technicalDetails;
	private STATUS status;
	//private Integer rentalCount;
	private Long rentalCount;
	private Date lastServiceDate;
	private String bikeCode;
	private String rentalOfficeCode;
	private String rentalNetworkCode;
	
	public enum STATUS {OUT_OF_ORDER, RENTED, IN_REPAIR, AVAILABLE};
	
	public Bike() {
		this.technicalDetails = new TechnicalDetails();
		this.status = STATUS.AVAILABLE;
		this.rentalCount = (long) 0;
		this.lastServiceDate = new Date();
		this.bikeCode = "";
		this.rentalOfficeCode = "";
		this.rentalNetworkCode = "";
	}
	public TechnicalDetails getTechnicalDetails() {
		return technicalDetails;
	}
	public void setTechnicalDetails(TechnicalDetails technicalDetails) {
		this.technicalDetails = technicalDetails;
	}
	public STATUS getStatus() {
		return status;
	}
	public String getStatusString() {
		switch (status){
		case OUT_OF_ORDER:
			return "OUT_OF_ORDER";
		case RENTED:
			return "RENTED";
		case IN_REPAIR:
			return "IN_REPAIR";
		case AVAILABLE:
			return "AVAILABLE";
		}
		return "";

	}
	public void setStatus(String status) {
		switch (status){
		case "OUT_OF_ORDER":
			this.status = STATUS.OUT_OF_ORDER;
			break;
		case "RENTED":
			this.status = STATUS.RENTED;
			break;
		case "IN_REPAIR":
			this.status = STATUS.IN_REPAIR;
			break;
		case "AVAILABLE":
			this.status = STATUS.AVAILABLE;
		}
		
	}
	public void setStatus(STATUS status) {
		this.status = status;
	}
/*
	public Integer getRentalCount() {
		return rentalCount;
	}
	public void setRentalCount(Integer rentalCount) {
		this.rentalCount = rentalCount;
	}
	*/
	public Long getRentalCount() {
		return rentalCount;
	}
	public void setRentalCount(Long rentalCount) {
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