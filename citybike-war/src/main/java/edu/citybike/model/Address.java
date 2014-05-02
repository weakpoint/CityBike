package edu.citybike.model;

import java.io.Serializable;

public class Address implements Serializable{
	private String city;
	private String street;
	private String houseNumber;
	private String flatNumber;
	private String postalCode;

	public Address() {
		this.city = "";
		this.street = "";
		this.houseNumber = "";
		this.flatNumber = "";
		this.postalCode = "";
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(String flatNumber) {
		this.flatNumber = flatNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
