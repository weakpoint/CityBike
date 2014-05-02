package edu.citybike.model;


public class RentalOffice {

	private Address address;
	private String longitude;
	private String latitude;
	private String rentalOfficeCode;
	private String rentalNetworkCode;
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
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
