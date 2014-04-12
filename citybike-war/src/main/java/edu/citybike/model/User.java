package edu.citybike.model;

import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.Text;

public class User {
	private String name;
	private String lastName;
	private Address address;
	private PhoneNumber phoneNumber;
	private Text notes;
	private Long overallRentalTime;
	private Long overallRentalCost;
	private String userCode;
	private String rentalNetworkCode;

	public User() {
		this.name = "";
		this.lastName = "";
		this.address = new Address();
		this.phoneNumber = new PhoneNumber("");
		this.notes = new Text("");
		this.overallRentalTime = new Long(0);
		this.overallRentalCost = new Long(0);
		this.userCode = "";
		this.rentalNetworkCode = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Text getNotes() {
		return notes;
	}

	public void setNotes(Text notes) {
		this.notes = notes;
	}

	public Long getOverallRentalTime() {
		return overallRentalTime;
	}

	public void setOverallRentalTime(Long overallRentalTime) {
		this.overallRentalTime = overallRentalTime;
	}

	public Long getOverallRentalCost() {
		return overallRentalCost;
	}

	public void setOverallRentalCost(Long overallRentalCost) {
		this.overallRentalCost = overallRentalCost;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getRentalNetworkCode() {
		return rentalNetworkCode;
	}

	public void setRentalNetworkCode(String rentalNetworkCode) {
		this.rentalNetworkCode = rentalNetworkCode;
	}

}
