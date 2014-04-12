package edu.citybike.model;

import com.google.appengine.api.datastore.GeoPt;

public class RentalOffice {

	private Address address;
	private GeoPt gpsCord;
	private String rentalOfficeCode;
	private String rentalNetworkCode;
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public GeoPt getGpsCord() {
		return gpsCord;
	}
	public void setGpsCord(GeoPt gpsCord) {
		this.gpsCord = gpsCord;
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
