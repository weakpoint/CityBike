package edu.citybike.database.nosql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import edu.citybike.database.exception.ModelAlreadyExistsException;
import edu.citybike.database.exception.ModelNotExistsException;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Credentials;

public class NoSQLCredentialsPersistence extends NoSQLModelPersistence<Credentials> {

	@Override
	public Credentials save(Credentials model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter emailAdressFilter = new FilterPredicate("emailAddress", FilterOperator.EQUAL, model.getEmailAddress());
		Filter rentalNetworkFilter = new FilterPredicate("rentalNetworkCode", FilterOperator.EQUAL, model.getRentalNetworkCode());
		
		PreparedQuery pq = datastore.prepare(new Query("Credentials").setFilter(CompositeFilterOperator.and(rentalNetworkFilter, emailAdressFilter)));

		if (pq.asSingleEntity() != null) {
			throw new ModelAlreadyExistsException("Credentials - "+model.getEmailAddress()+" - already exists");
		}	
		
		Entity entity = new Entity("Credentials");

		entity.setProperty("emailAddress", model.getEmailAddress());
		entity.setProperty("password", model.getPassword());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		
		save(entity);
		
		return model;
	}

	@Override
	public void update(Credentials model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter emailAdressFilter = new FilterPredicate("emailAddress", FilterOperator.EQUAL, model.getEmailAddress());
		Filter rentalNetworkFilter = new FilterPredicate("rentalNetworkCode", FilterOperator.EQUAL, model.getRentalNetworkCode());
		
		PreparedQuery pq = datastore.prepare(new Query("Credentials").setFilter(CompositeFilterOperator.and(rentalNetworkFilter, emailAdressFilter)));
		
		if (pq.asSingleEntity() == null) {
			throw new ModelNotExistsException("Credentials for - "+model.getEmailAddress()+" - do not exist");
		}

		Entity entity = pq.asSingleEntity();
		
		entity.setProperty("emailAddress", model.getEmailAddress());
		entity.setProperty("password", model.getPassword());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		
		save(entity);
	}

	@Override
	public void delete(Credentials model) throws PersistenceException {
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Filter emailAdressFilter = new FilterPredicate("emailAddress", FilterOperator.EQUAL, model.getEmailAddress());
			Filter rentalNetworkFilter = new FilterPredicate("rentalNetworkCode", FilterOperator.EQUAL, model.getRentalNetworkCode());
			
			PreparedQuery pq = datastore.prepare(new Query("Credentials").setFilter(CompositeFilterOperator.and(rentalNetworkFilter, emailAdressFilter)));
			
			if (pq.asSingleEntity() == null) {
				throw new ModelNotExistsException("Credentials for - "+model.getEmailAddress()+" - do not exist");
			}
			datastore.delete(pq.asSingleEntity().getKey());
		} catch (DatastoreFailureException e) {
			throw new PersistenceException("Error during model deletion: " + e.getMessage());
		}
		
	}

	@Override
	public List<Credentials> getAll(String rentalNetworkCode) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(generateQuery("Credentials", "rentalNetworkCode", FilterOperator.EQUAL, 
				rentalNetworkCode));
		
		List<Credentials> list = new ArrayList<Credentials>();
		for (Entity en : pq.asIterable()) {
			Credentials credentials = new Credentials();
			
			credentials.setEmailAddress((String) en.getProperty("emailAddress"));
			credentials.setPassword((String) en.getProperty("password"));
			credentials.setRentalNetworkCode((String) en.getProperty("rentalNetworkCode"));
			System.out.println(credentials);
			list.add(credentials);
		}

		return Collections.unmodifiableList(list);
	}

}
