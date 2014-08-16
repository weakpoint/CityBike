package edu.citybike.charts.operations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class SumOperation extends Operation {

	public SumOperation(String fieldName) {
		super(fieldName);
	}

	@Override
	public Object doOperation(List<?> data) {

		if ("".equals(methodName)) {
			return data.size();
		}

		if (data.size() > 0) {
			Class<?> clazz = data.get(0).getClass();
			try {
				Method method = clazz.getDeclaredMethod(methodName);
				if (double.class.equals(method.getReturnType()) || Double.class.equals(method.getReturnType())) {
					return doubleSummation(data, method);
				}
				if (int.class.equals(method.getReturnType()) || Integer.class.equals(method.getReturnType())
						|| long.class.equals(method.getReturnType()) || Long.class.equals(method.getReturnType())) {
					return longSummation(data, method);
				}

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				System.out.println("Error during [doOperation] " + e.getMessage());
			}
		}
		return 0;
	}

	private long longSummation(List<?> data, Method method) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		long sum = 0;
		for (Object obj : data) {
			sum += (long) method.invoke(obj);
		}
		return sum;
	}

	private double doubleSummation(List<?> data, Method method) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		double sum = 0;
		for (Object obj : data) {
			sum += (double) method.invoke(obj);
		}
		return sum;
	}

}
