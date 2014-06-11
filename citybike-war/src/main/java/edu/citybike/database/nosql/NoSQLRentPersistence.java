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
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import edu.citybike.database.exception.ModelAlreadyExistsException;
import edu.citybike.database.exception.ModelNotExistsException;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Bike;
import edu.citybike.model.Rent;
import edu.citybike.model.TechnicalDetails;

public class NoSQLRentPersistence extends NoSQLModelPersistence<Rent> {

	public Rent save(Rent model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter modelFilter = new FilterPredicate("rentCode", FilterOperator.EQUAL, model.getBikeCode());
		Filter rentalNetworkFilter = new FilterPredicate("rentalNetworkCode", FilterOperator.EQUAL, model.getRentalNetworkCode());
		
		PreparedQuery pq = datastore.prepare(new Query("Rent").setFilter(CompositeFilterOperator.and(rentalNetworkFilter, modelFilter)));
		

		if (pq.asSingleEntity() != null) {
			throw new ModelAlreadyExistsException("Rent ("+model.getBikeCode()+") already exists");
		}	
		
		Entity entity = new Entity("Rent");
			if("".equals(model.getBikeCode())){
			save(entity);	
			model.setBikeCode(""+entity.getKey().getId());
		}

		entity.setProperty("rentDuration", model.getRentDuration());
		entity.setProperty("startDate", model.getStartDate());
		entity.setProperty("endDate", model.getEndDate());
		entity.setProperty("active", model.isActive());
		entity.setProperty("rentCost", model.getRentCost());
		entity.setProperty("userCode", model.getUserCode());
		entity.setProperty("bikeCode", model.getBikeCode());
		entity.setProperty("rentCode", model.getRentCode());
		entity.setProperty("rentalOfficeCode", model.getRentalOfficeCode());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		
		save(entity);
		
		return model;
	}

	public void update(Rent model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter modelFilter = new FilterPredicate("rentCode", FilterOperator.EQUAL, model.getBikeCode());
		Filter rentalNetworkFilter = new FilterPredicate("rentalNetworkCode", FilterOperator.EQUAL, model.getRentalNetworkCode());
		
		PreparedQuery pq = datastore.prepare(new Query("Rent").setFilter(CompositeFilterOperator.and(rentalNetworkFilter, modelFilter)));
		if (pq.asSingleEntity() == null) {
			throw new ModelNotExistsException("Rent ("+model.getRentCode()+") does not exist");
		}

		Entity entity = pq.asSingleEntity();
		
		entity.setProperty("rentDuration", model.getRentDuration());
		entity.setProperty("startDate", model.getStartDate());
		entity.setProperty("endDate", model.getEndDate());
		entity.setProperty("active", model.isActive());
		entity.setProperty("rentCost", model.getRentCost());
		entity.setProperty("userCode", model.getUserCode());
		entity.setProperty("bikeCode", model.getBikeCode());
		entity.setProperty("rentCode", model.getRentCode());
		entity.setProperty("rentalOfficeCode", model.getRentalOfficeCode());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		
		save(entity);
		
	}

	public void delete(Rent model) throws PersistenceException {
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.delete(KeyFactory.createKey("Rent", model.getBikeCode()));
		} catch (DatastoreFailureException e) {
			throw new PersistenceException("Error during model deletion: " + e.getMessage());
		}		
	}

	public List<Rent> getAll(String rentalNetworkCode)
			throws PersistenceException {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(generateQuery("Rent", "rentalNetworkCode", FilterOperator.EQUAL, 
				rentalNetworkCode));
		
		List<Rent> list = new ArrayList<Rent>();
		for (Entity en : pq.asIterable()) {
			Rent rent = new Rent();

			//rent.setRentDuration((Integer) en.getProperty("rentDuration"));
			rent.setStartDate((Date)en.getProperty("startDate"));
			rent.setEndDate((Date) en.getProperty("endDate"));
			rent.setActive((boolean) en.getProperty("active"));
			rent.setRentCost((double) en.getProperty("rentCost"));
			rent.setUserCode((String) en.getProperty("userCode"));
			rent.setBikeCode((String) en.getProperty("bikeCode"));
			rent.setRentCode((String) en.getProperty("rentCode"));
			rent.setRentalOfficeCode((String) en.getProperty("rentalOfficeCode"));
			rent.setRentalNetworkCode((String) en.getProperty("rentalNetworkCode"));
			
			list.add(rent);
		}

		return Collections.unmodifiableList(list);
	}



}
