package edu.citybike.exceptions;

public class ModelNotExistsException extends PersistenceException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModelNotExistsException(String message){
		super(message);
	}


}
