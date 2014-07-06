package edu.citybike.model;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@Entity
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key userKey;

	private String name;
	private String lastName;
	@Embedded
	private Address address;
	private String phoneNumber;
	private String emailAddress;
	private Text notes;
	private String role;
	//private Long overallRentalTime;
	//private Double overallRentalCost;
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
		this.notes = new Text("");
		this.role = USER;
		//this.overallRentalTime = (long) 0;
		//this.overallRentalCost =  (double) 0;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Key getUserKey() {
		return userKey;
	}

	public void setUserKey(Key userKey) {
		this.userKey = userKey;
	}

	@Override
	public String toString() {
		return "User [userKey=" + userKey + ", name=" + name + ", lastName=" + lastName + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + ", emailAddress=" + emailAddress + ", notes=" + notes + ", role="
				+ role + "]";
	}

}
