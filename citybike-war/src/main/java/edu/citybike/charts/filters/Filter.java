package edu.citybike.charts.filters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import edu.citybike.utilities.ControllerUtilities;

public class Filter<S, T> {

	private String methodName;
	protected List<FilterModel<T>> filters;

	public Filter(String fieldName) {
		this(fieldName, null);
	}

	public Filter(String fieldName, FilterModel<T> filter) {
		filters = new ArrayList<>();

		if (fieldName != null & fieldName.length() > 0) {
			this.methodName = ControllerUtilities.createGetterMethodName(fieldName);
		} else {
			this.methodName = "";
		}

		if (filter != null) {
			filters.add(filter);
		}
	}

	public void addFilter(FilterModel<T> filter) {
		filters.add(filter);
	}

	public void addFilters(List<FilterModel<T>> filters) {
		this.filters.addAll(filters);
	}

	public List<S> filtrate(List<S> data) {
		List<S> filtratedList = new ArrayList<S>();
		boolean valid = false;
		
		if (data.size() > 0) {
			Class<?> clazz = data.get(0).getClass();
			Method method;
			try {
				method = clazz.getDeclaredMethod(methodName);
			System.out.println("Method: "+methodName);
			System.out.println("Data: "+data.size());
			System.out.println("Filter: "+filters.get(0).getClass());
			for (S obj : data) {
				for (FilterModel<T> filter : filters) {
					valid = filter.checkCondition((T)method.invoke(obj));
					System.out.println(" (" + filter.getOperator() + ") " + filter.getValue()
							+ " --> " + valid);
					if (!valid) {
						break;
					}
				}
				if (valid) {
					filtratedList.add(obj);
				}
			}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				System.out.println("Error during filtration: "+e.getMessage());
			}
		}
		return filtratedList;
	}

}
