package edu.citybike.database.sql;

import edu.citybike.database.DAOPersistenceFactory;
import edu.citybike.database.ModelPersistence;
import edu.citybike.model.Bike;
import edu.citybike.model.Credentials;
import edu.citybike.model.Fee;
import edu.citybike.model.Rent;
import edu.citybike.model.RentalNetwork;
import edu.citybike.model.RentalOffice;
import edu.citybike.model.User;

public class SQLPersistence implements DAOPersistenceFactory {

	@Override
	public ModelPersistence<User> getUserPersistence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelPersistence<RentalOffice> getRentalOfficePersistence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelPersistence<RentalNetwork> getRentalNetworkPersistence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelPersistence<Bike> getBikePersistence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelPersistence<Fee> getFeePersistence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelPersistence<Rent> getRentPersistence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelPersistence<Credentials> getCredentialsPersistence() {
		// TODO Auto-generated method stub
		return null;
	}

}
