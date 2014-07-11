package edu.citybike.model.view;

import com.google.appengine.api.datastore.Text;

import edu.citybike.model.Address;
import edu.citybike.model.Credentials;
import edu.citybike.model.User;

public class UserInfo{

	private User user;
	private Credentials credentials;

	public UserInfo(){
		this(new User(), new Credentials());
	}

	public UserInfo(User user, Credentials credentials) {
		this.user = user;
		this.credentials = credentials;
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

	public String getNotes() {
		return user.getNotes().getValue();
	}

	public void setNotes(String notes) {
		user.setNotes(new Text(notes));
	}

	public String getRole() {
		return user.getRole();
	}

	public void setRole(String role) {
		user.setRole(role);
	}

	@Override
	public String toString() {
		return "UserInfo [user=" + user + ", credentials=" + credentials + "]";
	}
	
}
