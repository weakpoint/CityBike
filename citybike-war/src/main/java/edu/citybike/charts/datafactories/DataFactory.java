package edu.citybike.charts.datafactories;

import java.util.List;

import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.User;

public interface DataFactory<T> {
	List<T> getData(User user) throws PersistenceException;
}
