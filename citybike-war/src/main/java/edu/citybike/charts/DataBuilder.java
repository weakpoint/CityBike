package edu.citybike.charts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.citybike.charts.datafactories.DataFactory;
import edu.citybike.charts.dividers.Divider;
import edu.citybike.charts.filters.Filter;
import edu.citybike.charts.operations.Operation;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.User;

public class DataBuilder<T> {
private List<Filter<?,T>> filterList = new ArrayList<>();
private Divider<?> divider;
private DataFactory<?> dataFactory;
private Operation operation;

protected Map<String, Long> build(User user) throws PersistenceException{
	List<?> data = dataFactory.getData(user);
	for(Filter<?,T> filter : filterList){
		//filter.filtrate(data);
	}
	return null;
}

public String generateOutputData(User user){
	return "";
}


public void setDivider(Divider<?> divider) {
	this.divider = divider;
}

public void setDataFactory(DataFactory<?> dataFactory) {
	this.dataFactory = dataFactory;
}

public void setOperation(Operation operation) {
	this.operation = operation;
}


public List<Filter<?, T>> getFilterList() {
	return filterList;
}

public void addFilter(Filter<?, T> filter){
	filterList.add(filter);
}

public Divider<?> getDivider() {
	return divider;
}

public DataFactory<?> getDataFactory() {
	return dataFactory;
}

public Operation getOperation() {
	return operation;
}



}
