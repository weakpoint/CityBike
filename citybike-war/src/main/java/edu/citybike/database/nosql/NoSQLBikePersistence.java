package edu.citybike.database.nosql;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterOperator;

import edu.citybike.database.exception.ModelAlreadyExistsException;
import edu.citybike.database.exception.ModelNotExistsException;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Bike;

public class NoSQLBikePersistence extends NoSQLModelPersistence<Bike> {

	public void save(Bike model) throws PersistenceException {
Entity entity = new Entity("Bike");
		
		EmbeddedEntity technicalDetails = new EmbeddedEntity();
		technicalDetails.setProperty("name", model.getTechnicalDetails().getName());
		
		entity.setProperty("technicalDetails", technicalDetails);
		entity.setProperty("status", model.getStatus());
		entity.setProperty("rentalCount", model.getRentalCount());
		entity.setProperty("lastServiceDate", model.getLastServiceDate());
		entity.setProperty("bikeCode", model.getBikeCode());
		entity.setProperty("rentalOfficeCode", model.getRentalOfficeCode());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(generateQuery("Bike", "bikeCode", FilterOperator.EQUAL,
				model.getBikeCode()));

		if (pq.asSingleEntity() != null) {
			throw new ModelAlreadyExistsException("Model already exists");
		}
		save(entity);
	}

	public void update(Bike model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		PreparedQuery pq = datastore.prepare(generateQuery("Bike", "bikeCode", FilterOperator.EQUAL,model.getBikeCode()));
		if (pq.asSingleEntity() == null) {
			throw new ModelNotExistsException("Model does not exist");
		}

		Entity entity = pq.asSingleEntity();
		
		EmbeddedEntity technicalDetails = new EmbeddedEntity();
		technicalDetails.setProperty("name", model.getTechnicalDetails().getName());
		
		entity.setProperty("technicalDetails", technicalDetails);
		entity.setProperty("status", model.getStatus());
		entity.setProperty("rentalCount", model.getRentalCount());
		entity.setProperty("lastServiceDate", model.getLastServiceDate());
		entity.setProperty("bikeCode", model.getBikeCode());
		entity.setProperty("rentalOfficeCode", model.getRentalOfficeCode());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		
		save(entity);
	}

	public void delete(Bike model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(generateQuery("Bike", "bikeCode", FilterOperator.EQUAL,
				model.getBikeCode()));
		Entity entity;

		
			if ((entity = pq.asSingleEntity()) == null) {
				throw new ModelNotExistsException("Model does not exist");
			}

			datastore.delete(entity.getKey());
	}

	public List<Bike> getAll(String rentalNetworkCode)
			throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}


}
