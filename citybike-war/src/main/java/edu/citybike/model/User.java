package edu.citybike.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.google.appengine.api.datastore.Key;

@Entity
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private String name;
	private String lastName;
	private String externalID;
	@Embedded
	private Address address;
	private String phoneNumber;
	
	@Column(unique=true, nullable=false) 
	private String emailAddress;
	private boolean activeRental;
	private String role;
	private Date registrationDate;
	private Date lastSuccessLogin;
	private Date lastFailedLogin;
	
	@Transient
	public static String USER = "USER";
	@Transient
	public static String ADMINISTRATOR = "ADMINISTRATOR"; 

	public User() {
		this.name = "";
		this.lastName = "";
		this.address = new Address();
		this.phoneNumber = "";
		this.emailAddress = "";
		this.activeRental = false;
		this.externalID = "";
		this.role = USER;
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


	public boolean hasActiveRental() {
		return activeRental;
	}

	public void setActiveRental(boolean activeRental) {
		this.activeRental = activeRental;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getLastSuccessLogin() {
		return lastSuccessLogin;
	}

	public void setLastSuccessLogin(Date lastSuccessLogin) {
		this.lastSuccessLogin = lastSuccessLogin;
	}

	public Date getLastFailedLogin() {
		return lastFailedLogin;
	}

	public void setLastFailedLogin(Date lastFailedLogin) {
		this.lastFailedLogin = lastFailedLogin;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key userKey) {
		this.key = userKey;
	}

	public String getExternalID() {
		return externalID;
	}

	public void setExternalID(String externalID) {
		this.externalID = externalID;
	}

	@Override
	public String toString() {
		return "User [key=" + key + ", name=" + name + ", lastName=" + lastName + ", externalID=" + externalID
				+ ", address=" + address + ", phoneNumber=" + phoneNumber + ", emailAddress=" + emailAddress
				+ ", activeRental=" + activeRental + ", role=" + role + ", registrationDate=" + registrationDate
				+ ", lastSuccessLogin=" + lastSuccessLogin + ", lastFailedLogin=" + lastFailedLogin + "]";
	}

	

}
