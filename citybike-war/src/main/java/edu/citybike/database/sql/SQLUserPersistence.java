package edu.citybike.database.sql;

import java.util.List;

import edu.citybike.database.exception.PersistenceException;
import edu.citybike.database.nosql.NoSQLModelPersistence;
import edu.citybike.model.User;

public class SQLUserPersistence extends NoSQLModelPersistence<User> {

	@Override
	public User save(User model) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(User model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(User model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getAll(String rentalNetworkCode) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}



}
