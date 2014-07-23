package edu.citybike.bank;

import com.google.appengine.api.datastore.Key;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.exceptions.ModelNotExistsException;
import edu.citybike.exceptions.NegativeBalanceException;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.BankAccount;

public class BankService {
	
	private static DatabaseFacade facade;

	public static DatabaseFacade getFacade() {
		return facade;
	}
	
	public static void setFacade(DatabaseFacade facade) {
		BankService.facade = facade;
	}


	public static void createBankAccount(Key userKey) throws PersistenceException{
		if(userKey != null){
			BankAccount account = new BankAccount();
			account.setUserKey(userKey);
			account.setBalance(5.0);
			facade.add(account);

		}
	}
	
	public static double checkBalance(Key userKey) throws ModelNotExistsException{
		if(userKey != null){
			return facade.getUserBankAccount(userKey).getBalance();
		}
		return 0;
	}
	
	public static void doOperation(Key userKey, double value) throws PersistenceException{
		if(userKey != null){
			BankAccount bankAccount = facade.getUserBankAccount(userKey);
			bankAccount.setBalance(bankAccount.getBalance() + value);
			
			if(bankAccount.getBalance() < 0){
				throw new NegativeBalanceException();
			}
			
			facade.update(bankAccount);
		}
	}
	
	public static void removeBankAccount(Key userKey) throws PersistenceException{
		if(userKey != null){
			BankAccount bankAccount = facade.getUserBankAccount(userKey);
			facade.remove(bankAccount);
		}
	}
}
