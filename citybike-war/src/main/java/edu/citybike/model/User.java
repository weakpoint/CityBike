package edu.citybike.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Text;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String lastName;
	private Address address;
	private String phoneNumber;
	private String emailAddress;
	private Text notes;
	private String role;
	private Long overallRentalTime;
	private Double overallRentalCost;
	private String userCode;
	private String rentalNetworkCode;
	public static String USER = "USER";
	public static String EMPLOYEE = "EMPLOYEE";
	public static String ADMINISTRATOR = "ADMINISTRATOR"; 
	public static String SUPERADMIN = "SUPERADMIN";

	public User(String name, String lastName, Address address, String phoneNumber, String emailAddress, Text notes,
			String role, Long overallRentalTime, Double overallRentalCost, String userCode, String rentalNetworkCode) {

		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.notes = notes;
		this.role = role;
		this.overallRentalTime = overallRentalTime;
		this.overallRentalCost = overallRentalCost;
		this.userCode = userCode;
		this.rentalNetworkCode = rentalNetworkCode;

	}

	public User() {
		this.name = "";
		this.lastName = "";
		this.address = new Address();
		this.phoneNumber = "";
		this.emailAddress = "";
		this.notes = new Text("");
		this.role = USER;
		this.overallRentalTime = (long) 0;
		this.overallRentalCost =  (double) 0;
		this.userCode = "";
		this.rentalNetworkCode = "";

	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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


	public Double getOverallRentalCost() {
		return overallRentalCost;
	}

	public void setOverallRentalCost(Double overallRentalCost) {
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", lastName=" + lastName + ", address=" + address + ", phoneNumber="
				+ phoneNumber + ", emailAddress=" + emailAddress + ", notes=" + notes + ", role=" + role
				+ ", overallRentalTime=" + overallRentalTime + ", overallRentalCost=" + overallRentalCost
				+ ", userCode=" + userCode + ", rentalNetworkCode=" + rentalNetworkCode + "]";
	}


}
