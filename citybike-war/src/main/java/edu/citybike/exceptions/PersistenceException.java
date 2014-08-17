package edu.citybike.exceptions;

public class PersistenceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersistenceException(String message){
		super(message);
	}
	
	public PersistenceException(Throwable e){
		super(e);
	}
}
