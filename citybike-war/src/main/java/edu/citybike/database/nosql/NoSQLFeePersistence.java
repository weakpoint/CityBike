package edu.citybike.database.nosql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
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
import edu.citybike.model.Fee;

public class NoSQLFeePersistence extends NoSQLModelPersistence<Fee> {

	@Override
	public Fee save(Fee model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter modelFilter = new FilterPredicate("fee", FilterOperator.EQUAL, model.getFee());
		Filter rentalNetworkFilter = new FilterPredicate("rentalNetworkCode", FilterOperator.EQUAL, model.getRentalNetworkCode());
		
		PreparedQuery pq = datastore.prepare(new Query("Fee").setFilter(CompositeFilterOperator.and(rentalNetworkFilter, modelFilter)));
		
		if (pq.asSingleEntity() != null) {
			throw new ModelAlreadyExistsException("Fee - "+model.getTime()+"/"+model.getFee()+" - already exists");
		}	
		
		Entity entity = new Entity("Fee");
		if ("".equals(model.getFeeCode())) {
			save(entity);
			model.setFeeCode("" + entity.getKey().getId());
		}

		entity.setProperty("time", model.getTime());
		entity.setProperty("fee", model.getFee());
		entity.setProperty("feeCode", model.getFeeCode());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		
		save(entity);
		
		return model;
	}

	@Override
	public void update(Fee model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter modelFilter = new FilterPredicate("feeCode", FilterOperator.EQUAL, model.getFeeCode());
		Filter rentalNetworkFilter = new FilterPredicate("rentalNetworkCode", FilterOperator.EQUAL, model.getRentalNetworkCode());
		
		PreparedQuery pq = datastore.prepare(new Query("Fee").setFilter(CompositeFilterOperator.and(rentalNetworkFilter, modelFilter)));
		if (pq.asSingleEntity() == null) {
			throw new ModelNotExistsException("Fee ("+model.getTime()+"/"+model.getFee()+") does not exist");
		}

		Entity entity = pq.asSingleEntity();
		
		entity.setProperty("time", model.getTime());
		entity.setProperty("fee", model.getFee());
		entity.setProperty("feeCode", model.getFeeCode());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		
		save(entity);
	}

	@Override
	public void delete(Fee model) throws PersistenceException {
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.delete(KeyFactory.createKey("Fee", model.getFeeCode()));
		} catch (DatastoreFailureException e) {
			throw new PersistenceException("Error during model deletion: " + e.getMessage());
		}
		
	}

	@Override
	public List<Fee> getAll(String rentalNetworkCode) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(generateQuery("Fee", "rentalNetworkCode", FilterOperator.EQUAL, 
				rentalNetworkCode));
		
		List<Fee> list = new ArrayList<Fee>();
		for (Entity en : pq.asIterable()) {
			Fee fee = new Fee();

			fee.setFeeCode((String) en.getProperty("feeCode"));
			fee.setTime((int) en.getProperty("time"));
			fee.setFee((double) en.getProperty("fee"));
			fee.setRentalNetworkCode((String) en.getProperty("rentalNetworkCode"));
			
			list.add(fee);
		}

		return Collections.unmodifiableList(list);
	}

}
