package edu.citybike.charts;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

import edu.citybike.exceptions.PersistenceException;

public class ChartData {

	private DataBuilder dataBuider;
	private String description;
	
	public ChartData(String description, DataBuilder dataBuider) {
		this.dataBuider = dataBuider;
		this.description = description;
	}
	
	public void setStartDate(Date startDate){
		dataBuider.setStartDate(startDate);
	}
	
	public void setEndDate(Date endDate){
		dataBuider.setEndDate(endDate);
	}
	
	public void setTimeInterval(TimeInterval timeInterval){
		dataBuider.getDivider().setTimeInterval(timeInterval);
	}
	
	public String generateOutputData(Key userKey) throws PersistenceException{

		String result = "{\"cols\": [{\"id\": \"'A'\", \"label\": \"Przedzial\", \"type\": \"string\"},{\"id\": \"'B'\", \"label\": \"Ilosc\", \"type\":\"number\"}], ";
		result += dataBuider.generateOutputData(userKey)+"}";
		return result;
	}

	public String getDescription() {
		return description;
	}
	
	
}
