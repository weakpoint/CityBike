package edu.citybike.charts.dividers;

import java.util.List;
import java.util.Map;

import edu.citybike.charts.TimeInterval;
import edu.citybike.utilities.ControllerUtilities;

/**
 * 
 * @author Emil Pluciennikowski
 *
 * @param <T> Type of model object
 */
public abstract class Divider<T> {
	protected TimeInterval timeInterval = TimeInterval.MONTH;
	
	protected String methodName;
	
	public Divider(String fieldName){
		if (fieldName != null & fieldName.length() > 0) {
			this.methodName = ControllerUtilities.createGetterMethodName(fieldName);
		} else {
			this.methodName = "";
		}
	}
	
	public abstract Map<String, List<T>> divide(List<T> data);

	public void setTimeInterval(TimeInterval timeInterval) {
		this.timeInterval = timeInterval;
	}
	
	
}
