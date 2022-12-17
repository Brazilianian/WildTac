package com.wildtac.exception.alreadyexists;

public class UserAlreadyExistsException extends EntityAlreadyExistsException{
    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
