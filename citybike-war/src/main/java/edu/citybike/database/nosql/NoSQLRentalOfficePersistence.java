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
import edu.citybike.model.Address;
import edu.citybike.model.Bike;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.TechnicalDetails;

public class NoSQLRentalOfficePersistence extends NoSQLModelPersistence<RentalOffice> {

	public RentalOffice save(RentalOffice model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter modelFilter = new FilterPredicate("rentalOfficeCode", FilterOperator.EQUAL, model.getRentalOfficeCode());
		Filter rentalNetworkFilter = new FilterPredicate("rentalNetworkCode", FilterOperator.EQUAL, model.getRentalNetworkCode());
		
		PreparedQuery pq = datastore.prepare(new Query("RentalOffice").setFilter(CompositeFilterOperator.and(rentalNetworkFilter, modelFilter)));
		
		if (pq.asSingleEntity() != null) {
			throw new ModelAlreadyExistsException("Rental Office - "+model.getRentalOfficeCode()+ "- already exists");
		}	
		
		Entity entity = new Entity("RentalOffice");
		if ("".equals(model.getRentalOfficeCode())) {
			save(entity);
			model.setRentalOfficeCode("" + entity.getKey().getId());
		}

		EmbeddedEntity address = new EmbeddedEntity();
		address.setProperty("city", model.getAddress().getCity());
		address.setProperty("street", model.getAddress().getStreet());
		address.setProperty("houseNumber", model.getAddress().getHouseNumber());
		address.setProperty("flatNumber", model.getAddress().getFlatNumber());
		address.setProperty("postalCode", model.getAddress().getPostalCode());

		entity.setProperty("address", address);

		entity.setProperty("longitude", model.getLongitude());
		entity.setProperty("latitude", model.getLatitude());
		entity.setProperty("rentalOfficeCode", model.getRentalOfficeCode());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		
		save(entity);
		
		return model;
	}

	public void update(RentalOffice model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter modelFilter = new FilterPredicate("rentalOfficeCode", FilterOperator.EQUAL, model.getRentalOfficeCode());
		Filter rentalNetworkFilter = new FilterPredicate("rentalNetworkCode", FilterOperator.EQUAL, model.getRentalNetworkCode());
		
		PreparedQuery pq = datastore.prepare(new Query("RentalOffice").setFilter(CompositeFilterOperator.and(rentalNetworkFilter, modelFilter)));
		
		if (pq.asSingleEntity() == null) {
			throw new ModelNotExistsException("Rental Office ("+model.getRentalOfficeCode()+") does not exist");
		}

		Entity entity = pq.asSingleEntity();
		
		EmbeddedEntity address = new EmbeddedEntity();
		address.setProperty("city", model.getAddress().getCity());
		address.setProperty("street", model.getAddress().getStreet());
		address.setProperty("houseNumber", model.getAddress().getHouseNumber());
		address.setProperty("flatNumber", model.getAddress().getFlatNumber());
		address.setProperty("postalCode", model.getAddress().getPostalCode());

		entity.setProperty("address", address);

		entity.setProperty("longitude", model.getLongitude());
		entity.setProperty("latitude", model.getLatitude());
		entity.setProperty("rentalOfficeCode", model.getRentalOfficeCode());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());
		
		save(entity);
		
	}

	public void delete(RentalOffice model) throws PersistenceException {
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.delete(KeyFactory.createKey("RentalOffice", model.getRentalOfficeCode()));
		} catch (DatastoreFailureException e) {
			throw new PersistenceException("Error during model deletion: " + e.getMessage());
		}
		
	}

	public List<RentalOffice> getAll(String rentalNetworkCode)
			throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(generateQuery("RentalOffice", "rentalNetworkCode", FilterOperator.EQUAL, 
				rentalNetworkCode));
		
		List<RentalOffice> list = new ArrayList<RentalOffice>();
		for (Entity en : pq.asIterable()) {
			RentalOffice rentalOffice = new RentalOffice();

			EmbeddedEntity emb = (EmbeddedEntity) en.getProperty("address");
			Address address = new Address();

			address.setCity((String) emb.getProperty("city"));
			address.setStreet((String) emb.getProperty("street"));
			address.setHouseNumber((String) emb.getProperty("houseNumber"));
			address.setFlatNumber((String) emb.getProperty("flatNumber"));
			address.setPostalCode((String) emb.getProperty("postalCode"));
			
			rentalOffice.setAddress(address);
			rentalOffice.setLongitude((String) en.getProperty("longitude"));
			rentalOffice.setLatitude((String) en.getProperty("latitude"));
			rentalOffice.setRentalOfficeCode((String) en.getProperty("rentalOfficeCode"));
			rentalOffice.setRentalNetworkCode((String) en.getProperty("rentalNetworkCode"));
			
			list.add(rentalOffice);
		}

		return Collections.unmodifiableList(list);
	}



}
