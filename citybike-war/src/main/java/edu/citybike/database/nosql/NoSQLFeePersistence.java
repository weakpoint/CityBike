package edu.citybike.database.nosql;

import java.util.ArrayList;
import java.util.List;

import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Fee;

public class NoSQLFeePersistence extends NoSQLModelPersistence<Fee> {

	@Override
	public Fee save(Fee model) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Fee model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Fee model) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Fee> getAll(String rentalNetworkCode) throws PersistenceException {
		ArrayList<Fee> feeList = new ArrayList<Fee>();
		
		Fee f1 = new Fee();
		f1.setFee(1);
		f1.setTime(20);
		
		Fee f2 = new Fee();
		f2.setFee(2);
		f2.setTime(40);
		
		Fee f3 = new Fee();
		f3.setFee(4);
		f3.setTime(120);
		
		feeList.add(f1);
		feeList.add(f2);
		feeList.add(f3);
		return feeList;
	}

}
