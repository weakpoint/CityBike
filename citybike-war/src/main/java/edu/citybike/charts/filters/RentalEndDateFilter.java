package edu.citybike.charts.filters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.citybike.model.Rent;

public class RentalEndDateFilter extends Filter<Rent, Date> {

	public RentalEndDateFilter(Operator operator, Date endDate) {
		super();
		filters.add(new DateFilterModel(operator, endDate));
	}

	@Override
	public List<Rent> filtrate(List<Rent> data) {
		List<Rent> filtratedList = new ArrayList<Rent>();
		boolean valid = false;

		for (Rent rent : data) {
			for (FilterModel<Date> filter : filters) {
				valid = filter.checkCondition(rent.getEndDate());
				if (!valid) {
					break;
				}
			}
			if (valid) {
				filtratedList.add(rent);
			}
		}
		return filtratedList;
	}
}
