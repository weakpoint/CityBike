package edu.citybike.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.database.nosql.NoSQLPersistence;
import edu.citybike.model.Bike;
import edu.citybike.model.Bike.STATUS;

public class ControllerUtilities {
	private static final Logger logger = LoggerFactory.getLogger(ControllerUtilities.class);
	private DatabaseFacade facade;
	
	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	public ControllerUtilities() {
		facade = new DatabaseFacade();
		facade.setDaoPersistenceFactory(new NoSQLPersistence());
	}

	public Bike changeBikeStatus(String bikeCode, String rentalNetworkCode, STATUS status) throws PersistenceException{
		logger.info("facade "+facade);
		Bike bike = facade.getBike(rentalNetworkCode, bikeCode);
		
		bike.setStatus(status);
		facade.update(bike);
		return bike;
	}
}
