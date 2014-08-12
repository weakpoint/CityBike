package edu.citybike.charts;

public class ChartData {

	private DataBuilder dataBuider;
	private String description;
	
	public ChartData(String description, DataBuilder dataBuider) {
		this.dataBuider = dataBuider;
		this.description = description;
	}
	
	public DataBuilder getDataBuider() {
		return dataBuider;
	}
	public void setDataBuider(DataBuilder dataBuider) {
		this.dataBuider = dataBuider;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
