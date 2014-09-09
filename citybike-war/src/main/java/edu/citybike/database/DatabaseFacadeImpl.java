package edu.citybike.database;

import java.util.ArrayList;
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

import edu.citybike.exceptions.ModelNotExistsException;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.exceptions.TooManyResults;
import edu.citybike.model.BankAccount;
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
		return entityManagerFactory.createEntityManager().getTransaction();
	}


	public User getUserByLogin(String login) throws PersistenceException {
		
		Query userQuery = entityManager.createQuery("select u from User u where u.emailAddress=?1");
		userQuery.setParameter(1, login);
		
		try {
			User user = (User) userQuery.getSingleResult();
			return user;
		} catch (NoResultException e) {
			throw new ModelNotExistsException("User does not exist");
		} catch (NonUniqueResultException e) {
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
		 Query credentialsQuery = entityManager.createQuery("select cred from Credentials cred where cred.username=?1");
		 credentialsQuery.setParameter(1, username);
		 
		 try{
		  Credentials credentials = (Credentials) credentialsQuery.getSingleResult();
		  return credentials;
		 }catch(NoResultException e){
			 throw new ModelNotExistsException("Wrong credentials");
		 }catch(NonUniqueResultException e){
			 throw new TooManyResults("Too many results");
		 }
		}
	
	public List<RentalOffice> getRentalOfficeList() {
		 Query rentalOfficeQuery = entityManager.createQuery("select rentalOffice from RentalOffice rentalOffice");
		
		  List<RentalOffice> rentalOfficeList = rentalOfficeQuery.getResultList();
		  if(rentalOfficeList == null){
			  rentalOfficeList = new ArrayList<RentalOffice>();
		  }
		  return rentalOfficeList;
	}
	

	public List<Rent> getUserRentListDesc(Key userKey) throws PersistenceException {
		Query userRentQuery = entityManager.createQuery("select rent from Rent rent where rent.userCode=?1 order by rent.startDate desc");
		userRentQuery.setParameter(1, userKey);
		
		List<Rent> resultList = userRentQuery.getResultList();

		return (resultList != null) ? resultList : new ArrayList<Rent>();
	}

	public List<Fee> getFeeList() throws PersistenceException {
		
		Query feeQuery = entityManager.createQuery("select fee from Fee fee order by fee.time asc");
		
		  List<Fee> feeList = (List<Fee>)feeQuery.getResultList();
		  if(feeList == null){
			  feeList = new ArrayList<Fee>();
		  }
		  return feeList;
	}
	
	public void remove(Object model) throws PersistenceException{
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(model);
			entityManager.getTransaction().commit();
			System.out.println("usuwanie: "+model);
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new PersistenceException(e);
		}
	}

	public List<Bike> getBikeList() throws PersistenceException{
		Query bikeQuery = entityManager.createQuery("select bike from Bike bike");
		
		List<Bike> bikeList = (List<Bike>) bikeQuery.getResultList();
		if(bikeList == null){
			bikeList = new ArrayList<Bike>();
		}
		return bikeList;
	}
	
	public RentalOffice getRentalOfficeByKey(Key key){
		return entityManager.find(RentalOffice.class, key);
	}
	
	public BankAccount getUserBankAccount(Key userKey) throws ModelNotExistsException{
		Query bankAccountQuery = entityManager.createQuery("select ba from BankAccount ba where ba.userKey=?1");
		bankAccountQuery.setParameter(1, userKey);
		
		BankAccount bankAccount = (BankAccount) bankAccountQuery.getSingleResult();
		if(bankAccount == null){
			throw new ModelNotExistsException("There is no bank account for this user");
		}
		return bankAccount;
	}
	
	public Rent getUserActiveRental(Key userKey) throws ModelNotExistsException{
		Query activeRentalQuery = entityManager.createQuery("select rental from Rent rental where rental.userCode=?1 and rental.active = true");
		activeRentalQuery.setParameter(1, userKey);
		Rent activeRental = null;
		try{
			activeRental = (Rent) activeRentalQuery.getSingleResult(); 
		}catch(NoResultException e){
			throw new ModelNotExistsException("There is no active rental");
		}
		return activeRental;
	}


	@Override
	public List <Rent> getRentList() throws PersistenceException {
		Query rentListQuery = entityManager.createQuery("select rental from Rent rental");
		return (List<Rent>) rentListQuery.getResultList();
	}

	@Override
	public List<User> getUserList() {
		Query userQuery = entityManager.createQuery("select u from User u");
			return userQuery.getResultList();
	}

}
