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
		logger.debug("calculatePayment: ");
		logger.debug("Fee list size: "+fees.size());
		logger.debug("Rent duration: "+rentDuration);
		
		if(fees == null || fees.size() == 0){
			return 0;
		}
		
		List<Fee> feeList = sortFeeList(fees, ControllerUtilities.ASC);
		
		//if rent time is greater or equal than the longest duration time
		if(feeList.get(0).getTime() <= rentDuration){
			return feeList.get(0).getFee();
		}
		
		//if rent time is smaller or equal the shortest duration time
		if(feeList.get(feeList.size()-1).getTime() >= rentDuration){
			return feeList.get(feeList.size()-1).getFee();
		}
		
		for(int i = 0; i < feeList.size()-1;i++){
			logger.trace(feeList.get(i+1).getTime()+" <= "+rentDuration+" < "+feeList.get(i).getTime());
			
			if(feeList.get(i).getTime() > rentDuration && feeList.get(i+1).getTime() <= rentDuration){
				double difC = 0;
				double difT = feeList.get(i).getTime() - feeList.get(i+1).getTime();
				
				//happy hour
				if(feeList.get(i+1).getFee() != 0){
					difC = feeList.get(i).getFee() - feeList.get(i+1).getFee();
				}
				
				return feeList.get(i+1).getFee() + ((rentDuration-feeList.get(i+1).getTime())*(difC/difT));
			}
		}
		
		return 0;
		
	}

	public static List<Fee> sortFeeList(List<Fee> feeList, final int direction) {
		List<Fee> list = new ArrayList<Fee>();
		list.addAll(feeList);

		Collections.sort(list, new Comparator<Fee>() {

			@Override
			public int compare(Fee fee1, Fee fee2) {
				if (direction == ControllerUtilities.ASC) {
					return fee2.getTime() - fee1.getTime();
				} else if (direction == ControllerUtilities.DESC) {
					return fee1.getTime() - fee2.getTime();
				}
				return 0;
			}

		});
		return list;
	}

	public static User retriveChangedUser(DatabaseFacade facade, UserInfo userInfo) throws PersistenceException {
		User userFromDatabase = facade.getUserByLogin(userInfo.getEmailAddress());
		userFromDatabase.setAddress(userInfo.getAddress());
		userFromDatabase.setName(userInfo.getName());
		userFromDatabase.setLastName(userInfo.getLastName());
		userFromDatabase.setPhoneNumber(userInfo.getPhoneNumber());
		return userFromDatabase;
	}

	public static String createGetterMethodName(String fieldName) {
		StringBuilder sb = new StringBuilder(fieldName);
		sb.setCharAt(0, fieldName.substring(0, 1).toUpperCase().charAt(0));
		sb.insert(0, "get");
		return sb.toString();
	}
}
