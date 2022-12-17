package com.wildtac.exception.alreadyexists;

public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException() {
        super();
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
