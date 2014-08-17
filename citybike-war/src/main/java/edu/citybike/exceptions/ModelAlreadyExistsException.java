package edu.citybike.exceptions;

public class ModelAlreadyExistsException extends PersistenceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModelAlreadyExistsException(String message) {
		super(message);
	}

	public ModelAlreadyExistsException(Exception e) {
		super(e);
	}
}
