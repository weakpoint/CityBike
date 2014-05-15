package edu.citybike.model.view;

import com.google.appengine.api.datastore.Text;

import edu.citybike.model.Address;
import edu.citybike.model.Credentials;
import edu.citybike.model.User;

public class UserInfo {

	private static final long serialVersionUID = 1L;
	private String name;
	private String lastName;
	private Address address;
	private String phoneNumber;
	private String emailAddress;
	private String password;
	private Text notes;
	private String role;
	private Long overallRentalTime;
	private Long overallRentalCost;
	private String userCode;
	private String rentalNetworkCode;

	public UserInfo(User user, Credentials credentials) {

		this.name = user.getName();
		this.lastName = user.getLastName();
		this.address = user.getAddress();
		this.phoneNumber = user.getPhoneNumber();
		this.emailAddress = user.getEmailAddress();
		this.password = credentials.getPassword();
		this.notes = user.getNotes();
		this.role = user.getRole();
		this.overallRentalTime = user.getOverallRentalTime();
		this.overallRentalCost = user.getOverallRentalCost();
		this.userCode = user.getUserCode();
		this.rentalNetworkCode = user.getRentalNetworkCode();

	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
