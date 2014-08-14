package edu.citybike.charts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;

import edu.citybike.charts.datafactories.DataFactory;
import edu.citybike.charts.dividers.Divider;
import edu.citybike.charts.filters.Filter;
import edu.citybike.charts.operations.Operation;
import edu.citybike.exceptions.PersistenceException;

public abstract class DataBuilder<S,T> {
private List<Filter<S,T>> filterList = new ArrayList<>();
private Divider<S> divider;
private DataFactory<S> dataFactory;
private Operation operation;

protected Map<String, Long> build(Key userKey) throws PersistenceException{
	List<S> data = dataFactory.getData(userKey);
	
	for(Filter<S,?> filter : filterList){
		data = filter.filtrate(data);
	}
	
	Map<String, List<S>> dividedData = divider.divide(data);
	
	Map<String, Long> result = new HashMap<String, Long>();
	Set<String> keySet = dividedData.keySet();
	
	for(String key : keySet){
		result.put(key,operation.doOperation(dividedData.get(key)));
	}

	return result;
}

public abstract void setStartDate(Date startDate);

public abstract void setEndDate(Date endDate);

public String generateOutputData(Key userKey) throws PersistenceException{
	
	return new Gson().toJson(build(userKey), Map.class);
}


public void setDivider(Divider<S> divider) {
	this.divider = divider;
}

public void addFilter(Filter<S,T> filter){
	filterList.add(filter);
}

public void setDataFactory(DataFactory<S> dataFactory) {
	this.dataFactory = dataFactory;
}

public void setOperation(Operation operation) {
	this.operation = operation;
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
