package edu.citybike.model;

public class Fee {

	private int time;
	private double fee;
	private String feeCode;
	private String rentalNetworkCode;

	public Fee() {
		this.time = 0;
		this.fee = 0;
		this.feeCode = "";
		this.rentalNetworkCode = "";
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
	public String getFeeCode() {
		return feeCode;
	}
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}
	public String getRentalNetworkCode() {
		return rentalNetworkCode;
	}
	public void setRentalNetworkCode(String rentalNetworkCode) {
		this.rentalNetworkCode = rentalNetworkCode;
	}
}
