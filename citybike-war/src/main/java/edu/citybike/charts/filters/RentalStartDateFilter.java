package edu.citybike.charts.filters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.citybike.model.Rent;

public class RentalStartDateFilter extends Filter<Rent, Date> {

	@Override
	public List<Rent> filtrate(List<Rent> data) {
		List<Rent> filtratedList = new ArrayList<Rent>();
		boolean valid = false;

		for (Rent rent : data) {
			for (FilterModel<Date> filter : filters) {
				valid = filter.checkCondition(rent.getStartDate());
			}
			if (valid) {
				filtratedList.add(rent);
			}
		}
		return filtratedList;
	}

}