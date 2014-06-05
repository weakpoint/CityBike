package edu.citybike.database.nosql;

import java.util.ArrayList;
import java.util.List;

import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Credentials;

public class NoSQLCredentialsPersistence extends NoSQLModelPersistence<Credentials> {

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
		ArrayList<Credentials> list = new ArrayList<Credentials>();
		Credentials c = new Credentials();
		c.setEmailAddress("test");
		c.setPassword("pass");
		c.setRentalNetworkCode("0001");
		list.add(c);
		return list;
	}

}
