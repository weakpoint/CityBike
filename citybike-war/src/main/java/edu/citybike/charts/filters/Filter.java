package edu.citybike.charts.filters;

import java.util.List;

public abstract class Filter<S,T> {
	protected List<FilterModel<T>> filters;
	
	public void addFilter(FilterModel<T> filter){
		filters.add(filter);
	}
	
	public void addFilters(List<FilterModel<T>> filters){
		this.filters.addAll(filters);
	}
	
	public abstract List<S> filtrate(List<S> data);

}
