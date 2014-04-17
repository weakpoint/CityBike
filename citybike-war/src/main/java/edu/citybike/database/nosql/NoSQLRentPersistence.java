package edu.citybike.database.nosql;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterOperator;

import edu.citybike.database.exception.ModelAlreadyExistsException;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Rent;

public class NoSQLRentPersistence extends NoSQLModelPersistence<Rent> {

	public void save(Rent model) throws PersistenceException {
		Entity entity = new Entity("Rent");
		

		entity.setProperty("rentalDuration", model.getRentalDuration());
		entity.setProperty("userCode", model.getUserCode());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		entity.setProperty("rentCode", model.getRentCode());

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(generateQuery("Rent", "rentCode", FilterOperator.EQUAL,
				model.getRentCode()));

		if (pq.asSingleEntity() != null) {
			throw new ModelAlreadyExistsException("Model already exists");
		}
		save(entity);	
		
		
	}

	public void update(Rent model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	public void delete(Rent model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	public List<Rent> getAll(String rentalNetworkCode)
			throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}



}
