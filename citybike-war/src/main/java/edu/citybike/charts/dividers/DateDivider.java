package edu.citybike.charts.dividers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateDivider<S> extends Divider<S> {

	public DateDivider(String fieldName) {
		super(fieldName);
	}

	@Override
	public Map<String, List<S>> divide(List<S> data) {
		Map<String, List<S>> result = new HashMap<String, List<S>>();
		if (data.size() > 0) {
			Class<?> clazz = data.get(0).getClass();
			try {
				Method method = clazz.getDeclaredMethod(methodName);

				for (S obj : data) {
					Calendar cal = Calendar.getInstance();
					cal.setTime((Date) method.invoke(obj));

					String key = createMapKey(cal);

					List<S> temp = null;
					if ((temp = result.get(key)) == null) {
						temp = new ArrayList<>();
					}
					temp.add(obj);
					result.put(key, temp);
				}

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				System.out.println("Error during [divide] " + e.getMessage());
			}
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
