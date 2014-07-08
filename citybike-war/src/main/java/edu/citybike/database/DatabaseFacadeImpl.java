package edu.citybike.database;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.datastore.Key;

import edu.citybike.database.exception.ModelNotExistsException;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.database.exception.TooManyResults;
import edu.citybike.model.Bike;
import edu.citybike.model.Credentials;
import edu.citybike.model.Fee;
import edu.citybike.model.Rent;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.User;

public class DatabaseFacadeImpl implements DatabaseFacade{
	private static final Logger logger = LoggerFactory.getLogger(DatabaseFacadeImpl.class);
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	protected EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	public EntityTransaction getTransaction(){
		return entityManager.getTransaction();
	}


	public User getUserByLogin(String login) throws PersistenceException {
		
		 Query userQuery = entityManager.createNativeQuery("select user from User u where u.emailAddress=?1", User.class);
		 userQuery.setParameter(1, login);
		 
		 try{
		  User user = (User) userQuery.getSingleResult();
		  return user;
		 }catch(NoResultException e){
			 throw new ModelNotExistsException("User does not exist");
		 }catch(NonUniqueResultException e){
			 throw new TooManyResults("Too many users");
		 }
	}
	
	public User getUserByKey(Key key){
		return entityManager.find(User.class, key);
	}
	
	public Bike getBikeByKey(Key key){
		return entityManager.find(Bike.class, key);
	}

	public void add(Object model) throws PersistenceException {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(model);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new PersistenceException(e);
		}

	}

	public void update(Object model) throws PersistenceException {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(model);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new PersistenceException(e);
		}

	}

	public Credentials getCredentials(String username) throws PersistenceException{
		 Query userQuery = entityManager.createNativeQuery("select cred from Credentials cred where cred.username=?1", Credentials.class);
		 userQuery.setParameter(1, username);
		 
		 try{
		  Credentials credentials = (Credentials) userQuery.getSingleResult();
		  return credentials;
		 }catch(NoResultException e){
			 throw new ModelNotExistsException("Wrong credentials");
		 }catch(NonUniqueResultException e){
			 throw new TooManyResults("Too many results");
		 }
		}
	
	public List<RentalOffice> getRentalOfficeList(String rentalNetworkCode) throws PersistenceException {
		return null;//daoPersistenceFactory.getRentalOfficePersistence().getAll(rentalNetworkCode);
	}

	public List<Rent> getUserRentList(String userCode, String rentalNetworkCode) throws PersistenceException {
		/*ArrayList<Rent> userList = new ArrayList<Rent>();
		List<Rent> allRent = daoPersistenceFactory.getRentPersistence().getAll(rentalNetworkCode);

		for (Rent rent : allRent) {
			if (userCode.equals(rent.getUserCode())) {
				userList.add(rent);
			}
		}
		*/return null;
	}

	public List<Fee> getFeeList(String rentalNetworkCode) throws PersistenceException {
		return null;//daoPersistenceFactory.getFeePersistence().getAll(rentalNetworkCode);
		
	}

	public Bike getBike(String rentalNetworkCode, String bikeCode) throws PersistenceException {
		/*List<Bike> bikes = daoPersistenceFactory.getBikePersistence().getAll(rentalNetworkCode);
		for (Bike bike : bikes) {
			if (bike.getBikeCode().equals(bikeCode)) {
				return bike;
			}
		}
		throw new ModelNotExistsException("Bike does not exist");
		*/ return null;
	}
	
	
	
	public User getUser(Credentials credentials) throws PersistenceException{
		/*List<User> all = daoPersistenceFactory.getUserPersistence().getAll(credentials.getRentalNetworkCode());
		
		for(User u : all){
			if(u.getEmailAddress().equals(credentials.getEmailAddress())){
				return u;
			}
		}
		throw new ModelNotExistsException("User does not exist");
		*/
		return null;
	}
	
	public Rent getLastUserRent(String rentalNetworkCode, String userCode) throws PersistenceException{
		/*List<Rent> all = daoPersistenceFactory.getRentPersistence().getAll(rentalNetworkCode);
		Rent lastRent = null;
		
		for(Rent rent : all){
			if(rent.getUserCode().equals(userCode)){
				if(lastRent == null){
					lastRent = rent;
				}
				if(lastRent.getStartDate().compareTo(rent.getStartDate()) > 0){
					lastRent = rent;
				}
			}
		}
		return lastRent;
		*/
		return null;
	}
}
