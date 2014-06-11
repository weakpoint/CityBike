package edu.citybike.database.nosql;

import java.util.ArrayList;
import java.util.Collections;
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
import com.google.appengine.api.datastore.Text;

import edu.citybike.database.exception.ModelAlreadyExistsException;
import edu.citybike.database.exception.ModelNotExistsException;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Address;
import edu.citybike.model.User;

public class NoSQLUserPersistence extends NoSQLModelPersistence<User> {

	public User save(User model) throws PersistenceException {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter modelFilter = new FilterPredicate("userCode", FilterOperator.EQUAL, model.getUserCode());
		Filter rentalNetworkFilter = new FilterPredicate("rentalNetworkCode", FilterOperator.EQUAL, model.getRentalNetworkCode());
		
		PreparedQuery pq = datastore.prepare(new Query("User").setFilter(CompositeFilterOperator.and(rentalNetworkFilter, modelFilter)));

		if (pq.asSingleEntity() != null) {
			throw new ModelAlreadyExistsException("User - "+model.getUserCode()+" - already exists");
		}
		
		Entity entity = new Entity("User");
		if("".equals(model.getUserCode())){
			save(entity);
			model.setUserCode(""+entity.getKey().getId());
		}
System.out.println("Save : "+model);
		EmbeddedEntity address = new EmbeddedEntity();
		address.setProperty("city", model.getAddress().getCity());
		address.setProperty("street", model.getAddress().getStreet());
		address.setProperty("houseNumber", model.getAddress().getHouseNumber());
		address.setProperty("flatNumber", model.getAddress().getFlatNumber());
		address.setProperty("postalCode", model.getAddress().getPostalCode());

		entity.setProperty("address", address);
		entity.setProperty("name", model.getName());
		entity.setProperty("lastName", model.getLastName());
		entity.setProperty("phoneNumber", model.getPhoneNumber());
		entity.setProperty("emailAddress", model.getEmailAddress());
		entity.setProperty("notes", model.getNotes());
		entity.setProperty("role", model.getRole());
		entity.setProperty("overallRentalTime", model.getOverallRentalTime());
		entity.setProperty("overallRentalCost", model.getOverallRentalCost());
		entity.setProperty("userCode", model.getUserCode());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());

		save(entity);
		return model;
	}

	public void update(User model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter modelFilter = new FilterPredicate("userCode", FilterOperator.EQUAL, model.getUserCode());
		Filter rentalNetworkFilter = new FilterPredicate("rentalNetworkCode", FilterOperator.EQUAL, model.getRentalNetworkCode());
		System.out.println(model);
		PreparedQuery pq = datastore.prepare(new Query("User").setFilter(CompositeFilterOperator.and(rentalNetworkFilter, modelFilter)));
		try {
			if (pq.asSingleEntity() == null) {
				throw new ModelNotExistsException("User "+model.getName()+" "+model.getLastName()+" does not exist");
			}

			Entity entity = pq.asSingleEntity();
			EmbeddedEntity address = new EmbeddedEntity();
			address.setProperty("city", model.getAddress().getCity());
			address.setProperty("street", model.getAddress().getStreet());
			address.setProperty("houseNumber", model.getAddress().getHouseNumber());
			address.setProperty("flatNumber", model.getAddress().getFlatNumber());
			address.setProperty("postalCode", model.getAddress().getPostalCode());

			entity.setProperty("address", address);
			entity.setProperty("name", model.getName());
			entity.setProperty("lastName", model.getLastName());
			entity.setProperty("phoneNumber", model.getPhoneNumber());
			entity.setProperty("emailAddress", model.getEmailAddress());
			entity.setProperty("notes", model.getNotes());
			entity.setProperty("role", model.getRole());
			entity.setProperty("overallRentalTime", model.getOverallRentalTime());
			entity.setProperty("overallRentalCost", model.getOverallRentalCost());
			entity.setProperty("userCode", model.getUserCode());
			entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());

			save(entity);
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	public void delete(User model) throws PersistenceException {
		try{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.delete(KeyFactory.createKey("User", model.getUserCode()));
		}catch(DatastoreFailureException e){
			throw new PersistenceException("Error during model deletion: "+e.getMessage());
		}
	}

	public List<User> getAll(String rentalNetworkCode)
			throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(generateQuery("User", "rentalNetworkCode", FilterOperator.EQUAL,
				rentalNetworkCode));
		List<User> users = new ArrayList<User>();

		for (Entity en : pq.asIterable()) {
			User user = new User();

			EmbeddedEntity emb = (EmbeddedEntity) en.getProperty("address");
			Address address = new Address();

			address.setCity((String) emb.getProperty("city"));
			address.setStreet((String) emb.getProperty("street"));
			address.setHouseNumber((String) emb.getProperty("houseNumber"));
			address.setFlatNumber((String) emb.getProperty("flatNumber"));
			address.setPostalCode((String) emb.getProperty("postalCode"));

			user.setAddress(address);
			user.setName((String) en.getProperty("name"));
			user.setLastName((String) en.getProperty("lastName"));
			user.setPhoneNumber((String) en.getProperty("phoneNumber"));
			user.setEmailAddress((String) en.getProperty("emailAddress"));
			user.setNotes((Text) en.getProperty("notes"));
			user.setRole((String) en.getProperty("role"));
			user.setOverallRentalTime((Long) en.getProperty("overallRentalTime"));
			user.setOverallRentalCost((Double) en.getProperty("overallRentalCost"));
			user.setUserCode((String) en.getProperty("userCode"));
			user.setRentalNetworkCode((String) en.getProperty("rentalNetworkCode"));

			users.add(user);
		}
		return Collections.unmodifiableList(users);
	}

}
