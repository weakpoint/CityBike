package edu.citybike.charts;

import java.util.Date;

import edu.citybike.charts.filters.Operator;
import edu.citybike.charts.filters.RentalStartDateFilter;
import edu.citybike.model.Rent;

public class RentDataBuilder extends DataBuilder<Rent, Date>{

	@Override
	public void setStartDate(Date startDate){
		addFilter(new RentalStartDateFilter(Operator.GEQ, startDate));
	}
	
	@Override
	public void setEndDate(Date endDate){
		addFilter(new RentalStartDateFilter(Operator.LEQ, endDate));
	}

}
