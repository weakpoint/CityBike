package edu.citybike.database;

import edu.citybike.model.Bike;
import edu.citybike.model.Credentials;
import edu.citybike.model.Fee;
import edu.citybike.model.Rent;
import edu.citybike.model.RentalNetwork;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.User;


public interface DAOPersistenceFactory {

	public ModelPersistence<User> getUserPersistence();
	//public ModelPersistence<Employee> getWorkerPersistence();
	public ModelPersistence<RentalOffice> getRentalOfficePersistence();
	public ModelPersistence<RentalNetwork> getRentalNetworkPersistence();
	public ModelPersistence<Bike> getBikePersistence();
	public ModelPersistence<Fee> getFeePersistence();
	public ModelPersistence<Rent> getRentPersistence();
	public ModelPersistence<Credentials> getCredentialsPersistence();
	
}
