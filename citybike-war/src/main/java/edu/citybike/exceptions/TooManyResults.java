package edu.citybike.exceptions;

public class TooManyResults extends PersistenceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TooManyResults(String message) {
		super(message);
	}
	
	public TooManyResults(Throwable e){
		super(e);
	}
}
