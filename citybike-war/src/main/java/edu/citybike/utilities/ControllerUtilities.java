package edu.citybike.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.DatabaseFacadeImpl;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.Fee;
import edu.citybike.model.User;
import edu.citybike.model.view.UserInfo;

public class ControllerUtilities {
	private static final Logger logger = LoggerFactory.getLogger(ControllerUtilities.class);
	public static int ASC = 0;
	public static int DESC = 1;
	private DatabaseFacade facade;
	
	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	public ControllerUtilities() {
		facade = new DatabaseFacadeImpl();
	}
	
	public static double calculatePayment(List<Fee> fees, long rentDuration){
		List<Fee> feeList = sortFeeList(fees, ControllerUtilities.ASC);
		
		int i  = feeList.size()-1;
		double payment = 0;
		boolean found = false;
		
		if(rentDuration >= feeList.get(i).getTime()){
			return feeList.get(i).getFee();
		}
		
		for(; i < 0;i--){
			if(found){
				payment += payment = Math.ceil((feeList.get(i).getTime() - feeList.get(i-1).getTime())/60)*feeList.get(i-1).getFee();
			}
			
			if(feeList.get(i).getTime() > rentDuration && feeList.get(i-1).getTime() > rentDuration && !found){
				found = true;
				payment = Math.ceil((rentDuration - feeList.get(i-1).getTime())/60)*feeList.get(i-1).getFee();
			}			
		}
		return payment;
	}
	
	public static List<Fee> sortFeeList(List<Fee> feeList, final int direction){
		List<Fee> list = new ArrayList<Fee>();
		list.addAll(feeList);
		
		Collections.sort(list, new Comparator<Fee>(){

			@Override
			public int compare(Fee fee1, Fee fee2) {	
				if(direction == ControllerUtilities.ASC){
				return fee2.getTime() - fee1.getTime();
				}
				else if(direction == ControllerUtilities.DESC){
					return fee1.getTime() - fee2.getTime();
				}
				return 0; 	
			}
			
		});
		return list;
	}	
	public static User retriveChangedUser(DatabaseFacade facade, UserInfo userInfo) throws PersistenceException{
		User userFromDatabase = facade.getUserByLogin(userInfo.getEmailAddress());
		userFromDatabase.setAddress(userInfo.getAddress());
		userFromDatabase.setName(userInfo.getName());
		userFromDatabase.setLastName(userInfo.getLastName());
		userFromDatabase.setPhoneNumber(userInfo.getPhoneNumber());
		return userFromDatabase;
	}
}

