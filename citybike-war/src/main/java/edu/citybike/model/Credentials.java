package edu.citybike.model;

public class Credentials {

	private String emailAddress;
	private String password;
	private String rentalNetworkCode;

	public Credentials() {
		this.emailAddress = "";
		this.password = "";
		this.rentalNetworkCode = "";
	}


	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRentalNetworkCode() {
		return rentalNetworkCode;
	}

	public void setRentalNetworkCode(String rentalNetworkCode) {
		this.rentalNetworkCode = rentalNetworkCode;
	}


	@Override
	public String toString() {
		return "Credentials [emailAddress=" + emailAddress + ", password=" + password + ", rentalNetworkCode="
				+ rentalNetworkCode + "]";
	}
	
	

}
