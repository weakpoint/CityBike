package edu.citybike.model;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Fee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key feeKey;
	private int time;
	private double fee;

	public Fee() {
		this.time = 0;
		this.fee = 0;
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}

	public Key getFeeKey() {
		return feeKey;
	}

	public void setFeeKey(Key feeKey) {
		this.feeKey = feeKey;
	}
	
}
