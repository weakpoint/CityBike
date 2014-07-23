package edu.citybike.exceptions;

public class ModelNotExistsException extends PersistenceException {
	
	public ModelNotExistsException(String message){
		super(message);
	}


}
