package edu.citybike.charts.operations;

import java.util.List;

import edu.citybike.utilities.ControllerUtilities;

public abstract class Operation {
	protected String methodName;

	public Operation(String fieldName) {
		if (fieldName != null & fieldName.length() > 0) {
			this.methodName = ControllerUtilities.createGetterMethodName(fieldName);
		} else {
			this.methodName = "";
		}
	}

	public abstract Object doOperation(List<?> data);
}
