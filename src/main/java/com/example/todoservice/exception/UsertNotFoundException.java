package com.example.todoservice.exception;

public class UsertNotFoundException extends RuntimeException {


    /**
     *
     */
    private static final long serialVersionUID = -7538068495982960133L;

    public UsertNotFoundException() {
        super("User not found");
    }

    public UsertNotFoundException(String message) {
        super(message);
    }

}
