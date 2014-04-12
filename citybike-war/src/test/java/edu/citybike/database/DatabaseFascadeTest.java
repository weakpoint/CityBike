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

public class DatabaseFascadeTest {

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
		DatabaseFascade fascade = new DatabaseFascade();
		fascade.setDaoPersistenceFactory(new NoSQLPersistence());

		User user = new User();
		user.setName("Test user");
		user.setLastName("TTest");

		fascade.save(user);
	}

	@Test(expected = PersistenceException.class)
	public void testSaveWithException() throws PersistenceException {
		DatabaseFascade fascade = new DatabaseFascade();
		fascade.setDaoPersistenceFactory(new NoSQLPersistence());

		User user = new User();
		user.setName("Test user");
		user.setLastName("TTest");
		user.setUserCode("007");

		fascade.save(user);
		fascade.save(user);
	}

	@Test
	public void testGetUser() throws PersistenceException {

		DatabaseFascade fascade = new DatabaseFascade();
		fascade.setDaoPersistenceFactory(new NoSQLPersistence());

		User user = new User();
		user.setName("Test user");
		user.setLastName("TTest");
		user.setUserCode("007");
		user.setRentalNetworkCode("00");

		fascade.save(user);

		User user2 = fascade.getUser("00", "007");

		assertEquals("Test user", user2.getName());

	}

	@Test
	public void testUpdate() throws PersistenceException {
		DatabaseFascade fascade = new DatabaseFascade();
		fascade.setDaoPersistenceFactory(new NoSQLPersistence());

		User user = new User();
		user.setName("Test user");
		user.setLastName("TTest");
		user.setUserCode("007");
		user.setRentalNetworkCode("00");

		fascade.save(user);
		
		user.setName("Updated name");
		
		fascade.update(user);
		
		User user2 = fascade.getUser("00", "007");
		
		assertEquals("Updated name", user2.getName());
	}
	
	@Test(expected=PersistenceException.class)
	public void testUpdateWithModelNotExistsException() throws PersistenceException {
		DatabaseFascade fascade = new DatabaseFascade();
		fascade.setDaoPersistenceFactory(new NoSQLPersistence());

		User user = new User();
		user.setName("Test user");
		user.setLastName("TTest");
		user.setUserCode("007");
		user.setRentalNetworkCode("00");
		
		fascade.update(user);
	}

}
