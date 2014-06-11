package edu.citybike.model;

import java.util.Date;

public class Rent {
	private final int MILS_IN_MIN = (1000*60);
	private Integer rentDuration;
	private Date startDate;
	private Date endDate;
	private boolean active;
	private double rentCost;
	private String userCode;
	private String bikeCode;
	private String rentCode;
	private String rentalOfficeCode;
	private String rentalNetworkCode;
	

	
	public Rent() {
		this.rentDuration = 0;
		this.startDate = new Date();
		this.endDate = new Date();
		this.active = false;
		this.rentCost = 0;
		this.userCode = "";
		this.bikeCode = "";
		this.rentalOfficeCode = "";
		this.rentalNetworkCode = "";
		this.rentCode = "";
	}

	public Integer getRentDuration() {
		return rentDuration;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		rentDuration = Math.round(((endDate.getTime()-startDate.getTime())/MILS_IN_MIN));
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public double getRentCost() {
		return rentCost;
	}

	public void setRentCost(double rentCost) {
		this.rentCost = rentCost;
	}

	public String getBikeCode() {
		return bikeCode;
	}

	public void setBikeCode(String bikeCode) {
		this.bikeCode = bikeCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
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

	public String getRentCode() {
		return rentCode;
	}

	public void setRentCode(String rentCode) {
		this.rentCode = rentCode;
	}

}
