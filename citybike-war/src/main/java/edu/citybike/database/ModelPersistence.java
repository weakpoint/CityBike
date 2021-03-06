package edu.citybike.database;

import java.util.List;

import com.google.appengine.api.datastore.Transaction;

import edu.citybike.database.exception.PersistenceException;

public interface ModelPersistence<T> {

	public T save(T model) throws PersistenceException;

	public void update(T model) throws PersistenceException;

	public void delete(T model) throws PersistenceException;

	public List<T> getAll(String rentalNetworkCode) throws PersistenceException;
	
	public Transaction getTransaction();

}