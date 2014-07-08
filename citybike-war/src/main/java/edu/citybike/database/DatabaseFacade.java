package edu.citybike.database;

import javax.persistence.EntityTransaction;

import com.google.appengine.api.datastore.Key;

import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Bike;
import edu.citybike.model.Credentials;
import edu.citybike.model.User;

public interface DatabaseFacade {
	EntityTransaction getTransaction();
	User getUserByLogin(String login) throws PersistenceException;
	User getUserByKey(Key key);
	Bike getBikeByKey(Key key);
	void add(Object model) throws PersistenceException;
	void update(Object model) throws PersistenceException;
	public Credentials getCredentials(String username) throws PersistenceException;
	

}
