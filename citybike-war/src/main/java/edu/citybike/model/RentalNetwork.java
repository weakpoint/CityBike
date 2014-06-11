package edu.citybike.model;

public class RentalNetwork {

	private String rentalNetworkCode;
	private String name;

	public RentalNetwork() {
		this.rentalNetworkCode = "";
		this.name = "";
	}
	public String getRentalNetworkCode() {
		return rentalNetworkCode;
	}
	public void setRentalNetworkCode(String rentalNetworkCode) {
		this.rentalNetworkCode = rentalNetworkCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
