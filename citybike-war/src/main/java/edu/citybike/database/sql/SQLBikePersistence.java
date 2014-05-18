package edu.citybike.database.sql;

import java.util.List;

import edu.citybike.database.exception.PersistenceException;
import edu.citybike.database.nosql.NoSQLModelPersistence;
import edu.citybike.model.Bike;

public class SQLBikePersistence extends SQLModelPersistence<Bike> {

	@Override
	public Bike save(Bike model) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Bike model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Bike model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Bike> getAll(String rentalNetworkCode) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

}
