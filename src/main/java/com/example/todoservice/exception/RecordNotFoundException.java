package com.example.todoservice.exception;

public class RecordNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2607859929525894779L;

	public RecordNotFoundException() {
		super("Record not found");
	}

	public RecordNotFoundException(String message) {
		super(message);
	}

}
