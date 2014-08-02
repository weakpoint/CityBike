package edu.citybike.model;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class Bike {

	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Key key;
	@Embedded
	private TechnicalDetails technicalDetails;
	private STATUS status;
	private int rentalCount;
	private Date lastServiceDate;
	private Key rentalOfficeKey;
	
	public enum STATUS {OUT_OF_ORDER, RENTED, IN_REPAIR, AVAILABLE};
	
	public Bike() {
		this.technicalDetails = new TechnicalDetails();
		this.status = STATUS.AVAILABLE;
		this.rentalCount =  0;
		this.lastServiceDate = new Date();
	}
	
	public TechnicalDetails getTechnicalDetails() {
		return technicalDetails;
	}
	public void setTechnicalDetails(TechnicalDetails technicalDetails) {
		this.technicalDetails = technicalDetails;
	}
	public STATUS getStatus() {
		return status;
	}
	public String getStatusString() {
		switch (status){
		case OUT_OF_ORDER:
			return "OUT_OF_ORDER";
		case RENTED:
			return "RENTED";
		case IN_REPAIR:
			return "IN_REPAIR";
		case AVAILABLE:
			return "AVAILABLE";
		}
		return "";

	}
	public void setStatus(String status) {
		switch (status){
		case "OUT_OF_ORDER":
			this.status = STATUS.OUT_OF_ORDER;
			break;
		case "RENTED":
			this.status = STATUS.RENTED;
			break;
		case "IN_REPAIR":
			this.status = STATUS.IN_REPAIR;
			break;
		case "AVAILABLE":
			this.status = STATUS.AVAILABLE;
		}
		
	}
	public void setStatus(STATUS status) {
		setStatus(status.toString());
	}
	
	public int getRentalCount() {
		return rentalCount;
	}
	public void setRentalCount(int rentalCount) {
		this.rentalCount = rentalCount;
	}
	
	public Date getLastServiceDate() {
		return lastServiceDate;
	}

	public void setLastServiceDate(Date lastServiceDate) {
		this.lastServiceDate = lastServiceDate;
	}
	
	
	public Key getRentalOfficeKey() {
		return rentalOfficeKey;
	}

	public void setRentalOfficeKey(Key rentalOfficeKey) {
		this.rentalOfficeKey = rentalOfficeKey;
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key bikeKey) {
		this.key = bikeKey;
	}


	
}