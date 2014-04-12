package edu.citybike.database.exception;

public class ModelAlreadyExistsException extends PersistenceException {
	public ModelAlreadyExistsException(String message) {
		super(message);
	}

	public ModelAlreadyExistsException(Exception e) {
		super(e);
	}
}
