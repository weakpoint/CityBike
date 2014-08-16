package edu.citybike.charts;

import java.util.Date;

import edu.citybike.charts.filters.DateFilterModel;
import edu.citybike.charts.filters.Filter;
import edu.citybike.charts.filters.Operator;
import edu.citybike.model.User;

public class RegistrationCounterDataBuilder extends DataBuilder<User, Date>{

	@Override
	public void setStartDate(Date startDate){
		addFilter(new Filter<User, Date>("registrationDate", new DateFilterModel(Operator.GEQ, startDate)));
	}
	
	@Override
	public void setEndDate(Date endDate){
		addFilter(new Filter<User, Date>("registrationDate", new DateFilterModel(Operator.LEQ, endDate)));
	}

}
