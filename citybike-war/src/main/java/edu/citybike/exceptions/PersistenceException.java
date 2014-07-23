package edu.citybike.exceptions;

public class PersistenceException extends Exception {

	public PersistenceException(String message){
		super(message);
	}
	
	public PersistenceException(Throwable e){
		super(e);
	}
}
