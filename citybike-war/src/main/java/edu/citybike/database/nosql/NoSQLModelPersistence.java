package edu.citybike.database.nosql;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;

import edu.citybike.database.ModelPersistence;
import edu.citybike.database.exception.PersistenceException;

public abstract class NoSQLModelPersistence<T> implements ModelPersistence<T> {

	protected void save(Entity entity) throws PersistenceException {
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Transaction transaction = datastore.beginTransaction();
			datastore.put(transaction, entity);
			transaction.commit();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	protected Query generateQuery(String entityKind, String propertyName, FilterOperator filterOperator, Object value) {
		return new Query(entityKind).setFilter(new FilterPredicate(propertyName, filterOperator, value));
		
	}

}
