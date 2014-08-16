package edu.citybike.charts.dividers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.citybike.model.Rent;

public class RentalStartDateDivider extends Divider<Rent> {

	public RentalStartDateDivider(String fieldName) {
		super(fieldName);
	}

	@Override
	public Map<String, List<Rent>> divide(List<Rent> list) {
		Map<String, List<Rent>> result = new HashMap<String, List<Rent>>();

		for (Rent rent : list) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(rent.getStartDate());

			String key = createMapKey(cal);

			List<Rent> temp = null;
			if ((temp = result.get(key)) == null) {
				temp = new ArrayList<>();
			}
			temp.add(rent);
			result.put(key, temp);
		}
		return result;
	}

	private String createMapKey(Calendar cal) {

		switch (timeInterval) {
		case DAY:
			return cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
		case MONTH:
			return (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
		case YEAR:
			return "" + cal.get(Calendar.YEAR);
		default:
			return "";
		}
	}

}
