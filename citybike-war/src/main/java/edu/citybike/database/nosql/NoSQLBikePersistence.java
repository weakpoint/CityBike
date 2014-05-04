package edu.citybike.database.nosql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterOperator;

import edu.citybike.database.exception.ModelAlreadyExistsException;
import edu.citybike.database.exception.ModelNotExistsException;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Bike;
import edu.citybike.model.TechnicalDetails;

public class NoSQLBikePersistence extends NoSQLModelPersistence<Bike> {

	public Bike save(Bike model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(generateQuery("Bike", "bikeCode", FilterOperator.EQUAL,
				model.getBikeCode()));

		if (pq.asSingleEntity() != null) {
			throw new ModelAlreadyExistsException("Model already exists");
		}	
		
		Entity entity = new Entity("Bike");
		save(entity);	
		model.setBikeCode(""+entity.getKey().getId());
		
		EmbeddedEntity technicalDetails = new EmbeddedEntity();
		technicalDetails.setProperty("name", model.getTechnicalDetails().getName());
		
		entity.setProperty("technicalDetails", technicalDetails);
		entity.setProperty("status", model.getStatusString());
		entity.setProperty("rentalCount", model.getRentalCount());
		entity.setProperty("lastServiceDate", model.getLastServiceDate());
		entity.setProperty("bikeCode", model.getBikeCode());
		entity.setProperty("rentalOfficeCode", model.getRentalOfficeCode());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		
		save(entity);
		
		return model;
	}

	public void update(Bike model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		PreparedQuery pq = datastore.prepare(generateQuery("Bike", "bikeCode", FilterOperator.EQUAL,model.getBikeCode()));
		if (pq.asSingleEntity() == null) {
			throw new ModelNotExistsException("Bike ("+model.getBikeCode()+") does not exist");
		}

		Entity entity = pq.asSingleEntity();
		
		EmbeddedEntity technicalDetails = new EmbeddedEntity();
		technicalDetails.setProperty("name", model.getTechnicalDetails().getName());
		
		entity.setProperty("technicalDetails", technicalDetails);
		entity.setProperty("status", model.getStatusString());
		entity.setProperty("rentalCount", model.getRentalCount());
		entity.setProperty("lastServiceDate", model.getLastServiceDate());
		entity.setProperty("bikeCode", model.getBikeCode());
		entity.setProperty("rentalOfficeCode", model.getRentalOfficeCode());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		
		save(entity);
	}

	public void delete(Bike model) throws PersistenceException {
		try{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.delete(KeyFactory.createKey("Bike", model.getBikeCode()));
		}catch(DatastoreFailureException e){
			throw new PersistenceException("Error during model deletion: "+e.getMessage());
		}
	}

	public List<Bike> getAll(String rentalNetworkCode)
			throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(generateQuery("Bike", "rentalNetworkCode", FilterOperator.EQUAL, 
				rentalNetworkCode));
		
		List<Bike> bikes = new ArrayList<Bike>();
		for (Entity en : pq.asIterable()) {
			Bike bike = new Bike();
			System.out.println("-*-*-*-**-*-*-*-*-*-*-*");
			TechnicalDetails technicalDetails = new TechnicalDetails();
			EmbeddedEntity emb = (EmbeddedEntity) en.getProperty("technicalDetails");
			
			technicalDetails.setName((String) emb.getProperty("name"));

			bike.setTechnicalDetails(technicalDetails);
			bike.setStatus((String) en.getProperty("status"));
			bike.setRentalCount((Long) en.getProperty("rentalCount"));
			bike.setLastServiceDate((Date) en.getProperty("lastServiceDate"));
			bike.setBikeCode((String) en.getProperty("bikeCode"));
			bike.setRentalOfficeCode((String) en.getProperty("rentalOfficeCode"));
			bike.setRentalNetworkCode((String) en.getProperty("rentalNetworkCode"));
			
			bikes.add(bike);
		}
		System.out.println("getall: "+bikes.size());
		return Collections.unmodifiableList(bikes);
	}


}
