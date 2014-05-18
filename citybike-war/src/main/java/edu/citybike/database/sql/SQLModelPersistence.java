package edu.citybike.database.sql;

import java.util.List;

import com.google.appengine.api.datastore.Transaction;

import edu.citybike.database.ModelPersistence;
import edu.citybike.database.exception.PersistenceException;

public class SQLModelPersistence<T> implements ModelPersistence<T> {

	@Override
	public T save(T model) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(T model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(T model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<T> getAll(String rentalNetworkCode) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction getTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

}
