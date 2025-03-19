package org.example.app.exception;

public class EmailAlreadyRegistered extends RuntimeException{
    public EmailAlreadyRegistered(final String message) {
        super(message);
    }
}
