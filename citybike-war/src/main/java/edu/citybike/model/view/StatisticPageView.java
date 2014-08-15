package edu.citybike.model.view;

import edu.citybike.charts.TimeInterval;

public class StatisticPageView {
int operation;
TimeInterval timeInterval;
String startInterval;
String endInterval;


public int getOperation() {
	return operation;
}
public void setOperation(int operation) {
	this.operation = operation;
}
public TimeInterval getTimeInterval() {
	return timeInterval;
}
public void setTimeInterval(TimeInterval timeInterval) {
	this.timeInterval = timeInterval;
}
public String getStartInterval() {
	return startInterval;
}
public void setStartInterval(String startInterval) {
	this.startInterval = startInterval;
}
public String getEndInterval() {
	return endInterval;
}
public void setEndInterval(String endInterval) {
	this.endInterval = endInterval;
}
@Override
public String toString() {
	return "StatisticPageView [operation=" + operation + ", timeInterval=" + timeInterval + ", startInterval="
			+ startInterval + ", endInterval=" + endInterval + "]";
}


}
