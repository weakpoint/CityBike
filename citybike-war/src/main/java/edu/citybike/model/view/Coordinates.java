package edu.citybike.model.view;

public class Coordinates {

	public String longitude;
	public String latitude;
	
	public String getLongitude() {
		System.out.println("lon: "+longitude);
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
		System.out.println("lon: "+longitude);
	}
	public String getLatitude() {
		System.out.println("Lat: "+latitude);
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
		System.out.println("Lat: "+latitude);		
	}
	
	
}
