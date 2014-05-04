package edu.citybike.database;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.citybike.database.exception.PersistenceException;
import edu.citybike.database.nosql.NoSQLPersistence;
import edu.citybike.model.User;

public class DatabaseFacadeTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();

	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testSave() throws PersistenceException {
		DatabaseFacade facade = new DatabaseFacade();
		facade.setDaoPersistenceFactory(new NoSQLPersistence());

		User user = new User();
		user.setName("Test user");
		user.setLastName("TTest");
		facade.save(user);
	}

	@Test(expected = PersistenceException.class)
	public void testSaveWithException() throws PersistenceException {
		DatabaseFacade facade = new DatabaseFacade();
		facade.setDaoPersistenceFactory(new NoSQLPersistence());

		User user = new User();
		user.setName("Test user");
		user.setLastName("TTest");

		user = (User) facade.save(user);
		facade.save(user);
	}

	@Test
	public void testGetUser() throws PersistenceException {

		DatabaseFacade facade = new DatabaseFacade();
		facade.setDaoPersistenceFactory(new NoSQLPersistence());

		User user = new User();
		user.setName("Test user");
		user.setLastName("TTest");
		user.setRentalNetworkCode("00");

		user = (User) facade.save(user);

		User user2 = facade.getUser("00", user.getUserCode());

		assertEquals("Test user", user2.getName());

	}

	@Test
	public void testUpdate() throws PersistenceException {
		DatabaseFacade facade = new DatabaseFacade();
		facade.setDaoPersistenceFactory(new NoSQLPersistence());

		User user = new User();
		user.setName("Test user");
		user.setLastName("TTest");
		user.setUserCode("007");
		user.setRentalNetworkCode("00");

		user = (User) facade.save(user);
		
		user.setName("Updated name");
		
		facade.update(user);
		
		User user2 = facade.getUser("00", user.getUserCode());
		
		assertEquals("Updated name", user2.getName());
	}
	
	@Test(expected=PersistenceException.class)
	public void testUpdateWithModelNotExistsException() throws PersistenceException {
		DatabaseFacade facade = new DatabaseFacade();
		facade.setDaoPersistenceFactory(new NoSQLPersistence());

		User user = new User();
		user.setName("Test user");
		user.setLastName("TTest");
		user.setUserCode("007");
		user.setRentalNetworkCode("00");
		
		facade.update(user);
	}

}
