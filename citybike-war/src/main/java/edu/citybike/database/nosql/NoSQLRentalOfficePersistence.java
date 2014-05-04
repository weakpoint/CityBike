package edu.citybike.database.nosql;

import java.util.ArrayList;
import java.util.List;

import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Address;
import edu.citybike.model.RentalOffice;

public class NoSQLRentalOfficePersistence extends NoSQLModelPersistence<RentalOffice> {

	public RentalOffice save(RentalOffice model) throws PersistenceException {
		return model;
		// TODO Auto-generated method stub
		
	}

	public void update(RentalOffice model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	public void delete(RentalOffice model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	public List<RentalOffice> getAll(String rentalNetworkCode)
			throws PersistenceException {
		RentalOffice office = new RentalOffice();
		office.setRentalNetworkCode("0001");
		office.setRentalOfficeCode("01");
		office.setLatitude("21.1212");
		office.setLongitude("52.5115");
		Address address = new Address();
		address.setCity("Lodz");
		address.setStreet("Strykowska");
		address.setHouseNumber("33/43");
		address.setFlatNumber("");
		address.setPostalCode("92-233");
		office.setAddress(address);
		List<RentalOffice> list = new ArrayList<RentalOffice>();
		list.add(office);
		return list;
	}



}
