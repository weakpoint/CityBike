package edu.citybike.charts.dividers;

import java.util.List;
import java.util.Map;

import edu.citybike.charts.TimeInterval;

public abstract class Divider<T> {
	protected TimeInterval timeInterval = TimeInterval.MONTH;
	
	public abstract Map<String, List<T>> divide(List<T> list);

	public void setTimeInterval(TimeInterval timeInterval) {
		this.timeInterval = timeInterval;
	}
	
	
}
