package edu.citybike.model;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class RentalOffice  implements Serializable{

	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key key;
	
	@Embedded
	private Address address;
	private String longitude;
	private String latitude;
		
	public RentalOffice() {
		this.address = new Address();
		this.longitude = "";
		this.latitude = "";
	}
	
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

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
}
