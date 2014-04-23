package edu.citybike.database;

import java.util.List;

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
import edu.citybike.model.Employee;

public class DatabaseFacade {

	private DAOPersistenceFactory daoPersistenceFactory;

	public void setDaoPersistenceFactory(DAOPersistenceFactory daoPersistenceFactory) {
		this.daoPersistenceFactory = daoPersistenceFactory;
	}

	public User getUser(String rentalNetworkCode, String userCode) throws PersistenceException {
			List<User> users = daoPersistenceFactory.getUserPersistence().getAll(rentalNetworkCode);
			System.out.println(users.size());

			for (User user : users) {
				if (user.getUserCode().equals(userCode)) {
					return user;
				}
			}
			throw new ModelNotExistsException("User does not exist");
	}

	public void save(Object model) throws PersistenceException {
		try {
			if (model instanceof User) {
				daoPersistenceFactory.getUserPersistence().save((User) model);
			} else if (model instanceof Employee) {
				daoPersistenceFactory.getWorkerPersistence().save((Employee) model);
			} else if (model instanceof RentalOffice) {
				daoPersistenceFactory.getRentalOfficePersistence().save((RentalOffice) model);
			} else if (model instanceof RentalNetwork) {
				daoPersistenceFactory.getRentalNetworkPersistence().save((RentalNetwork) model);
			} else if (model instanceof Bike) {
				daoPersistenceFactory.getBikePersistence().save((Bike) model);
			} else if (model instanceof Fee) {
				daoPersistenceFactory.getFeePersistence().save((Fee) model);
			} else if (model instanceof Rent) {
				daoPersistenceFactory.getRentPersistence().save((Rent) model);
			} else if (model instanceof Credentials) {
				daoPersistenceFactory.getCredentialsPersistence().save((Credentials) model);
			} else {
				throw new UnsupportedModelType("This model type is unsupported");
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

	}
	
	public void update(Object model) throws PersistenceException{
		try {
			if (model instanceof User) {
				daoPersistenceFactory.getUserPersistence().update((User) model);
			} else if (model instanceof Employee) {
				daoPersistenceFactory.getWorkerPersistence().update((Employee) model);
			} else if (model instanceof RentalOffice) {
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
	
	public List<RentalOffice> getRentalOfficeList(String rentalNetworkCode) throws PersistenceException{
		return daoPersistenceFactory.getRentalOfficePersistence().getAll(rentalNetworkCode);
	}

}
