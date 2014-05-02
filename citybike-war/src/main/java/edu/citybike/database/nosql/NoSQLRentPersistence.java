package edu.citybike.database.nosql;

import java.util.ArrayList;
import java.util.Date;
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
		

		entity.setProperty("rentDuration", model.getRentDuration());
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
		Rent r = new Rent();
		r.setUserCode("00");
		r.setActive(true);
		r.setBikeCode("00");
		r.setStartDate(new Date(System.currentTimeMillis()-60*45000));
		
		Rent r2 = new Rent();
		r2.setUserCode("00");
		r2.setActive(true);
		r2.setBikeCode("01");
		r2.setStartDate(new Date(System.currentTimeMillis()-60*30000));
		
		Rent r3 = new Rent();
		r3.setUserCode("00");
		r3.setActive(false);
		r3.setBikeCode("02");
		r3.setStartDate(new Date(System.currentTimeMillis()-60*60000));
		
		List<Rent> list = new ArrayList<Rent>();
		list.add(r);
		list.add(r2);
		list.add(r3);
		return list;
	}



}
