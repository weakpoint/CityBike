package edu.citybike.charts.datafactories;

import java.util.List;

import com.google.appengine.api.datastore.Key;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.Rent;
import edu.citybike.model.User;

public class RentDataFactory implements DataFactory<Rent> {

	private DatabaseFacade facade;

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}

	@Override
	public List<Rent> getData(User user) throws PersistenceException {
		if (user.getRole().equals(User.ADMINISTRATOR)) {
			return facade.getRentList();
		} else {
			return facade.getUserRentListDesc(user.getKey());
		}
	}

	@Override
	public List<Rent> getData(Key userKey) throws PersistenceException {
		User user = facade.getUserByKey(userKey);
		return getData(user);
	}

}
