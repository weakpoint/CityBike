package edu.citybike.model.view;

import edu.citybike.model.Address;
import edu.citybike.model.Credentials;
import edu.citybike.model.User;

public class UserInfo{

	private User user;
	private Credentials credentials;
	private double balance;
	private String repeatpassword;
	private String externalID;

	public UserInfo(){
		this(new User(), new Credentials(), 0);
	}

	public UserInfo(User user, Credentials credentials, double balance) {
		this.user = user;
		this.credentials = credentials;
		this.balance = balance;
	}

	public User getUser(){
		return user;
	}
	
	public Credentials getCredentials(){
		return credentials;
	}
	
	public String getPassword() {
		return credentials.getPassword();
	}

	public void setPassword(String password) {
		credentials.setPassword(password);
	}
	
	public String getPhoneNumber() {
		return user.getPhoneNumber();
	}

	public void setPhoneNumber(String phoneNumber) {
		user.setPhoneNumber(phoneNumber);
	}

	public String getName() {
		return user.getName();
	}

	public void setName(String name) {
		user.setName(name);
	}

	public String getLastName() {
		return user.getLastName();
	}

	public void setLastName(String lastName) {
		user.setLastName(lastName);
	}

	public Address getAddress() {
		return user.getAddress();
	}

	public void setAddress(Address address) {
		user.setAddress(address);
	}

	public String getEmailAddress() {
		return user.getEmailAddress();
	}

	public void setEmailAddress(String emailAddress) {
		user.setEmailAddress(emailAddress);
		credentials.setUsername(emailAddress);
	}
	
	public String getRole() {
		return user.getRole();
	}

	public void setRole(String role) {
		user.setRole(role);
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public String getRepeatpassword() {
		return repeatpassword;
	}

	public void setRepeatpassword(String repeatpassword) {
		this.repeatpassword = repeatpassword;
	}
	
	public String getExternalID() {
		return user.getExternalID();
	}

	public void setExternalID(String externalID) {
		user.setExternalID(externalID);
	}

	@Override
	public String toString() {
		return "UserInfo [user=" + user + ", credentials=" + credentials + "]";
	}
	
}
