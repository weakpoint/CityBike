package edu.citybike.database;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.datastore.Transaction;

import edu.citybike.database.exception.ModelNotExistsException;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.database.exception.UnsupportedModelType;
import edu.citybike.model.Bike;
import edu.citybike.model.Credentials;
import edu.citybike.model.Fee;
import edu.citybike.model.Rent;
import edu.citybike.model.RentalNetwork;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.User;

public class DatabaseFacade {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseFacade.class);
	private DAOPersistenceFactory daoPersistenceFactory;

	public void setDaoPersistenceFactory(DAOPersistenceFactory daoPersistenceFactory) {
		this.daoPersistenceFactory = daoPersistenceFactory;
	}
	
	public Transaction getTransaction(){
		return daoPersistenceFactory.getUserPersistence().getTransaction();
	}


	public User getUser(String rentalNetworkCode, String userCode) throws PersistenceException {
		List<User> users = daoPersistenceFactory.getUserPersistence().getAll(rentalNetworkCode);

		for (User user : users) {
			if (user.getUserCode().equals(userCode)) {
				return user;
			}
		}
		throw new ModelNotExistsException("User does not exist");
	}

	public Object add(Object model) throws PersistenceException {
		try {
			if (model instanceof User) {
				return daoPersistenceFactory.getUserPersistence().save((User) model);
			}
			/*
			 * else if (model instanceof Employee) {
			 * daoPersistenceFactory.getWorkerPersistence().save((Employee)
			 * model); }
			 */else if (model instanceof RentalOffice) {
				 return daoPersistenceFactory.getRentalOfficePersistence().save((RentalOffice) model);
			} else if (model instanceof RentalNetwork) {
				return daoPersistenceFactory.getRentalNetworkPersistence().save((RentalNetwork) model);
			} else if (model instanceof Bike) {
				return daoPersistenceFactory.getBikePersistence().save((Bike) model);
			} else if (model instanceof Fee) {
				return daoPersistenceFactory.getFeePersistence().save((Fee) model);
			} else if (model instanceof Rent) {
				return daoPersistenceFactory.getRentPersistence().save((Rent) model);
			} else if (model instanceof Credentials) {
				return daoPersistenceFactory.getCredentialsPersistence().save((Credentials) model);
			} else {
				throw new UnsupportedModelType("This model type is unsupported");
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

	}

	public void update(Object model) throws PersistenceException {
		try {
			if (model instanceof User) {
				daoPersistenceFactory.getUserPersistence().update((User) model);
			}
			/*
			 * else if (model instanceof Employee) {
			 * daoPersistenceFactory.getWorkerPersistence().update((Employee)
			 * model); }
			 */else if (model instanceof RentalOffice) {
				daoPersistenceFactory.getRentalOfficePersistence().update((RentalOffice) model);
			} else if (model instanceof RentalNetwork) {
				daoPersistenceFactory.getRentalNetworkPersistence().update((RentalNetwork) model);
			} else if (model instanceof Bike) {
				daoPersistenceFactory.getBikePersistence().update((Bike) model);
			} else if (model instanceof Fee) {
				daoPersistenceFactory.getFeePersistence().update((Fee) model);
			} else if (model instanceof Rent) {
				daoPersistenceFactory.getRentPersistence().update((Rent) model);
			} else if (model instanceof Credentials) {
				daoPersistenceFactory.getCredentialsPersistence().update((Credentials) model);
			} else {
				throw new UnsupportedModelType("This model type is unsupported");
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

	}

	public List<RentalOffice> getRentalOfficeList(String rentalNetworkCode) throws PersistenceException {
		return daoPersistenceFactory.getRentalOfficePersistence().getAll(rentalNetworkCode);
	}

	public List<Rent> getUserRentList(String userCode, String rentalNetworkCode) throws PersistenceException {
		ArrayList<Rent> userList = new ArrayList<Rent>();
		List<Rent> allRent = daoPersistenceFactory.getRentPersistence().getAll(rentalNetworkCode);

		for (Rent rent : allRent) {
			if (userCode.equals(rent.getUserCode())) {
				userList.add(rent);
			}
		}
		return userList;
	}

	public List<Fee> getFeeList(String rentalNetworkCode) throws PersistenceException {
		return daoPersistenceFactory.getFeePersistence().getAll(rentalNetworkCode);
		
	}

	public Bike getBike(String rentalNetworkCode, String bikeCode) throws PersistenceException {
		List<Bike> bikes = daoPersistenceFactory.getBikePersistence().getAll(rentalNetworkCode);
		for (Bike bike : bikes) {
			if (bike.getBikeCode().equals(bikeCode)) {
				return bike;
			}
		}
		throw new ModelNotExistsException("Bike does not exist");
		
	}
	
	public Credentials getCredentials(String rentalNetworkCode, String userEmail) throws PersistenceException{
		List<Credentials> all = daoPersistenceFactory.getCredentialsPersistence().getAll(rentalNetworkCode);
		for(Credentials c : all){
			if(c.getEmailAddress().equals(userEmail)){
				return c;
			}
		}
		throw new ModelNotExistsException("Wrong email");
	}
	
	public User getUser(Credentials credentials) throws PersistenceException{
		List<User> all = daoPersistenceFactory.getUserPersistence().getAll(credentials.getRentalNetworkCode());
		
		for(User u : all){
			if(u.getEmailAddress().equals(credentials.getEmailAddress())){
				return u;
			}
		}
		throw new ModelNotExistsException("Wrong email");
	}

}
