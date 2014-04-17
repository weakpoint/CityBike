package edu.citybike.database.nosql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Transaction;

import edu.citybike.database.exception.ModelAlreadyExistsException;
import edu.citybike.database.exception.ModelNotExistsException;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Address;
import edu.citybike.model.User;

public class NoSQLUserPersistence extends NoSQLModelPersistence<User> {

	public void save(User model) throws PersistenceException {
		Entity entity = new Entity("User");

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
		entity.setProperty("notes", model.getNotes());
		entity.setProperty("overallRentalTime", model.getOverallRentalTime());
		entity.setProperty("overallRentalCost", model.getOverallRentalCost());
		entity.setProperty("userCode", model.getUserCode());
		entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(generateQuery("User", "userCode", FilterOperator.EQUAL,
				model.getUserCode()));

		if (pq.asSingleEntity() != null) {
			throw new ModelAlreadyExistsException("Model already exists");
		}
		save(entity);	
	}

	public void update(User model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		PreparedQuery pq = datastore.prepare(generateQuery("User", "userCode", FilterOperator.EQUAL,model.getUserCode()));
		try {
			if (pq.asSingleEntity() == null) {
				throw new ModelNotExistsException("Model does not exist");
			}

			Entity entity = pq.asSingleEntity();//new Entity("User");
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
			entity.setProperty("notes", model.getNotes());
			entity.setProperty("overallRentalTime", model.getOverallRentalTime());
			entity.setProperty("overallRentalCost", model.getOverallRentalCost());
			entity.setProperty("userCode", model.getUserCode());
			entity.setProperty("rentalNetworkCode", model.getRentalNetworkCode());

			save(entity);
			System.out.println(entity.getAppId()+" "+entity.getKey());
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	public void delete(User model) throws PersistenceException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		PreparedQuery pq = datastore.prepare(generateQuery("User", "userCode", FilterOperator.EQUAL,
				model.getUserCode()));
		Entity entity;

		try {
			if ((entity = pq.asSingleEntity()) == null) {
				throw new ModelNotExistsException("Model does not exist");
			}

			datastore.delete(entity.getKey());
		} catch (Exception e) {
			throw new PersistenceException(e);
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
			user.setPhoneNumber((PhoneNumber) en.getProperty("phoneNumber"));
			user.setNotes((Text) en.getProperty("notes"));
			user.setOverallRentalTime((Long) en.getProperty("overallRentalTime"));
			user.setOverallRentalCost((Long) en.getProperty("overallRentalCost"));
			user.setUserCode((String) en.getProperty("userCode"));
			user.setRentalNetworkCode((String) en.getProperty("rentalNetworkCode"));

			users.add(user);
		}
		return Collections.unmodifiableList(users);
	}

}
