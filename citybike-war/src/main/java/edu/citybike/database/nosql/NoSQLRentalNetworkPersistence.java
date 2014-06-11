package edu.citybike.database.nosql;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterOperator;

import edu.citybike.database.exception.ModelAlreadyExistsException;
import edu.citybike.database.exception.ModelNotExistsException;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.RentalNetwork;

public class NoSQLRentalNetworkPersistence extends NoSQLModelPersistence<RentalNetwork> {

	@Override
	public RentalNetwork save(RentalNetwork model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		PreparedQuery pq = datastore.prepare(generateQuery("RentalNetwork", "rentalNetworkCode", FilterOperator.EQUAL, 
				model.getRentalNetworkCode()));
		
		if (pq.asSingleEntity() != null) {
			throw new ModelAlreadyExistsException("Rental Network - "+model.getName()+" - already exists");
		}	
		
		Entity entity = new Entity("RentalNetwork");
			if("".equals(model.getRentalNetworkCode())){
			save(entity);	
			model.setRentalNetworkCode(""+entity.getKey().getId());
		}

		entity.setProperty("name", model.getName());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		
		save(entity);
		
		return model;
	}

	@Override
	public void update(RentalNetwork model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		PreparedQuery pq = datastore.prepare(generateQuery("RentalNetwork", "rentalNetworkCode", FilterOperator.EQUAL, 
				model.getRentalNetworkCode()));
		if (pq.asSingleEntity() == null) {
			throw new ModelNotExistsException("Rental Network ("+model.getRentalNetworkCode()+") does not exist");
		}

		Entity entity = pq.asSingleEntity();
		
		entity.setProperty("name", model.getName());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());;
		
		save(entity);
	}

	@Override
	public void delete(RentalNetwork model) throws PersistenceException {
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.delete(KeyFactory.createKey("RetalNetwork", model.getRentalNetworkCode()));
		} catch (DatastoreFailureException e) {
			throw new PersistenceException("Error during model deletion: " + e.getMessage());
		}
		
	}

	@Override
	public List<RentalNetwork> getAll(String rentalNetworkCode) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}


}
