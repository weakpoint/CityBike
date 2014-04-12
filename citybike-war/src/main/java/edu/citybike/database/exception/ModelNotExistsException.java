package edu.citybike.database.exception;

public class ModelNotExistsException extends PersistenceException {
	
	public ModelNotExistsException(String message){
		super(message);
	}


}
