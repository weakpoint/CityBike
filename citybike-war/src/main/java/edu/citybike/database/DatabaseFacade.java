package edu.citybike.database;

import java.util.List;

import javax.persistence.EntityTransaction;

import com.google.appengine.api.datastore.Key;

import edu.citybike.exceptions.ModelNotExistsException;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.BankAccount;
import edu.citybike.model.Bike;
import edu.citybike.model.Credentials;
import edu.citybike.model.Fee;
import edu.citybike.model.Rent;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.User;

public interface DatabaseFacade {
	EntityTransaction getTransaction();
	User getUserByLogin(String login) throws PersistenceException;
	User getUserByKey(Key key);
	Bike getBikeByKey(Key key);
	void add(Object model) throws PersistenceException;
	void update(Object model) throws PersistenceException;
	Credentials getCredentials(String username) throws PersistenceException;
	List<RentalOffice> getRentalOfficeList();
	List<Fee> getFeeList() throws PersistenceException;
	void remove(Object model) throws PersistenceException;
	List<Rent> getUserRentListDesc(Key userKey) throws PersistenceException;
	List<Bike> getBikeList() throws PersistenceException;
	RentalOffice getRentalOfficeByKey(Key key);
	BankAccount getUserBankAccount(Key userKey) throws ModelNotExistsException;
	Rent getUserActiveRental(Key userKey) throws ModelNotExistsException;
	List<Rent> getRentList() throws PersistenceException;
	List<User> getUserList();
	

}
