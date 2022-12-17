package com.wildtac.exception.wasnotfound;

public class EntityWasNotFoundException extends RuntimeException{
    public EntityWasNotFoundException() {
        super();
    }

    public EntityWasNotFoundException(String message) {
        super(message);
    }
}
