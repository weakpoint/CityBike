package edu.citybike.database.nosql;

import edu.citybike.database.DAOPersistenceFactory;
import edu.citybike.database.ModelPersistence;
import edu.citybike.model.Bike;
import edu.citybike.model.Credentials;
import edu.citybike.model.Fee;
import edu.citybike.model.Rent;
import edu.citybike.model.RentalNetwork;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.User;
import edu.citybike.model.Employee;

public class NoSQLPersistence implements DAOPersistenceFactory {

	public ModelPersistence<User> getUserPersistence() {
		return new NoSQLUserPersistence();
	}

	public ModelPersistence<Employee> getWorkerPersistence() {
		return new NoSQLEmployeePersistence();
	}

	public ModelPersistence<RentalOffice> getRentalOfficePersistence() {
		return new NoSQLRentalOfficePersistence();
	}

	public ModelPersistence<RentalNetwork> getRentalNetworkPersistence() {
		return new NoSQLRentalNetworkPersistence();
	}

	public ModelPersistence<Bike> getBikePersistence() {
		return new NoSQLBikePersistence();
	}

	public ModelPersistence<Fee> getFeePersistence() {
		return new NoSQLFeePersistence();
	}

	public ModelPersistence<Rent> getRentPersistence() {
		return new NoSQLRentPersistence();
	}

	public ModelPersistence<Credentials> getCredentialsPersistence() {
		return new NoSQLCredentialsPersistence();
	}

	
}
