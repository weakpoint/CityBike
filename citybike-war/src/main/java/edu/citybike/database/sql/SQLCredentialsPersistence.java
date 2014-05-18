package edu.citybike.database.sql;

import java.util.List;

import edu.citybike.database.exception.PersistenceException;
import edu.citybike.database.nosql.NoSQLModelPersistence;
import edu.citybike.model.Credentials;

public class SQLCredentialsPersistence extends SQLModelPersistence<Credentials> {

	@Override
	public Credentials save(Credentials model) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Credentials model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Credentials model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Credentials> getAll(String rentalNetworkCode) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}


}
