package edu.citybike.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.google.appengine.api.datastore.Key;

@Entity
public class Rent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	@Transient
	private final int MILS_IN_MIN = (1000*60);
	private int rentDuration;
	private Date startDate;
	private Date endDate;
	private boolean active;
	private double rentCost;
	private Key userCode;
	private Key bikeCode;
	private Key rentalOfficeCode;
	
	public Rent() {
		this.rentDuration = 0;
		this.startDate = new Date();
		this.endDate = new Date();
		this.active = false;
		this.rentCost = 0;
	}

	public int getRentDuration() {
		return rentDuration;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		rentDuration = Math.round(((endDate.getTime()-startDate.getTime())/MILS_IN_MIN));
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public double getRentCost() {
		return rentCost;
	}

	public void setRentCost(double rentCost) {
		this.rentCost = rentCost;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key rentKey) {
		this.key = rentKey;
	}

	public Key getUserCode() {
		return userCode;
	}

	public void setUserCode(Key userCode) {
		this.userCode = userCode;
	}

	public Key getBikeCode() {
		return bikeCode;
	}

	public void setBikeCode(Key bikeCode) {
		this.bikeCode = bikeCode;
	}

	public Key getRentalOfficeCode() {
		return rentalOfficeCode;
	}

	public void setRentalOfficeCode(Key rentalOfficeCode) {
		this.rentalOfficeCode = rentalOfficeCode;
	}

	public void setRentDuration(int rentDuration) {
		this.rentDuration = rentDuration;
	}

}
