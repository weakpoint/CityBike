package edu.citybike.charts.operations;

import java.util.List;

public abstract class Operation {
	protected String methodName;

	public Operation(String fieldName) {
		if (fieldName != null & fieldName.length() > 0) {
			StringBuilder sb = new StringBuilder(fieldName);
			sb.setCharAt(0, fieldName.substring(0, 1).toUpperCase().charAt(0));
			sb.insert(0, "get");
			this.methodName = sb.toString();
		} else
			this.methodName = "";
	}

	public abstract Object doOperation(List<?> data);
}
