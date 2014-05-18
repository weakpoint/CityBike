package edu.citybike.database.sql;

import java.util.List;

import edu.citybike.database.exception.PersistenceException;
import edu.citybike.database.nosql.NoSQLModelPersistence;
import edu.citybike.model.Fee;

public class SQLFeePersistence extends SQLModelPersistence<Fee> {

	@Override
	public Fee save(Fee model) throws PersistenceException {
		System.out.println("Fee: "+model.getFee()+" "+model.getTime());
		return model;
	}

	@Override
	public void update(Fee model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Fee model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Fee> getAll(String rentalNetworkCode) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}


}
