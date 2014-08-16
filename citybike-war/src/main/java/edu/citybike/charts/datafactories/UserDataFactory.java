package edu.citybike.charts.datafactories;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Key;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.User;

public class UserDataFactory implements DataFactory<User> {

	private DatabaseFacade facade;

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}

	@Override
	public List<User> getData(User user) throws PersistenceException {
		if (user.getRole().equals(User.ADMINISTRATOR)) {
			return facade.getUserList();
		} else {
			return new ArrayList<User>();
		}
	}

	@Override
	public List<User> getData(Key userKey) throws PersistenceException {
		User user = facade.getUserByKey(userKey);
		return getData(user);
	}

}
