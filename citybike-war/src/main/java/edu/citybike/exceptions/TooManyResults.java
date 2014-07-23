package edu.citybike.exceptions;

public class TooManyResults extends PersistenceException {

	public TooManyResults(String message) {
		super(message);
	}
	
	public TooManyResults(Throwable e){
		super(e);
	}
}
